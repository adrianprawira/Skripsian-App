package com.upnvj.skripsian.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> addLecturerEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> addThesisScheduleEvent = new MutableLiveData<>();

    public LiveData<Boolean> getAddLecturerEvent() {
        return addLecturerEvent;
    }

    public LiveData<Boolean> getAddThesisScheduleEvent() {
        return addThesisScheduleEvent;
    }

    public void triggerAddLecturerEvent() {
        addLecturerEvent.setValue(true);
    }

    public void triggerAddThesisScheduleEvent() {
        addThesisScheduleEvent.setValue(true);
    }

    public void onAddLecturerEventHandled() {
        addLecturerEvent.setValue(null);
    }

    public void onAddThesisScheduleEventHandled() {
        addThesisScheduleEvent.setValue(null);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel();
        }
    }
}
