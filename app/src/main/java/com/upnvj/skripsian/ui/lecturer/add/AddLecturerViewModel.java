package com.upnvj.skripsian.ui.lecturer.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.upnvj.skripsian.data.manager.LecturerManager;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.util.callback.ResultCallback;

public class AddLecturerViewModel extends ViewModel {

    public MutableLiveData<String> fullName, nip, studyProgram;

    private LecturerManager lecturerManager;
    private MutableLiveData<Long> lecturerId;

    private MediatorLiveData<AddLecturerViewState> viewState;
    private MutableLiveData<String> eventMessage;
    private MutableLiveData<Boolean> eventLecturerSaved;

    private AddLecturerViewModel(LecturerManager lecturerManager) {
        this.lecturerManager = lecturerManager;
        fullName = new MutableLiveData<>();
        nip = new MutableLiveData<>();
        studyProgram = new MutableLiveData<>();
        lecturerId = new MutableLiveData<>();

        viewState = new MediatorLiveData<>();
        viewState.addSource(fullName, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setNameFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerViewState() : state;
                    state.setNameFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(nip, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setNipFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerViewState() : state;
                    state.setNipFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(studyProgram, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddLecturerViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setStudyProgramFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddLecturerViewState() : state;
                    state.setStudyProgramFilled(false);
                    viewState.setValue(state);
                }
            }
        });

        eventMessage = new MutableLiveData<>();
        eventLecturerSaved = new MutableLiveData<>();
    }

    public void saveLecturer() {
        String textFullName = fullName.getValue(), textNip = nip.getValue(), textStudyProgram = studyProgram.getValue();
        if (textFullName == null || textNip == null || textStudyProgram == null
                || textFullName.isEmpty() || textNip.isEmpty() || textStudyProgram.isEmpty()) {
            eventMessage.setValue("Kolom tidak boleh kosong!");
            eventLecturerSaved.setValue(false);
        } else {
            Long id = lecturerId.getValue();
            if (id == null) id = 0L;
            Lecturer lecturer = new Lecturer(id, textFullName, textNip, textStudyProgram);
            lecturerManager.saveLecturer(lecturer);
            eventLecturerSaved.setValue(true);
        }
    }

    LiveData<AddLecturerViewState> getViewState() {
        return viewState;
    }

    LiveData<String> getEventMessage() {
        return eventMessage;
    }

    LiveData<Boolean> getEventLecturerSaved() {
        return eventLecturerSaved;
    }

    void setLecturerId(long id) {
        lecturerId.setValue(id);
        if (id == 0) {
            // Empty data
            fullName.setValue("");
            nip.setValue("");
            studyProgram.setValue("");
        } else {
            // Updated data
            lecturerManager.getLecturer(id, new ResultCallback<Lecturer>() {
                @Override
                public void onSuccess(Lecturer data) {
                    fullName.postValue(data.getFullName());
                    nip.postValue(data.getNip());
                    studyProgram.postValue(data.getStudyProgram());
                }

                @Override
                public void onError(String message) {
                    fullName.postValue("");
                    nip.postValue("");
                    studyProgram.postValue("");
                }
            });
        }
    }

    void onEventMessageHandled() {
        eventMessage.setValue(null);
    }

    void onEventLecturerSavedHandled() {
        eventLecturerSaved.setValue(null);
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
            return (T) new AddLecturerViewModel(lecturerManager);
        }
    }

}
