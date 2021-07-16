package com.upnvj.skripsian.ui.thesis.add;

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
import com.upnvj.skripsian.data.manager.ThesisScheduleManager;
import com.upnvj.skripsian.data.model.Lecturer;
import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.data.model.LecturerScheduleWithLecturer;
import com.upnvj.skripsian.data.model.ThesisSchedule;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;
import com.upnvj.skripsian.util.DateTimeUtils;
import com.upnvj.skripsian.util.callback.ResultCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddThesisScheduleViewModel extends ViewModel {

    public MutableLiveData<String> thesisTitle, studentName, studentNim, studyProgram, phoneNumber;
    public MutableLiveData<String> lecturerSupervisorName, lecturerExaminer1Name, lecturerExaminer2Name, time;

    private LecturerManager lecturerManager;
    private LecturerScheduleManager lecturerScheduleManager;
    private ThesisScheduleManager thesisScheduleManager;

    private MediatorLiveData<AddThesisScheduleViewState> viewState;
    private MutableLiveData<String> dayDateDisplay;
    private MutableLiveData<String> eventMessage;
    private MutableLiveData<Boolean> eventScheduleSaved;

    private long scheduleId, timestampDayDate;
    private List<Lecturer> lecturerList;
    private String[] timeOfStudiesWithDetail;

    private AddThesisScheduleViewModel(LecturerManager lecturerManager, LecturerScheduleManager lecturerScheduleManager, ThesisScheduleManager thesisScheduleManager) {
        this.lecturerManager = lecturerManager;
        this.lecturerScheduleManager = lecturerScheduleManager;
        this.thesisScheduleManager = thesisScheduleManager;

        thesisTitle = new MutableLiveData<>();
        studentName = new MutableLiveData<>();
        studentNim = new MutableLiveData<>();
        studyProgram = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        lecturerSupervisorName = new MutableLiveData<>();
        lecturerExaminer1Name = new MutableLiveData<>();
        lecturerExaminer2Name = new MutableLiveData<>();
        dayDateDisplay = new MutableLiveData<>();
        time = new MutableLiveData<>();

        viewState = new MediatorLiveData<>();
        viewState.addSource(thesisTitle, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setThesisTitleFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setThesisTitleFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(studentName, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setStudentNameFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setStudentNameFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(studentNim, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setNimFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setNimFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(studyProgram, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setStudyProgramFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setStudyProgramFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(phoneNumber, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setPhoneFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setPhoneFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(lecturerSupervisorName, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setSupervisorFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setSupervisorFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(lecturerExaminer1Name, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setExaminer1Filled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setExaminer1Filled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(lecturerExaminer2Name, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setExaminer2Filled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setExaminer2Filled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(dayDateDisplay, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setDayDateFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setDayDateFilled(false);
                    viewState.setValue(state);
                }
            }
        });
        viewState.addSource(time, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                AddThesisScheduleViewState state = viewState.getValue();
                if (state != null && s != null && !s.trim().isEmpty()) {
                    state.setTimeFilled(true);
                    viewState.setValue(state);
                } else {
                    state = state == null ? new AddThesisScheduleViewState() : state;
                    state.setTimeFilled(false);
                    viewState.setValue(state);
                }
            }
        });

        eventMessage = new MutableLiveData<>();
        eventScheduleSaved = new MutableLiveData<>();
    }

    public void validateSchedule() {
        String textTitle = thesisTitle.getValue();
        String textName = studentName.getValue();
        String textNim = studentNim.getValue();
        String textProgram = studyProgram.getValue();
        String textPhone = phoneNumber.getValue();
        String textSupervisor = lecturerSupervisorName.getValue();
        String textExaminer1 = lecturerExaminer1Name.getValue();
        String textExaminer2 = lecturerExaminer2Name.getValue();
        String textTime = time.getValue();

        int indexTime = -1;
        for (int i = 0; i < timeOfStudiesWithDetail.length; i++) {
            if (timeOfStudiesWithDetail[i].equals(textTime)) {
                indexTime = i;
            }
        }

        long supervisorId = -1, examiner1Id = -1, examiner2Id = -1;
        for (Lecturer lecturer : lecturerList) {
            if (lecturer.getFullName().equals(textSupervisor)) {
                supervisorId = lecturer.getId();
            }
            if (lecturer.getFullName().equals(textExaminer1)) {
                examiner1Id = lecturer.getId();
            }
            if (lecturer.getFullName().equals(textExaminer2)) {
                examiner2Id = lecturer.getId();
            }
        }

        // Check nullable data
        if (textTitle == null || textName == null || textNim == null ||
                textProgram == null || textPhone == null || textTitle.isEmpty() ||
                textName.isEmpty() || textNim.isEmpty() || textProgram.isEmpty() || textPhone.isEmpty()
        ) {
            eventMessage.setValue("Kolom tidak boleh kosong!");
            eventScheduleSaved.setValue(false);
        } else if (timestampDayDate == -1) {
            eventMessage.setValue("Kolom hari dan tanggal harus diisi!");
            eventScheduleSaved.setValue(false);
        } else if (indexTime == -1) {
            eventMessage.setValue("Waktu sidang harus diisi dengan pilihan yg tersedia!");
            eventScheduleSaved.setValue(false);
        } else if (supervisorId == -1 || examiner1Id == -1 || examiner2Id == -1) {
            eventMessage.setValue("Data dosen harus diisi dengan pilihan yg tersedia!");
            eventScheduleSaved.setValue(false);
        } else if (supervisorId == examiner1Id || supervisorId == examiner2Id || examiner1Id == -examiner2Id) {
            eventMessage.setValue("Dosen pembimbing dan penguji tidak boleh orang yang sama!");
            eventScheduleSaved.setValue(false);
        } else {
            // Check conflict schedule
            final ThesisSchedule schedule = new ThesisSchedule(scheduleId, supervisorId, examiner1Id, examiner2Id, timestampDayDate, indexTime, textName, textNim, textProgram, textTitle, textPhone);
            ArrayList<Long> lecturerIds = new ArrayList<>();
            lecturerIds.add(supervisorId);
            lecturerIds.add(examiner1Id);
            lecturerIds.add(examiner2Id);
            lecturerScheduleManager.getLecturerSchedules(lecturerIds, new ResultCallback<List<LecturerScheduleWithLecturer>>() {
                @Override
                public void onSuccess(List<LecturerScheduleWithLecturer> data) {
                    checkConflict(schedule, data);
                }

                @Override
                public void onError(String message) {
                    eventMessage.postValue(message);
                    eventScheduleSaved.postValue(false);
                }
            });
        }
    }

    public LiveData<String> getDayDateDisplay() {
        return dayDateDisplay;
    }

    LiveData<List<String>> getLecturerNameList() {
        return Transformations.map(lecturerManager.getAllLecturers(), new Function<List<Lecturer>, List<String>>() {
            @Override
            public List<String> apply(List<Lecturer> input) {
                lecturerList = input;
                ArrayList<String> names = new ArrayList<>();
                for (Lecturer lecturer : lecturerList) {
                    names.add(lecturer.getFullName());
                }
                Collections.sort(names, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareTo(s2);
                    }
                });
                return names;
            }
        });
    }

    LiveData<AddThesisScheduleViewState> getViewState() {
        return viewState;
    }

    LiveData<String> getEventMessage() {
        return eventMessage;
    }

    LiveData<Boolean> getEventScheduleSaved() {
        return eventScheduleSaved;
    }

    void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
        if (scheduleId == 0) {
            // Empty Data
            thesisTitle.setValue("");
            studentName.setValue("");
            studentNim.setValue("");
            studyProgram.setValue("");
            phoneNumber.setValue("");
            dayDateDisplay.setValue("");
            time.setValue("");
        } else {
            // Updated Data
            thesisScheduleManager.getThesisSchedule(scheduleId, new ResultCallback<ThesisScheduleWithLecturers>() {
                @Override
                public void onSuccess(ThesisScheduleWithLecturers data) {
                    ThesisSchedule schedule = data.getSchedule();
                    thesisTitle.postValue(schedule.getThesisTitle());
                    studentName.postValue(schedule.getFullName());
                    studentNim.postValue(schedule.getNim());
                    studyProgram.postValue(schedule.getStudyProgram());
                    phoneNumber.postValue(schedule.getPhoneNumber());

                    Lecturer supervisor = data.getSupervisor();
                    if (supervisor != null) {
                        lecturerSupervisorName.postValue(supervisor.getFullName());
                    } else {
                        lecturerSupervisorName.postValue("");
                    }

                    Lecturer examiner1 = data.getExaminer1();
                    if (examiner1 != null) {
                        lecturerExaminer1Name.postValue(examiner1.getFullName());
                    } else {
                        lecturerExaminer1Name.postValue("");
                    }

                    Lecturer examiner2 = data.getExaminer2();
                    if (examiner2 != null) {
                        lecturerExaminer2Name.postValue(examiner2.getFullName());
                    } else {
                        lecturerExaminer2Name.postValue("");
                    }

                    timestampDayDate = schedule.getTimestamp();
                    dayDateDisplay.postValue(DateTimeUtils.formatDayDate(timestampDayDate));
                    time.postValue(timeOfStudiesWithDetail[schedule.getTimeOfStudyStart()]);
                }

                @Override
                public void onError(String message) {
                    eventMessage.postValue(message);
                    // Empty Data
                    thesisTitle.postValue("");
                    studentName.postValue("");
                    studentNim.postValue("");
                    studyProgram.postValue("");
                    phoneNumber.postValue("");
                    dayDateDisplay.postValue("");
                    time.postValue("");
                }
            });
        }
    }

    void setDayDate(long timestampDayDate) {
        this.timestampDayDate = timestampDayDate;
        if (timestampDayDate == -1) dayDateDisplay.setValue("");
        else dayDateDisplay.setValue(DateTimeUtils.formatDayDate(timestampDayDate));
    }

    void setTimeOfStudiesWithDetail(String[] timeOfStudiesWithDetail) {
        this.timeOfStudiesWithDetail = timeOfStudiesWithDetail;
    }

    void onEventMessageHandled() {
        eventMessage.setValue(null);
    }

    void onEventScheduleSavedHandled() {
        eventScheduleSaved.setValue(null);
    }

    private void checkConflict(final ThesisSchedule schedule, List<LecturerScheduleWithLecturer> lecturerSchedules) {
        for (LecturerScheduleWithLecturer data : lecturerSchedules) {
            LecturerSchedule s = data.getSchedule();
            if (DateTimeUtils.isFromSameDay(schedule.getTimestamp(), s.getDayOfWeek())) {
                int time = schedule.getTimeOfStudyStart();
                if (time >= s.getTimeOfStudyStart() && time <= s.getTimeOfStudyEnd()) {
                    Lecturer l = data.getLecturer();
                    eventMessage.postValue("Jadwal sidang bentrok dengan jadwal mengajar dosen " + l.getFullName() + " untuk mata kuliah " + s.getSubject());
                    eventScheduleSaved.postValue(false);
                    return;
                }
            }
        }

        ArrayList<Long> lecturerIds = new ArrayList<>();
        lecturerIds.add(schedule.getSupervisorLecturerId());
        lecturerIds.add(schedule.getExaminer1LecturerId());
        lecturerIds.add(schedule.getExaminer2LecturerId());
        thesisScheduleManager.getThesisSchedulesFromLecturersWithinDate(lecturerIds, schedule.getTimestamp(), new ResultCallback<List<ThesisScheduleWithLecturers>>() {
            @Override
            public void onSuccess(List<ThesisScheduleWithLecturers> data) {
                ThesisSchedule conflictedSchedule = null;
                for (ThesisScheduleWithLecturers t : data) {
                    ThesisSchedule s = t.getSchedule();
                    if (s.getTimeOfStudyStart() == schedule.getTimeOfStudyStart()) {
                        conflictedSchedule = s;
                        break;
                    }
                }
                if (conflictedSchedule != null) {
                    eventMessage.postValue("Jadwal sidang bentrok dengan jadwal dosen untuk sidang mahasiswa " + conflictedSchedule.getFullName() + ".");
                    eventScheduleSaved.postValue(false);
                } else saveSchedule(schedule);
            }

            @Override
            public void onError(String message) {
                eventMessage.postValue("Terjadi kesalahan ketika memeriksa jadwal sidang dosen.");
                eventScheduleSaved.postValue(false);
            }
        });
    }

    private void saveSchedule(ThesisSchedule schedule) {
        thesisScheduleManager.saveThesisSchedule(schedule);
        eventScheduleSaved.postValue(true);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private LecturerManager lecturerManager;
        private LecturerScheduleManager lecturerScheduleManager;
        private ThesisScheduleManager thesisScheduleManager;

        public Factory(LecturerManager lecturerManager, LecturerScheduleManager lecturerScheduleManager, ThesisScheduleManager thesisScheduleManager) {
            this.lecturerManager = lecturerManager;
            this.lecturerScheduleManager = lecturerScheduleManager;
            this.thesisScheduleManager = thesisScheduleManager;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AddThesisScheduleViewModel(lecturerManager, lecturerScheduleManager, thesisScheduleManager);
        }
    }

}
