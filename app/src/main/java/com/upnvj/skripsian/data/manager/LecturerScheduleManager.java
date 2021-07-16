package com.upnvj.skripsian.data.manager;

import androidx.lifecycle.LiveData;

import com.upnvj.skripsian.data.dao.LecturerScheduleDao;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.data.model.LecturerScheduleWithLecturer;
import com.upnvj.skripsian.util.AppExecutors;
import com.upnvj.skripsian.util.callback.ResultCallback;

import java.util.List;

public class LecturerScheduleManager {

    private static LecturerScheduleManager sInstance;

    private AppExecutors executors;
    private LecturerScheduleDao scheduleDao;

    private LecturerScheduleManager(AppExecutors executors, LecturerScheduleDao scheduleDao) {
        this.executors = executors;
        this.scheduleDao = scheduleDao;
    }

    public static LecturerScheduleManager getInstance(AppExecutors executors, LecturerScheduleDao scheduleDao) {
        if (sInstance == null) {
            synchronized (LecturerScheduleManager.class) {
                if (sInstance == null) {
                    sInstance = new LecturerScheduleManager(executors, scheduleDao);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<LecturerSchedule>> getLecturerSchedules(long lectureId) {
        return scheduleDao.getObservableLecturerSchedules(lectureId);
    }

    public LiveData<LecturerSchedule> getLecturerSchedule(long id) {
        return scheduleDao.getObservableLecturerSchedule(id);
    }

    public void getLecturerSchedules(final List<Long> ids, final ResultCallback<List<LecturerScheduleWithLecturer>> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<LecturerScheduleWithLecturer> schedules = scheduleDao.getLecturerSchedules(ids);
                if (schedules != null) callback.onSuccess(schedules);
                else callback.onError("Data jadwal dosen tidak ditemukan!");
            }
        });
    }

    public void getLecturerSchedule(final long id, final ResultCallback<LecturerSchedule> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LecturerSchedule schedule = scheduleDao.getLecturerSchedule(id);
                if (schedule != null) callback.onSuccess(schedule);
                else callback.onError("Data jadwal dosen dengan id " + id + " tidak ditemukan!");
            }
        });
    }

    public void saveLecturerSchedule(final LecturerSchedule schedule) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                scheduleDao.insert(schedule);
            }
        });
    }

    public void deleteLecturerSchedule(final LecturerSchedule schedule) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                scheduleDao.delete(schedule);
            }
        });
    }

}
