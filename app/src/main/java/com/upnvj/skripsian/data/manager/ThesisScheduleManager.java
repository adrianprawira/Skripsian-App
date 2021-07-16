package com.upnvj.skripsian.data.manager;

import androidx.lifecycle.LiveData;

import com.upnvj.skripsian.data.dao.ThesisScheduleDao;
import com.upnvj.skripsian.data.model.ThesisSchedule;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.util.AppExecutors;
import com.upnvj.skripsian.util.DateTimeUtils;
import com.upnvj.skripsian.util.callback.ResultCallback;

import java.util.ArrayList;
import java.util.List;

public class ThesisScheduleManager {

    private static ThesisScheduleManager sInstance;

    private AppExecutors executors;
    private ThesisScheduleDao thesisScheduleDao;

    private ThesisScheduleManager(AppExecutors executors, ThesisScheduleDao thesisScheduleDao) {
        this.executors = executors;
        this.thesisScheduleDao = thesisScheduleDao;
    }

    public static ThesisScheduleManager getInstance(AppExecutors executors, ThesisScheduleDao lecturerDao) {
        if (sInstance == null) {
            synchronized (ThesisScheduleManager.class) {
                if (sInstance == null) {
                    sInstance = new ThesisScheduleManager(executors, lecturerDao);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<ThesisScheduleWithLecturers>> getAllThesisSchedules() {
        return thesisScheduleDao.getObservableThesisSchedules();
    }

    public LiveData<ThesisScheduleWithLecturers> getThesisSchedule(long id) {
        return thesisScheduleDao.getObservableThesisSchedule(id);
    }

    public void getThesisScheduleWithinDate(final String dateString, final ResultCallback<List<ThesisScheduleWithLecturers>> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ThesisScheduleWithLecturers> schedules = thesisScheduleDao.getThesisSchedules();
                ArrayList<ThesisScheduleWithLecturers> filtered = new ArrayList<>();
                for (ThesisScheduleWithLecturers data : schedules) {
                    ThesisSchedule schedule = data.getSchedule();
                    if (DateTimeUtils.isFromSameDate(schedule.getTimestamp(), dateString)) {
                        filtered.add(data);
                    }
                }
                callback.onSuccess(filtered);
            }
        });
    }

    public void getThesisSchedulesFromLecturersWithinDate(final List<Long> lecturerIds, final long timestamp, final ResultCallback<List<ThesisScheduleWithLecturers>> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ThesisScheduleWithLecturers> schedules = thesisScheduleDao.getThesisSchedulesFromLecturers(lecturerIds);
                ArrayList<ThesisScheduleWithLecturers> filtered = new ArrayList<>();
                for (ThesisScheduleWithLecturers data : schedules) {
                    ThesisSchedule schedule = data.getSchedule();
                    if (DateTimeUtils.isFromSameDate(schedule.getTimestamp(), timestamp)) {
                        filtered.add(data);
                    }
                }
                callback.onSuccess(filtered);
            }
        });
    }

    public void getThesisSchedule(final long id, final ResultCallback<ThesisScheduleWithLecturers> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ThesisScheduleWithLecturers schedule = thesisScheduleDao.getThesisSchedule(id);
                if (schedule != null) callback.onSuccess(schedule);
                else callback.onError("Data jadwal sidang dengan id " + id + " tidak ditemukan!");
            }
        });
    }

    public void saveThesisSchedule(final ThesisSchedule schedule) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                thesisScheduleDao.insert(schedule);
            }
        });
    }

    public void deleteThesisSchedule(final ThesisSchedule schedule) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                thesisScheduleDao.delete(schedule);
            }
        });
    }

}
