package com.upnvj.skripsian.ui.lecturer.schedule;

import android.os.Bundle;
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

import com.upnvj.skripsian.R;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.databinding.FragmentAddLecturerScheduleBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.util.Toaster;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLecturerScheduleFragment extends Fragment {

    private NavController navController;
    private FragmentAddLecturerScheduleBinding binding;
    private AddLecturerScheduleViewModel viewModel;

    private String[] dayOfWeeks, timeOfStudiesWithDetail;

    public AddLecturerScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_lecturer_schedule, container, false);
        viewModel = new ViewModelProvider(this, ServiceLocator.addLecturerScheduleFactory(requireContext())).get(AddLecturerScheduleViewModel.class);

        dayOfWeeks = getResources().getStringArray(R.array.available_day_of_week_names);
        timeOfStudiesWithDetail = getTimeOfStudiesWithDetails();

        viewModel.setDayOfWeeks(dayOfWeeks);
        viewModel.setTimeOfStudiesWithDetail(timeOfStudiesWithDetail);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddLecturerScheduleFragmentArgs safeArgs = AddLecturerScheduleFragmentArgs.fromBundle(getArguments());
        navController = Navigation.findNavController(view);

        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, dayOfWeeks);
        binding.choiceDay.setAdapter(adapterDays);
        binding.choiceDay.setThreshold(100);

        ArrayAdapter<String> adapterTimeStart = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, timeOfStudiesWithDetail);
        binding.choiceTimeStart.setAdapter(adapterTimeStart);
        binding.choiceDay.setThreshold(100);

        ArrayAdapter<String> adapterTimeEnd = new ArrayAdapter<>(requireContext(), R.layout.item_drop_down_menu, timeOfStudiesWithDetail);
        binding.choiceTimeEnd.setAdapter(adapterTimeEnd);
        binding.choiceDay.setThreshold(100);

        viewModel.setScheduleId(safeArgs.getScheduleId());
        viewModel.setLecturerId(safeArgs.getLecturerId());
        viewModel.getLecturer(safeArgs.getLecturerId()).observe(getViewLifecycleOwner(), new Observer<Lecturer>() {
            @Override
            public void onChanged(Lecturer lecturer) {
                binding.setLecturer(lecturer);
            }
        });
        viewModel.getViewState().observe(getViewLifecycleOwner(), new Observer<AddLecturerScheduleViewState>() {
            @Override
            public void onChanged(AddLecturerScheduleViewState state) {
                binding.setViewState(state);
            }
        });
        viewModel.getEventScheduleSaved().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEventAvailable) {
                if (isEventAvailable != null && isEventAvailable) {
                    Toaster.shortLength(requireContext(), R.string.msg_lecturer_schedule_saved);
                    viewModel.onEventScheduleSavedHandled();
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

    private String[] getTimeOfStudiesWithDetails() {
        String[] timeOfStudies = getResources().getStringArray(R.array.time_of_studies);
        String[] timeOfStudyDetails = getResources().getStringArray(R.array.time_of_study_details);
        for (int i = 0; i < timeOfStudies.length; i++) {
            timeOfStudies[i] = timeOfStudies[i] + " (" + timeOfStudyDetails[i] + ")";
        }
        return timeOfStudies;
    }

}
