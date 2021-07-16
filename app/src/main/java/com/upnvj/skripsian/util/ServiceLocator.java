package com.upnvj.skripsian.util;

import android.content.Context;

import com.upnvj.skripsian.data.AppDatabase;
import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.manager.LecturerScheduleManager;
import com.upnvj.skripsian.data.manager.ThesisScheduleManager;
import com.upnvj.skripsian.ui.home.HomeViewModel;
import com.upnvj.skripsian.ui.lecturer.LecturerViewModel;
import com.upnvj.skripsian.ui.lecturer.add.AddLecturerViewModel;
import com.upnvj.skripsian.ui.lecturer.detail.DetailLecturerViewModel;
import com.upnvj.skripsian.ui.lecturer.schedule.AddLecturerScheduleViewModel;
import com.upnvj.skripsian.ui.thesis.ThesisViewModel;
import com.upnvj.skripsian.ui.thesis.add.AddThesisScheduleViewModel;
import com.upnvj.skripsian.ui.thesis.list.ListThesisViewModel;
import com.upnvj.skripsian.vm.MainViewModel;

public class ServiceLocator {

    private static AppDatabase appDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    private static AppExecutors appExecutors() {
        return new AppExecutors();
    }

    private static LecturerManager lecturerManager(Context context) {
        AppDatabase appDb = appDatabase(context);
        return LecturerManager.getInstance(
                appExecutors(),
                appDb.lecturerDao()
        );
    }

    private static LecturerScheduleManager lecturerScheduleManager(Context context) {
        AppDatabase appDb = appDatabase(context);
        return LecturerScheduleManager.getInstance(
                appExecutors(),
                appDb.lecturerScheduleDao()
        );
    }

    private static ThesisScheduleManager thesisScheduleManager(Context context) {
        AppDatabase appDb = appDatabase(context);
        return ThesisScheduleManager.getInstance(
                appExecutors(),
                appDb.thesisScheduleDao()
        );
    }

    public static MainViewModel.Factory mainFactory() {
        return new MainViewModel.Factory();
    }

    public static HomeViewModel.Factory homeFactory(Context context) {
        return new HomeViewModel.Factory(lecturerManager(context), thesisScheduleManager(context));
    }

    public static LecturerViewModel.Factory lecturerFactory(Context context) {
        return new LecturerViewModel.Factory(lecturerManager(context));
    }

    public static AddLecturerViewModel.Factory addLecturerFactory(Context context) {
        return new AddLecturerViewModel.Factory(lecturerManager(context));
    }

    public static DetailLecturerViewModel.Factory detailLecturerFactory(Context context) {
        return new DetailLecturerViewModel.Factory(lecturerManager(context), lecturerScheduleManager(context));
    }

    public static AddLecturerScheduleViewModel.Factory addLecturerScheduleFactory(Context context) {
        return new AddLecturerScheduleViewModel.Factory(lecturerManager(context), lecturerScheduleManager(context));
    }

    public static ThesisViewModel.Factory thesisFactory(Context context) {
        return new ThesisViewModel.Factory(thesisScheduleManager(context));
    }

    public static ListThesisViewModel.Factory listThesisFactory(Context context) {
        return new ListThesisViewModel.Factory(thesisScheduleManager(context));
    }

    public static AddThesisScheduleViewModel.Factory addThesisScheduleFactory(Context context) {
        return new AddThesisScheduleViewModel.Factory(lecturerManager(context), lecturerScheduleManager(context), thesisScheduleManager(context));
    }

}
