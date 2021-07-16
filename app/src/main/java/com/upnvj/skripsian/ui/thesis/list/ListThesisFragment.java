package com.upnvj.skripsian.ui.thesis.list;

import android.content.DialogInterface;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.upnvj.skripsian.MainActivity;
import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.databinding.FragmentListThesisBinding;
import com.upnvj.skripsian.util.DateTimeUtils;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.Toaster;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListThesisFragment extends Fragment {

    private NavController navController;
    private FragmentListThesisBinding binding;
    private ListThesisViewModel viewModel;

    private ListThesisAdapter scheduleAdapter;
    private ListThesisFilterAdapter filterAdapter;

    private String dateFilter;

    public ListThesisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_thesis, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.listThesisFactory(requireContext())).get(ListThesisViewModel.class);

        ListThesisFragmentArgs safeArgs = ListThesisFragmentArgs.fromBundle(getArguments());
        String[] timeStudies = getResources().getStringArray(R.array.time_of_studies);
        String[] timeStudyDetails = getResources().getStringArray(R.array.time_of_study_details);
        dateFilter = safeArgs.getGroupDateString();

        viewModel.setDateFilter(dateFilter);
        viewModel.setTimeFilterList(timeStudies, timeStudyDetails);

        binding.setView(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).setToolbarTitle(dateFilter);
        navController = Navigation.findNavController(view);

        OnItemClickListener<ListThesisFilter> filterListener = new OnItemClickListener<ListThesisFilter>() {
            @Override
            public void onClick(ListThesisFilter data) {
                viewModel.setTimeFilter(data);
            }
        };
        filterAdapter = new ListThesisFilterAdapter(filterListener);
        binding.listFilter.setAdapter(filterAdapter);

        OnItemClickListener<ThesisScheduleWithLecturers> scheduleListener = new OnItemClickListener<ThesisScheduleWithLecturers>() {
            @Override
            public void onClick(ThesisScheduleWithLecturers data) {
                showEditThesisSchedulePage(data);
            }

            @Override
            public void onLongClick(ThesisScheduleWithLecturers data) {
                showDeleteScheduleConfirmationDialog(data);
            }
        };
        scheduleAdapter = new ListThesisAdapter(scheduleListener);
        binding.listSchedule.setAdapter(scheduleAdapter);

        viewModel.getTimeFilterList().observe(getViewLifecycleOwner(), new Observer<List<ListThesisFilter>>() {
            @Override
            public void onChanged(List<ListThesisFilter> listThesisFilters) {
                filterAdapter.submitList(listThesisFilters);
            }
        });
        viewModel.getFilteredSchedules().observe(getViewLifecycleOwner(), new Observer<List<ThesisScheduleWithLecturers>>() {
            @Override
            public void onChanged(List<ThesisScheduleWithLecturers> data) {
                if (data == null || data.isEmpty()) {
                    binding.msgEmpty.setVisibility(View.VISIBLE);
                    binding.listSchedule.setVisibility(View.GONE);
                } else {
                    binding.msgEmpty.setVisibility(View.GONE);
                    binding.listSchedule.setVisibility(View.VISIBLE);
                    scheduleAdapter.submitList(data);
                }
            }
        });
        viewModel.getEventScheduleDeleted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_thesis_schedule_deleted);
                    viewModel.onEventScheduleDeletedHandled();
                }
            }
        });
    }

    public void showAddThesisSchedulePage() {
        ListThesisFragmentDirections.ActionListThesisFragmentToAddThesisScheduleFragment action = ListThesisFragmentDirections.actionListThesisFragmentToAddThesisScheduleFragment();
        action.setDayDateTimestamp(DateTimeUtils.parseDate(dateFilter));
        navController.navigate(action);
    }

    private void showEditThesisSchedulePage(ThesisScheduleWithLecturers data) {
        ListThesisFragmentDirections.ActionListThesisFragmentToAddThesisScheduleFragment action = ListThesisFragmentDirections.actionListThesisFragmentToAddThesisScheduleFragment();
        action.setScheduleId(data.getSchedule().getId());
        navController.navigate(action);
    }

    private void showDeleteScheduleConfirmationDialog(final ThesisScheduleWithLecturers schedule) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.action_delete_schedule)
                .setMessage(getString(R.string.msg_delete_thesis_schedule_confirmation, schedule.getSchedule().getFullName()))
                .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteSchedule(schedule);
                    }
                })
                .setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

}
