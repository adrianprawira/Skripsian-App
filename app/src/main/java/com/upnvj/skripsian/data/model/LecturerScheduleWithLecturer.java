package com.upnvj.skripsian.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LecturerScheduleWithLecturer {

    @Embedded
    private LecturerSchedule schedule;

    @Relation(
            entity = Lecturer.class,
            parentColumn = "lecturer_id",
            entityColumn = "id"
    )
    private Lecturer lecturer;

    public LecturerSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(LecturerSchedule schedule) {
        this.schedule = schedule;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

}
