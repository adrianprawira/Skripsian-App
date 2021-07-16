package com.upnvj.skripsian.ui.home;

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

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.databinding.FragmentHomeBinding;
import com.upnvj.skripsian.util.ServiceLocator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private HomeAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.homeFactory(requireContext())).get(HomeViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HomeAdapter();
        binding.listTodaySchedule.setAdapter(adapter);

        viewModel.getLecturerCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setLecturerCount(s);
            }
        });
        viewModel.getTotalSchedule().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setTotalSchedule(s);
            }
        });
        viewModel.getTodaySchedules().observe(getViewLifecycleOwner(), new Observer<List<ThesisScheduleWithLecturers>>() {
            @Override
            public void onChanged(List<ThesisScheduleWithLecturers> list) {
                if (list == null || list.isEmpty()) {
                    binding.msgEmpty.setVisibility(View.VISIBLE);
                    binding.listTodaySchedule.setVisibility(View.GONE);
                } else {
                    binding.msgEmpty.setVisibility(View.GONE);
                    binding.listTodaySchedule.setVisibility(View.VISIBLE);
                    adapter.submitList(list);
                }
            }
        });
    }

}
