package com.upnvj.skripsian.ui.lecturer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.model.Lecturer;

import java.util.List;

public class LecturerViewModel extends ViewModel {

    private LecturerManager lecturerManager;
    private MutableLiveData<Boolean> loadingState;

    private LecturerViewModel(LecturerManager lecturerManager) {
        this.lecturerManager = lecturerManager;
        loadingState = new MutableLiveData<>();
    }

    public void refreshList() {
        loadingState.setValue(false);
    }

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    LiveData<List<Lecturer>> getAllLecturers() {
        return lecturerManager.getAllLecturers();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private LecturerManager lecturerManager;

        public Factory(LecturerManager lecturerManager) {
            this.lecturerManager = lecturerManager;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LecturerViewModel(lecturerManager);
        }
    }

}
