package com.upnvj.skripsian.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.upnvj.skripsian.BuildConfig;
import com.upnvj.skripsian.data.dao.LecturerDao;
import com.upnvj.skripsian.data.dao.LecturerScheduleDao;
import com.upnvj.skripsian.data.dao.ThesisScheduleDao;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.data.model.ThesisSchedule;

@Database(
        entities = {Lecturer.class, LecturerSchedule.class, ThesisSchedule.class},
        version = BuildConfig.DB_VERSION,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, BuildConfig.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract LecturerDao lecturerDao();

    public abstract LecturerScheduleDao lecturerScheduleDao();

    public abstract ThesisScheduleDao thesisScheduleDao();

}
