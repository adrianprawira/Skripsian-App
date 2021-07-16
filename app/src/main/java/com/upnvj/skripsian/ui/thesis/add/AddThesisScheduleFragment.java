package com.upnvj.skripsian.ui.thesis.add;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.upnvj.skripsian.MainActivity;
import com.upnvj.skripsian.R;
import com.upnvj.skripsian.databinding.FragmentAddThesisScheduleBinding;
import com.upnvj.skripsian.util.DateTimeUtils;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.Toaster;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddThesisScheduleFragment extends Fragment {

    private static final String TAG_DATE_PICKER = "ADD_THESIS_DATE_PICKER_DIALOG";
    private NavController navController;
    private FragmentAddThesisScheduleBinding binding;
    private AddThesisScheduleViewModel viewModel;

    private String[] timeOfStudiesWithDetail;

    public AddThesisScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_thesis_schedule, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.addThesisScheduleFactory(requireContext())).get(AddThesisScheduleViewModel.class);

        timeOfStudiesWithDetail = getTimeOfStudiesWithDetails();
        viewModel.setTimeOfStudiesWithDetail(timeOfStudiesWithDetail);

        binding.setView(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AddThesisScheduleFragmentArgs safeArgs = AddThesisScheduleFragmentArgs.fromBundle(getArguments());
        int titleRes = safeArgs.getScheduleId() == 0 ? R.string.title_add_thesis_schedule : R.string.title_edit_thesis_schedule;
        ((MainActivity) requireActivity()).setToolbarTitle(titleRes);

        ArrayAdapter<String> adapterTimes = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, timeOfStudiesWithDetail);
        binding.choiceTime.setAdapter(adapterTimes);
        binding.choiceTime.setThreshold(100);
        binding.choiceLecturerSupervisor.setThreshold(100);
        binding.choiceLecturerExaminer1.setThreshold(100);
        binding.choiceLecturerExaminer2.setThreshold(100);

        long scheduleId = safeArgs.getScheduleId();
        viewModel.setScheduleId(scheduleId);
        if (scheduleId == 0) viewModel.setDayDate(safeArgs.getDayDateTimestamp());
        viewModel.getLecturerNameList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                ArrayAdapter<String> adapterSupervisors = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, names);
                binding.choiceLecturerSupervisor.setAdapter(adapterSupervisors);

                ArrayAdapter<String> adapterExaminers1 = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, names);
                binding.choiceLecturerExaminer1.setAdapter(adapterExaminers1);

                ArrayAdapter<String> adapterExaminers2 = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, names);
                binding.choiceLecturerExaminer2.setAdapter(adapterExaminers2);
            }
        });
        viewModel.getViewState().observe(getViewLifecycleOwner(), new Observer<AddThesisScheduleViewState>() {
            @Override
            public void onChanged(AddThesisScheduleViewState addThesisScheduleViewState) {
                binding.setState(addThesisScheduleViewState);
            }
        });
        viewModel.getEventScheduleSaved().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_thesis_schedule_saved);
                    viewModel.onEventScheduleSavedHandled();
                    navController.navigateUp();
                }
            }
        });
        viewModel.getEventMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    showMessageDialog(message);
                    viewModel.onEventMessageHandled();
                }
            }
        });
    }

    public void showDatePickerDialog() {
        buildDatePicker().show(getChildFragmentManager(), TAG_DATE_PICKER);
    }

    private String[] getTimeOfStudiesWithDetails() {
        String[] timeOfStudies = getResources().getStringArray(R.array.time_of_studies);
        String[] timeOfStudyDetails = getResources().getStringArray(R.array.time_of_study_details);
        for (int i = 0; i < timeOfStudies.length; i++) {
            timeOfStudies[i] = timeOfStudies[i] + " (" + timeOfStudyDetails[i] + ")";
        }
        return timeOfStudies;
    }

    private void showMessageDialog(String message) {
        if (isVisible()) {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.title_error_message)
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private MaterialDatePicker buildDatePicker() {
        CalendarConstraints.DateValidator validator = new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return DateTimeUtils.isWeekdays(date);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
            }
        };
        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(validator)
                .build();
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> dialog = builder.setCalendarConstraints(constraints)
                .setTheme(R.style.ThemeOverlay_Skripsian_Calendar)
                .setTitleText(R.string.plain_thesis_daydate)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        dialog.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                viewModel.setDayDate(selection);
            }
        });
        return dialog;
    }

}
