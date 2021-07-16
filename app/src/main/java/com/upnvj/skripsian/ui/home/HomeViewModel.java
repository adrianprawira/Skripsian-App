package com.upnvj.skripsian.ui.home;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.manager.ThesisScheduleManager;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private LecturerManager lecturerManager;
    private ThesisScheduleManager scheduleManager;

    private HomeViewModel(LecturerManager lecturerManager, ThesisScheduleManager scheduleManager) {
        this.lecturerManager = lecturerManager;
        this.scheduleManager = scheduleManager;
    }

    LiveData<String> getLecturerCount() {
        return Transformations.map(lecturerManager.getAllLecturers(), new Function<List<Lecturer>, String>() {
            @Override
            public String apply(List<Lecturer> input) {
                return input.isEmpty() ? "0" : input.size() + "";
            }
        });
    }

    LiveData<String> getTotalSchedule() {
        return Transformations.map(scheduleManager.getAllThesisSchedules(), new Function<List<ThesisScheduleWithLecturers>, String>() {
            @Override
            public String apply(List<ThesisScheduleWithLecturers> input) {
                return input.isEmpty() ? "0" : input.size() + "";
            }
        });
    }

    LiveData<List<ThesisScheduleWithLecturers>> getTodaySchedules() {
        return Transformations.map(scheduleManager.getAllThesisSchedules(), new Function<List<ThesisScheduleWithLecturers>, List<ThesisScheduleWithLecturers>>() {
            @Override
            public List<ThesisScheduleWithLecturers> apply(List<ThesisScheduleWithLecturers> list) {
                ArrayList<ThesisScheduleWithLecturers> schedules = new ArrayList<>();
                long currentTimestamp = new Date().getTime();
                for (ThesisScheduleWithLecturers data : list) {
                    if (DateTimeUtils.isFromSameDate(currentTimestamp, data.getSchedule().getTimestamp())) {
                        schedules.add(data);
                    }
                }
                Collections.sort(schedules, new Comparator<ThesisScheduleWithLecturers>() {
                    @Override
                    public int compare(ThesisScheduleWithLecturers t1, ThesisScheduleWithLecturers t2) {
                        return Integer.compare(t1.getSchedule().getTimeOfStudyStart(), t2.getSchedule().getTimeOfStudyStart());
                    }
                });
                return schedules;
            }
        });
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private LecturerManager lecturerManager;
        private ThesisScheduleManager scheduleManager;

        public Factory(LecturerManager lecturerManager, ThesisScheduleManager scheduleManager) {
            this.lecturerManager = lecturerManager;
            this.scheduleManager = scheduleManager;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(lecturerManager, scheduleManager);
        }
    }

}