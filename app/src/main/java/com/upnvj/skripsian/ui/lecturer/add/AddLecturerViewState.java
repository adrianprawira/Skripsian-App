package com.upnvj.skripsian.ui.lecturer.add;

public class AddLecturerViewState {

    private boolean isNameFilled;
    private boolean isNipFilled;
    private boolean isStudyProgramFilled;

    public AddLecturerViewState() {
        isNameFilled = false;
        isNipFilled = false;
        isStudyProgramFilled = false;
    }

    public AddLecturerViewState(boolean isNameFilled, boolean isNipFilled, boolean isStudyProgramFilled) {
        this.isNameFilled = isNameFilled;
        this.isNipFilled = isNipFilled;
        this.isStudyProgramFilled = isStudyProgramFilled;
    }

    public boolean isNameFilled() {
        return isNameFilled;
    }

    public void setNameFilled(boolean nameFilled) {
        isNameFilled = nameFilled;
    }

    public boolean isNipFilled() {
        return isNipFilled;
    }

    public void setNipFilled(boolean nipFilled) {
        isNipFilled = nipFilled;
    }

    public boolean isStudyProgramFilled() {
        return isStudyProgramFilled;
    }

    public void setStudyProgramFilled(boolean studyProgramFilled) {
        isStudyProgramFilled = studyProgramFilled;
    }

}
