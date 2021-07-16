package com.upnvj.skripsian.ui.thesis.add;

public class AddThesisScheduleViewState {

    private boolean isThesisTitleFilled;
    private boolean isStudentNameFilled;
    private boolean isNimFilled;
    private boolean isStudyProgramFilled;
    private boolean isPhoneFilled;
    private boolean isSupervisorFilled;
    private boolean isExaminer1Filled;
    private boolean isExaminer2Filled;
    private boolean isDayDateFilled;
    private boolean isTimeFilled;

    public AddThesisScheduleViewState() {
        isThesisTitleFilled = false;
        isStudentNameFilled = false;
        isNimFilled = false;
        isStudyProgramFilled = false;
        isPhoneFilled = false;
        isSupervisorFilled = false;
        isExaminer1Filled = false;
        isExaminer2Filled = false;
        isDayDateFilled = false;
        isTimeFilled = false;
    }

    public AddThesisScheduleViewState(boolean isThesisTitleFilled, boolean isStudentNameFilled, boolean isNimFilled, boolean isStudyProgramFilled, boolean isPhoneFilled, boolean isSupervisorFilled, boolean isExaminer1Filled, boolean isExaminer2Filled, boolean isDayDateFilled, boolean isTimeFilled) {
        this.isThesisTitleFilled = isThesisTitleFilled;
        this.isStudentNameFilled = isStudentNameFilled;
        this.isNimFilled = isNimFilled;
        this.isStudyProgramFilled = isStudyProgramFilled;
        this.isPhoneFilled = isPhoneFilled;
        this.isSupervisorFilled = isSupervisorFilled;
        this.isExaminer1Filled = isExaminer1Filled;
        this.isExaminer2Filled = isExaminer2Filled;
        this.isDayDateFilled = isDayDateFilled;
        this.isTimeFilled = isTimeFilled;
    }

    public boolean isThesisTitleFilled() {
        return isThesisTitleFilled;
    }

    public void setThesisTitleFilled(boolean thesisTitleFilled) {
        isThesisTitleFilled = thesisTitleFilled;
    }

    public boolean isStudentNameFilled() {
        return isStudentNameFilled;
    }

    public void setStudentNameFilled(boolean studentNameFilled) {
        isStudentNameFilled = studentNameFilled;
    }

    public boolean isNimFilled() {
        return isNimFilled;
    }

    public void setNimFilled(boolean nimFilled) {
        isNimFilled = nimFilled;
    }

    public boolean isStudyProgramFilled() {
        return isStudyProgramFilled;
    }

    public void setStudyProgramFilled(boolean studyProgramFilled) {
        isStudyProgramFilled = studyProgramFilled;
    }

    public boolean isPhoneFilled() {
        return isPhoneFilled;
    }

    public void setPhoneFilled(boolean phoneFilled) {
        isPhoneFilled = phoneFilled;
    }

    public boolean isSupervisorFilled() {
        return isSupervisorFilled;
    }

    public void setSupervisorFilled(boolean supervisorFilled) {
        isSupervisorFilled = supervisorFilled;
    }

    public boolean isExaminer1Filled() {
        return isExaminer1Filled;
    }

    public void setExaminer1Filled(boolean examiner1Filled) {
        isExaminer1Filled = examiner1Filled;
    }

    public boolean isExaminer2Filled() {
        return isExaminer2Filled;
    }

    public void setExaminer2Filled(boolean examiner2Filled) {
        isExaminer2Filled = examiner2Filled;
    }

    public boolean isDayDateFilled() {
        return isDayDateFilled;
    }

    public void setDayDateFilled(boolean dayDateFilled) {
        isDayDateFilled = dayDateFilled;
    }

    public boolean isTimeFilled() {
        return isTimeFilled;
    }

    public void setTimeFilled(boolean timeFilled) {
        isTimeFilled = timeFilled;
    }

}
