package com.upnvj.skripsian.ui.thesis;

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
import com.upnvj.skripsian.databinding.FragmentThesisBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.callback.OnItemClickListener;
import com.upnvj.skripsian.vm.MainViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThesisFragment extends Fragment {

    private NavController navController;
    private FragmentThesisBinding binding;
    private MainViewModel mainViewModel;
    private ThesisViewModel viewModel;

    private ThesisAdapter adapter;

    public ThesisFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thesis, container, false);
        mainViewModel = new ViewModelProvider(requireActivity(), ServiceLocator.mainFactory()).get(MainViewModel.class);
        viewModel = new ViewModelProvider(requireActivity(), ServiceLocator.thesisFactory(requireContext())).get(ThesisViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        OnItemClickListener<ThesisScheduleGroup> listener = new OnItemClickListener<ThesisScheduleGroup>() {
            @Override
            public void onClick(ThesisScheduleGroup data) {
                showThesisScheduleListPage(data);
            }
        };
        adapter = new ThesisAdapter(listener);
        binding.listSchedules.setAdapter(adapter);

        mainViewModel.getAddThesisScheduleEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    mainViewModel.onAddThesisScheduleEventHandled();
                    NavDirections action =
                            ThesisFragmentDirections.actionThesisFragmentToAddThesisScheduleFragment();
                    navController.navigate(action);
                }
            }
        });
        viewModel.getThesisScheduleGroups().observe(getViewLifecycleOwner(), new Observer<List<ThesisScheduleGroup>>() {
            @Override
            public void onChanged(List<ThesisScheduleGroup> thesisScheduleGroups) {
                if (thesisScheduleGroups == null || thesisScheduleGroups.isEmpty()) {
                    binding.msgEmpty.setVisibility(View.VISIBLE);
                    binding.listSchedules.setVisibility(View.GONE);
                } else {
                    binding.msgEmpty.setVisibility(View.GONE);
                    binding.listSchedules.setVisibility(View.VISIBLE);
                    adapter.submitList(thesisScheduleGroups);
                }
            }
        });
    }

    private void showThesisScheduleListPage(ThesisScheduleGroup group) {
        NavDirections action = ThesisFragmentDirections.actionThesisFragmentToListThesisFragment(group.getDateString());
        navController.navigate(action);
    }

}
