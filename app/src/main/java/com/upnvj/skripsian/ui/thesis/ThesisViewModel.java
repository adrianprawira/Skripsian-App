package com.upnvj.skripsian.ui.thesis;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.ThesisScheduleManager;
import com.upnvj.skripsian.data.model.ThesisSchedule;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThesisViewModel extends ViewModel {

    private ThesisScheduleManager scheduleManager;
    private MutableLiveData<Boolean> loadingState;

    private ThesisViewModel(ThesisScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
        loadingState = new MutableLiveData<>();
    }

    public void refreshList() {
        loadingState.setValue(false);
    }

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    LiveData<List<ThesisScheduleGroup>> getThesisScheduleGroups() {
        return Transformations.map(scheduleManager.getAllThesisSchedules(), new Function<List<ThesisScheduleWithLecturers>, List<ThesisScheduleGroup>>() {
            @Override
            public List<ThesisScheduleGroup> apply(List<ThesisScheduleWithLecturers> input) {
                List<ThesisScheduleGroup> groups = groupScheduleByDate(input);
                Collections.sort(groups, new Comparator<ThesisScheduleGroup>() {
                    @Override
                    public int compare(ThesisScheduleGroup t1, ThesisScheduleGroup t2) {
                        return t1.getDateString().compareTo(t2.getDateString());
                    }
                });
                return groups;
            }
        });
    }

    private List<ThesisScheduleGroup> groupScheduleByDate(List<ThesisScheduleWithLecturers> schedules) {
        ArrayList<ThesisScheduleGroup> groups = new ArrayList<>();
        for (ThesisScheduleWithLecturers data : schedules) {
            ThesisSchedule schedule = data.getSchedule();
            String dateString = DateTimeUtils.formatDate(schedule.getTimestamp());
            ThesisScheduleGroup currentGroup = null;
            for (ThesisScheduleGroup group : groups) {
                if (dateString.equals(group.getDateString())) {
                    currentGroup = group;
                    break;
                }
            }

            // Check already exists or not
            if (currentGroup == null) {
                currentGroup = new ThesisScheduleGroup(dateString, 1);
                groups.add(currentGroup);
            } else {
                groups.remove(currentGroup);
                int count = currentGroup.getSchedulesCount();
                currentGroup.setSchedulesCount(count + 1);
                groups.add(currentGroup);
            }
        }
        return groups;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private ThesisScheduleManager scheduleManager;

        public Factory(ThesisScheduleManager scheduleManager) {
            this.scheduleManager = scheduleManager;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ThesisViewModel(scheduleManager);
        }
    }

}
