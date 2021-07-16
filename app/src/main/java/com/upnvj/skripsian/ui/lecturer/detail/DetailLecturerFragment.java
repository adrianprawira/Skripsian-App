package com.upnvj.skripsian.ui.lecturer.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.databinding.FragmentDetailLecturerBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.Toaster;
import com.upnvj.skripsian.util.callback.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailLecturerFragment extends Fragment {

    private NavController navController;
    private FragmentDetailLecturerBinding binding;
    private DetailLecturerViewModel viewModel;
    private DetailLecturerAdapter adapter;

    private Lecturer lecturer;

    public DetailLecturerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_lecturer, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.detailLecturerFactory(requireContext())).get(DetailLecturerViewModel.class);
        binding.setView(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailLecturerFragmentArgs safeArgs = DetailLecturerFragmentArgs.fromBundle(getArguments());
        navController = Navigation.findNavController(view);

        OnItemClickListener<LecturerSchedule> listener = new OnItemClickListener<LecturerSchedule>() {
            @Override
            public void onClick(LecturerSchedule data) {
                showEditLecturerSchedulePage(data.getId());
            }

            @Override
            public void onLongClick(LecturerSchedule data) {
                showDeleteScheduleConfirmationDialog(data);
            }
        };
        String[] timeStudies = getResources().getStringArray(R.array.time_of_study_details);
        adapter = new DetailLecturerAdapter(listener, timeStudies);
        binding.listSchedules.setAdapter(adapter);

        viewModel.getDetailLecturer(safeArgs.getLecturerId()).observe(getViewLifecycleOwner(), new Observer<Lecturer>() {
            @Override
            public void onChanged(Lecturer lecturer) {
                DetailLecturerFragment.this.lecturer = lecturer;
                binding.setLecturer(lecturer);
            }
        });
        viewModel.getLecturerSchedules(safeArgs.getLecturerId()).observe(getViewLifecycleOwner(), new Observer<List<LecturerSchedule>>() {
            @Override
            public void onChanged(List<LecturerSchedule> lecturerSchedules) {
                if (lecturerSchedules == null || lecturerSchedules.isEmpty()) {
                    binding.listSchedules.setVisibility(View.GONE);
                } else {
                    binding.listSchedules.setVisibility(View.VISIBLE);
                    adapter.submitList(lecturerSchedules);
                }
            }
        });
        viewModel.getViewData().observe(getViewLifecycleOwner(), new Observer<DetailLecturerViewData>() {
            @Override
            public void onChanged(DetailLecturerViewData detailLecturerViewData) {
                binding.setData(detailLecturerViewData);
            }
        });
        viewModel.getEventDeleted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_lecturer_deleted);
                    viewModel.onEventDeletedHandled();
                    navController.navigateUp();
                }
            }
        });
        viewModel.getEventScheduleDeleted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_lecturer_schedule_deleted);
                    viewModel.onEventScheduleDeletedHandled();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.detail_lecturer_action, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_lecturer) {
            showEditLecturerPage();
            return true;
        } else if (id == R.id.action_delete_lecturer) {
            showDeleteConfirmationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void showAddLecturerSchedulePage() {
        DetailLecturerFragmentDirections.ActionDetailLecturerFragmentToAddLecturerScheduleFragment action = DetailLecturerFragmentDirections.actionDetailLecturerFragmentToAddLecturerScheduleFragment(lecturer.getId());
        navController.navigate(action);
    }

    private void showEditLecturerPage() {
        DetailLecturerFragmentDirections.ActionDetailLecturerFragmentToAddLecturerFragment action = DetailLecturerFragmentDirections.actionDetailLecturerFragmentToAddLecturerFragment();
        action.setLecturerId(lecturer.getId());
        navController.navigate(action);
    }

    private void showEditLecturerSchedulePage(long scheduleId) {
        DetailLecturerFragmentDirections.ActionDetailLecturerFragmentToAddLecturerScheduleFragment action = DetailLecturerFragmentDirections.actionDetailLecturerFragmentToAddLecturerScheduleFragment(lecturer.getId());
        action.setScheduleId(scheduleId);
        navController.navigate(action);
    }

    private void showDeleteConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.action_delete_lecturer)
                .setMessage(getString(R.string.msg_delete_lecturer_confirmation, lecturer.getFullName()))
                .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteLecturer(lecturer);
                    }
                })
                .setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void showDeleteScheduleConfirmationDialog(final LecturerSchedule schedule) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.action_delete_schedule)
                .setMessage(getString(R.string.msg_delete_lecturer_schedule_confirmation, schedule.getSubject(), lecturer.getFullName()))
                .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteLecturerSchedule(schedule);
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
