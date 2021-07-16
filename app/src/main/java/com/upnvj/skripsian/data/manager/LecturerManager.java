package com.upnvj.skripsian.data.manager;

import androidx.lifecycle.LiveData;

import com.upnvj.skripsian.data.dao.LecturerDao;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.util.AppExecutors;
import com.upnvj.skripsian.util.callback.ResultCallback;

import java.util.List;

public class LecturerManager {

    private static LecturerManager sInstance;

    private AppExecutors executors;
    private LecturerDao lecturerDao;

    private LecturerManager(AppExecutors executors, LecturerDao lecturerDao) {
        this.executors = executors;
        this.lecturerDao = lecturerDao;
    }

    public static LecturerManager getInstance(AppExecutors executors, LecturerDao lecturerDao) {
        if (sInstance == null) {
            synchronized (LecturerManager.class) {
                if (sInstance == null) {
                    sInstance = new LecturerManager(executors, lecturerDao);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Lecturer>> getAllLecturers() {
        return lecturerDao.getObservableAllLecturers();
    }

    public LiveData<Lecturer> getLecturer(long id) {
        return lecturerDao.getObservableLecturer(id);
    }

    public void getLecturer(final long id, final ResultCallback<Lecturer> callback) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Lecturer lecturer = lecturerDao.getLecturer(id);
                if (lecturer != null) callback.onSuccess(lecturer);
                else callback.onError("Data dosen dengan id " + id + " tidak ditemukan!");
            }
        });
    }

    public void saveLecturer(final Lecturer lecturer) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                lecturerDao.insert(lecturer);
            }
        });
    }

    public void deleteLecturer(final Lecturer lecturer) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                lecturerDao.delete(lecturer);
            }
        });
    }

}
