package com.upnvj.skripsian.ui.lecturer;

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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.databinding.FragmentLecturerBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.callback.OnItemClickListener;
import com.upnvj.skripsian.vm.MainViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturerFragment extends Fragment {

    private NavController navController;
    private FragmentLecturerBinding binding;
    private MainViewModel mainViewModel;
    private LecturerViewModel viewModel;
    private LecturerAdapter adapter;

    public LecturerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lecturer, container, false);
        mainViewModel = new ViewModelProvider(requireActivity(), ServiceLocator.mainFactory()).get(MainViewModel.class);
        viewModel = new ViewModelProvider(this, ServiceLocator.lecturerFactory(requireContext())).get(LecturerViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        OnItemClickListener<Lecturer> itemClickListener = new OnItemClickListener<Lecturer>() {
            @Override
            public void onClick(Lecturer data) {
                navController.navigate(LecturerFragmentDirections.actionLecturerFragmentToDetailLecturerFragment(data.getId()));
            }
        };
        adapter = new LecturerAdapter(itemClickListener);
        binding.listLecturer.setAdapter(adapter);

        mainViewModel.getAddLecturerEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    mainViewModel.onAddLecturerEventHandled();
                    NavDirections action = LecturerFragmentDirections.actionLecturerFragmentToAddLecturerFragment();
                    navController.navigate(action);
                }
            }
        });
        viewModel.getAllLecturers().observe(getViewLifecycleOwner(), new Observer<List<Lecturer>>() {
            @Override
            public void onChanged(List<Lecturer> lecturers) {
                if (lecturers == null || lecturers.isEmpty()) {
                    binding.msgEmpty.setVisibility(View.VISIBLE);
                    binding.listLecturer.setVisibility(View.GONE);
                } else {
                    binding.msgEmpty.setVisibility(View.GONE);
                    binding.listLecturer.setVisibility(View.VISIBLE);
                    adapter.submitList(lecturers);
                }
            }
        });
    }

}
