package com.upnvj.skripsian.ui.thesis.list;

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
import java.util.List;

public class ListThesisViewModel extends ViewModel {

    private ThesisScheduleManager scheduleManager;

    private MutableLiveData<ListThesisFilter> timeFilter;
    private MutableLiveData<Boolean> eventScheduleDeleted;

    private String dateFilter;
    private String[] timeStudies, timeStudyDetails;

    private ListThesisViewModel(ThesisScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
        timeFilter = new MutableLiveData<>();
        eventScheduleDeleted = new MutableLiveData<>();
    }

    LiveData<List<ListThesisFilter>> getTimeFilterList() {
        return Transformations.switchMap(timeFilter, new Function<ListThesisFilter, LiveData<List<ListThesisFilter>>>() {
            @Override
            public LiveData<List<ListThesisFilter>> apply(final ListThesisFilter selectedFilter) {
                return Transformations.map(scheduleManager.getAllThesisSchedules(), new Function<List<ThesisScheduleWithLecturers>, List<ListThesisFilter>>() {
                    @Override
                    public List<ListThesisFilter> apply(List<ThesisScheduleWithLecturers> input) {
                        List<ListThesisFilter> timeFilterList = new ArrayList<>();
                        for (int i = 0; i < timeStudies.length; i++) {
                            ArrayList<ThesisScheduleWithLecturers> filtered = new ArrayList<>();
                            for (ThesisScheduleWithLecturers data : input) {
                                ThesisSchedule s = data.getSchedule();
                                if (DateTimeUtils.isFromSameDate(s.getTimestamp(), dateFilter) && s.getTimeOfStudyStart() == i) {
                                    filtered.add(data);
                                }
                            }
                            boolean isChecked = selectedFilter.getIndexTime() == i;
                            ListThesisFilter filter = new ListThesisFilter(i, timeStudies[i], timeStudyDetails[i], isChecked, filtered);
                            timeFilterList.add(filter);
                        }
                        return timeFilterList;
                    }
                });
            }
        });
    }

    LiveData<List<ThesisScheduleWithLecturers>> getFilteredSchedules() {
        return Transformations.switchMap(timeFilter, new Function<ListThesisFilter, LiveData<List<ThesisScheduleWithLecturers>>>() {
            @Override
            public LiveData<List<ThesisScheduleWithLecturers>> apply(final ListThesisFilter filter) {
                return Transformations.map(scheduleManager.getAllThesisSchedules(), new Function<List<ThesisScheduleWithLecturers>, List<ThesisScheduleWithLecturers>>() {
                    @Override
                    public List<ThesisScheduleWithLecturers> apply(List<ThesisScheduleWithLecturers> input) {
                        ArrayList<ThesisScheduleWithLecturers> list = new ArrayList<>();
                        for (ThesisScheduleWithLecturers data : input) {
                            if (DateTimeUtils.isFromSameDate(data.getSchedule().getTimestamp(), dateFilter) && data.getSchedule().getTimeOfStudyStart() == filter.getIndexTime()) {
                                list.add(data);
                            }
                        }
                        return list;
                    }
                });
            }
        });
    }

    LiveData<Boolean> getEventScheduleDeleted() {
        return eventScheduleDeleted;
    }

    void deleteSchedule(ThesisScheduleWithLecturers schedule) {
        scheduleManager.deleteThesisSchedule(schedule.getSchedule());
        eventScheduleDeleted.setValue(true);
    }

    void setDateFilter(String dateFilter) {
        this.dateFilter = dateFilter;
    }

    void setTimeFilter(ListThesisFilter timeFilter) {
        this.timeFilter.setValue(timeFilter);
    }

    void setTimeFilterList(final String[] timeStudies, final String[] timeStudyDetails) {
        this.timeStudies = timeStudies;
        this.timeStudyDetails = timeStudyDetails;
        int i = 0;
        timeFilter.setValue(new ListThesisFilter(i, timeStudies[i], timeStudyDetails[i], false, new ArrayList<ThesisScheduleWithLecturers>()));
    }

    void onEventScheduleDeletedHandled() {
        eventScheduleDeleted.setValue(null);
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
            return (T) new ListThesisViewModel(scheduleManager);
        }
    }

}
