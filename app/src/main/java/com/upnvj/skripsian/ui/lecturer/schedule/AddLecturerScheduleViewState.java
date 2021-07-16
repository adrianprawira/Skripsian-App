package com.upnvj.skripsian.ui.lecturer.schedule;

public class AddLecturerScheduleViewState {

    private boolean isClassSubjectFilled;
    private boolean isSksFilled;
    private boolean isClassNameFilled;
    private boolean isDayFilled;
    private boolean isTimeStartFilled;
    private boolean isTimeEndFilled;

    AddLecturerScheduleViewState() {
        isClassSubjectFilled = false;
        isSksFilled = false;
        isClassNameFilled = false;
        isDayFilled = false;
        isTimeStartFilled = false;
        isTimeEndFilled = false;
    }

    public AddLecturerScheduleViewState(boolean isClassSubjectFilled, boolean isSksFilled, boolean isClassNameFilled, boolean isDayFilled, boolean isTimeStartFilled, boolean isTimeEndFilled) {
        this.isClassSubjectFilled = isClassSubjectFilled;
        this.isSksFilled = isSksFilled;
        this.isClassNameFilled = isClassNameFilled;
        this.isDayFilled = isDayFilled;
        this.isTimeStartFilled = isTimeStartFilled;
        this.isTimeEndFilled = isTimeEndFilled;
    }

    public boolean isClassSubjectFilled() {
        return isClassSubjectFilled;
    }

    void setClassSubjectFilled(boolean classSubjectFilled) {
        isClassSubjectFilled = classSubjectFilled;
    }

    public boolean isSksFilled() {
        return isSksFilled;
    }

    void setSksFilled(boolean sksFilled) {
        isSksFilled = sksFilled;
    }

    public boolean isClassNameFilled() {
        return isClassNameFilled;
    }

    void setClassNameFilled(boolean classNameFilled) {
        isClassNameFilled = classNameFilled;
    }

    public boolean isDayFilled() {
        return isDayFilled;
    }

    void setDayFilled(boolean dayFilled) {
        isDayFilled = dayFilled;
    }

    public boolean isTimeStartFilled() {
        return isTimeStartFilled;
    }

    public void setTimeStartFilled(boolean timeStartFilled) {
        isTimeStartFilled = timeStartFilled;
    }

    public boolean isTimeEndFilled() {
        return isTimeEndFilled;
    }

    public void setTimeEndFilled(boolean timeEndFilled) {
        isTimeEndFilled = timeEndFilled;
    }

}
