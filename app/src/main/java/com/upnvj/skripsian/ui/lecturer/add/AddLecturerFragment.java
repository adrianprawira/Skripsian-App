package com.upnvj.skripsian.ui.lecturer.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.upnvj.skripsian.MainActivity;
import com.upnvj.skripsian.R;
import com.upnvj.skripsian.databinding.FragmentAddLecturerBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.Toaster;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLecturerFragment extends Fragment {

    private NavController navController;
    private FragmentAddLecturerBinding binding;
    private AddLecturerViewModel viewModel;

    public AddLecturerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_lecturer, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.addLecturerFactory(requireContext())).get(AddLecturerViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AddLecturerFragmentArgs safeArgs = AddLecturerFragmentArgs.fromBundle(getArguments());
        int titleRes = safeArgs.getLecturerId() == 0 ? R.string.title_add_lecturer : R.string.title_edit_lecturer;
        ((MainActivity) requireActivity()).setToolbarTitle(titleRes);

        viewModel.setLecturerId(safeArgs.getLecturerId());
        viewModel.getViewState().observe(getViewLifecycleOwner(), new Observer<AddLecturerViewState>() {
            @Override
            public void onChanged(AddLecturerViewState state) {
                binding.setViewState(state);
            }
        });
        viewModel.getEventLecturerSaved().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_lecturer_saved);
                    viewModel.onEventLecturerSavedHandled();
                    navController.navigateUp();
                }
            }
        });
        viewModel.getEventMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toaster.longLength(requireContext(), message);
                    viewModel.onEventMessageHandled();
                }
            }
        });
    }
}
