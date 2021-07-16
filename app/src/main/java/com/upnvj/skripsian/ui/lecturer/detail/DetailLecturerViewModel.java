package com.upnvj.skripsian.ui.lecturer.detail;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.manager.LecturerScheduleManager;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.LecturerSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewData.ScheduleFilter.DAY_1;
import static com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewData.ScheduleFilter.DAY_2;
import static com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewData.ScheduleFilter.DAY_3;
import static com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewData.ScheduleFilter.DAY_4;
import static com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewData.ScheduleFilter.DAY_5;

public class DetailLecturerViewModel extends ViewModel {

    private LecturerManager lecturerManager;
    private LecturerScheduleManager scheduleManager;

    private MutableLiveData<DetailLecturerViewData.ScheduleFilter> scheduleFilter;
    private MediatorLiveData<DetailLecturerViewData> viewData;
    private MutableLiveData<Boolean> eventDeleted, eventScheduleDeleted;

    private DetailLecturerViewModel(LecturerManager lecturerManager, LecturerScheduleManager scheduleManager) {
        this.lecturerManager = lecturerManager;
        this.scheduleManager = scheduleManager;

        scheduleFilter = new MutableLiveData<>();
        scheduleFilter.setValue(DAY_1);

        viewData = new MediatorLiveData<>();
        eventDeleted = new MutableLiveData<>();
        eventScheduleDeleted = new MutableLiveData<>();
    }

    public LiveData<DetailLecturerViewData.ScheduleFilter> getScheduleFilter() {
        return scheduleFilter;
    }

    public void setScheduleFilter(DetailLecturerViewData.ScheduleFilter filter) {
        scheduleFilter.setValue(filter);
    }

    LiveData<Lecturer> getDetailLecturer(long id) {
        return lecturerManager.getLecturer(id);
    }

    LiveData<List<LecturerSchedule>> getLecturerSchedules(final long lecturerId) {
        LiveData<List<LecturerSchedule>> transformed = Transformations.switchMap(scheduleFilter, new Function<DetailLecturerViewData.ScheduleFilter, LiveData<List<LecturerSchedule>>>() {
            @Override
            public LiveData<List<LecturerSchedule>> apply(final DetailLecturerViewData.ScheduleFilter filter) {
                return Transformations.map(scheduleManager.getLecturerSchedules(lecturerId), new Function<List<LecturerSchedule>, List<LecturerSchedule>>() {
                    @Override
                    public List<LecturerSchedule> apply(List<LecturerSchedule> list) {
                        ArrayList<LecturerSchedule> filtered = new ArrayList<>();
                        for (LecturerSchedule schedule : list) {
                            if (schedule.getDayOfWeek() == filter.index) filtered.add(schedule);
                        }
                        Collections.sort(filtered, new Comparator<LecturerSchedule>() {
                            @Override
                            public int compare(LecturerSchedule t1, LecturerSchedule t2) {
                                int start1 = t1.getTimeOfStudyStart(), start2 = t2.getTimeOfStudyStart();
                                return Integer.compare(start1, start2);
                            }
                        });
                        return filtered;
                    }
                });
            }
        });
        viewData.addSource(scheduleManager.getLecturerSchedules(lecturerId), new Observer<List<LecturerSchedule>>() {
            @Override
            public void onChanged(List<LecturerSchedule> lecturerSchedules) {
                DetailLecturerViewData data = new DetailLecturerViewData();
                int countMon = 0, countTue = 0, countWed = 0, countThu = 0, countFri = 0;
                for (LecturerSchedule s : lecturerSchedules) {
                    if (s.getDayOfWeek() == DAY_1.index) countMon++;
                    else if (s.getDayOfWeek() == DAY_2.index) countTue++;
                    else if (s.getDayOfWeek() == DAY_3.index) countWed++;
                    else if (s.getDayOfWeek() == DAY_4.index) countThu++;
                    else if (s.getDayOfWeek() == DAY_5.index) countFri++;
                }
                /*if (countMon != 0)*/
                data.setCountScheduleMonday(countMon + "");
                /*if (countTue != 0)*/
                data.setCountScheduleTuesday(countTue + "");
                /*if (countWed != 0)*/
                data.setCountScheduleWednesday(countWed + "");
                /*if (countThu != 0)*/
                data.setCountScheduleThursday(countThu + "");
                /*if (countFri != 0)*/
                data.setCountScheduleFriday(countFri + "");
                viewData.setValue(data);
            }
        });
        return transformed;
    }

    LiveData<DetailLecturerViewData> getViewData() {
        return viewData;
    }

    LiveData<Boolean> getEventDeleted() {
        return eventDeleted;
    }

    LiveData<Boolean> getEventScheduleDeleted() {
        return eventScheduleDeleted;
    }

    void deleteLecturer(Lecturer lecturer) {
        lecturerManager.deleteLecturer(lecturer);
        eventDeleted.setValue(true);
    }

    void deleteLecturerSchedule(LecturerSchedule schedule) {
        scheduleManager.deleteLecturerSchedule(schedule);
        eventScheduleDeleted.setValue(true);
    }

    void onEventDeletedHandled() {
        eventDeleted.setValue(null);
    }

    void onEventScheduleDeletedHandled() {
        eventScheduleDeleted.setValue(null);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private LecturerManager lecturerManager;
        private LecturerScheduleManager scheduleManager;

        public Factory(LecturerManager lecturerManager, LecturerScheduleManager scheduleManager) {
            this.lecturerManager = lecturerManager;
            this.scheduleManager = scheduleManager;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DetailLecturerViewModel(lecturerManager, scheduleManager);
        }
    }

}
