package com.upnvj.skripsian.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Embedded;
import androidx.room.Relation;

public class ThesisScheduleWithLecturers {

    public static DiffUtil.ItemCallback<ThesisScheduleWithLecturers> DIFF_CALLBACK = new DiffUtil.ItemCallback<ThesisScheduleWithLecturers>() {
        @Override
        public boolean areItemsTheSame(@NonNull ThesisScheduleWithLecturers oldItem, @NonNull ThesisScheduleWithLecturers newItem) {
            return oldItem.getSchedule().getId() == newItem.getSchedule().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ThesisScheduleWithLecturers oldItem, @NonNull ThesisScheduleWithLecturers newItem) {
            return false;
        }
    };

    @Embedded
    private ThesisSchedule schedule;

    @Relation(
            entity = Lecturer.class,
            parentColumn = "supervisor_lecturer_id",
            entityColumn = "id"
    )
    private Lecturer supervisor;

    @Relation(
            entity = Lecturer.class,
            parentColumn = "examiner_1_lecturer_id",
            entityColumn = "id"
    )
    private Lecturer examiner1;

    @Relation(
            entity = Lecturer.class,
            parentColumn = "examiner_2_lecturer_id",
            entityColumn = "id"
    )
    private Lecturer examiner2;

    public ThesisSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(ThesisSchedule schedule) {
        this.schedule = schedule;
    }

    public Lecturer getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Lecturer supervisor) {
        this.supervisor = supervisor;
    }

    public Lecturer getExaminer1() {
        return examiner1;
    }

    public void setExaminer1(Lecturer examiner1) {
        this.examiner1 = examiner1;
    }

    public Lecturer getExaminer2() {
        return examiner2;
    }

    public void setExaminer2(Lecturer examiner2) {
        this.examiner2 = examiner2;
    }
}
