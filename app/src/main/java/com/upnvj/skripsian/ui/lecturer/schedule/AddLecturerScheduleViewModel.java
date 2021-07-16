package com.upnvj.skripsian.ui.lecturer.schedule;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.manager.LecturerScheduleManager;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.util.callback.ResultCallback;

public class AddLecturerScheduleViewModel extends ViewModel {

    public MutableLiveData<String> subject, sks, className;
    public MutableLiveData<String> dayOfWeek, timeOfStudyStart, timeOfStudyEnd;

    private LecturerManager lecturerManager;
    private LecturerScheduleManager scheduleManager;

    private MediatorLiveData<AddLecturerScheduleViewState> viewState;
    private MutableLiveData<String> eventMessage;
    private MutableLiveData<Boolean> eventScheduleSaved;

    private long scheduleId, lecturerId;
    private String[] dayOfWeeks, timeOfStudiesWithDetail;

    private AddLecturerScheduleViewModel(LecturerManager lecturerManager, LecturerScheduleManager scheduleManager) {
        this.lecturerManager = lecturerManager;
        this.scheduleManager = scheduleManager;

        dayOfWeek = new MutableLiveData<>();
        timeOfStudyStart = new MutableLiveData<>();
        timeOfStudyEnd = new MutableLiveData<>();
        subject = new MutableLiveData<>();
        sks = new MutableLiveData<>();
        className = new MutableLiveData<>();

        viewState = new MediatorLiveData<>();
        viewState.addSource(subject, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setClassSubjectFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setClassSubjectFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(sks, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setSksFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setSksFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(className, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setClassNameFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setClassNameFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(dayOfWeek, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setDayFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setDayFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(timeOfStudyStart, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setTimeStartFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setTimeStartFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(timeOfStudyEnd, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setTimeEndFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerScheduleViewState() : state;
                    state.setTimeEndFilled(false);
                    viewState.setValue(state);
                }
            }
        });

        eventMessage = new MutableLiveData<>();
        eventScheduleSaved = new MutableLiveData<>();
    }

    public void saveSchedule() {
        String textSubject = subject.getValue();
        String textSks = sks.getValue();
        String textClassName = className.getValue();
        String textDayOfWeek = dayOfWeek.getValue();
        String textTimeStart = timeOfStudyStart.getValue();
        String textTimeEnd = timeOfStudyEnd.getValue();

        int indexDayOfWeek = -1;
        for (int i = 0; i < dayOfWeeks.length; i++) {
            if (dayOfWeeks[i].equals(textDayOfWeek)) {
                indexDayOfWeek = i;
                break;
            }
        }

        int indexTimeStart = -1, indexTimeEnd = -1;
        for (int i = 0; i < timeOfStudiesWithDetail.length; i++) {
            if (timeOfStudiesWithDetail[i].equals(textTimeStart)) {
                indexTimeStart = i;
            }
            if (timeOfStudiesWithDetail[i].equals(textTimeEnd)) {
                indexTimeEnd = i;
            }
        }

        if (textSubject == null || textSks == null || textClassName == null ||
                textSubject.isEmpty() || textSks.isEmpty() || textClassName.isEmpty()) {
            eventMessage.setValue("Kolom tidak boleh kosong!");
            eventScheduleSaved.setValue(false);
        } else if (indexDayOfWeek < 0) {
            eventMessage.setValue("Kolom hari harus dipilih dengan pilihan yg tersedia!");
            eventScheduleSaved.setValue(false);
        } else if (indexTimeStart < 0 || indexTimeEnd < 0) {
            eventMessage.setValue("Kolom jam kuliah harus dipilih dengan pilihan yg tersedia!");
            eventScheduleSaved.setValue(false);
        } else if (indexTimeStart > indexTimeEnd) {
            eventMessage.setValue("Jam mulai kuliah tidak boleh lebih besar dari jam selesai kuliah!");
            eventScheduleSaved.setValue(false);
        } else {
            LecturerSchedule schedule = new LecturerSchedule(scheduleId, lecturerId, indexDayOfWeek, indexTimeStart, indexTimeEnd, textSubject, textSks, textClassName);
            scheduleManager.saveLecturerSchedule(schedule);
            eventScheduleSaved.setValue(true);
        }
    }

    LiveData<Lecturer> getLecturer(long lecturerId) {
        return lecturerManager.getLecturer(lecturerId);
    }

    LiveData<AddLecturerScheduleViewState> getViewState() {
        return viewState;
    }

    LiveData<Boolean> getEventScheduleSaved() {
        return eventScheduleSaved;
    }

    LiveData<String> getEventMessage() {
        return eventMessage;
    }

    void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
        if (scheduleId == 0) {
            // Empty Data
            subject.setValue("");
            sks.setValue("");
            className.setValue("");
        } else {
            // Updated Data
            scheduleManager.getLecturerSchedule(scheduleId, new ResultCallback<LecturerSchedule>() {
                @Override
                public void onSuccess(LecturerSchedule data) {
                    subject.postValue(data.getSubject());
                    sks.postValue(data.getSks());
                    className.postValue(data.getClassName());
                    dayOfWeek.postValue(dayOfWeeks[data.getDayOfWeek()]);
                    timeOfStudyStart.postValue(timeOfStudiesWithDetail[data.getTimeOfStudyStart()]);
                    timeOfStudyEnd.postValue(timeOfStudiesWithDetail[data.getTimeOfStudyEnd()]);
                }

                @Override
                public void onError(String message) {
                    subject.postValue("");
                    sks.postValue("");
                    className.postValue("");
                }
            });
        }
    }

    void setLecturerId(long lecturerId) {
        this.lecturerId = lecturerId;
    }

    void setDayOfWeeks(String[] dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    void setTimeOfStudiesWithDetail(String[] timeOfStudiesWithDetail) {
        this.timeOfStudiesWithDetail = timeOfStudiesWithDetail;
    }

    void onEventScheduleSavedHandled() {
        eventScheduleSaved.setValue(null);
    }

    void onEventMessageHandled() {
        eventMessage.setValue(null);
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
            return (T) new AddLecturerScheduleViewModel(lecturerManager, scheduleManager);
        }
    }

}
