package com.upnvj.skripsian.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "lecturer_schedules")
public class LecturerSchedule {

    public static DiffUtil.ItemCallback<LecturerSchedule> DIFF_CALLBACK = new DiffUtil.ItemCallback<LecturerSchedule>() {
        @Override
        public boolean areItemsTheSame(@NonNull LecturerSchedule oldItem, @NonNull LecturerSchedule newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LecturerSchedule oldItem, @NonNull LecturerSchedule newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "lecturer_id")
    private long lecturerId;

    @ColumnInfo(name = "day_of_week")
    private int dayOfWeek;

    @ColumnInfo(name = "time_of_study_start")
    private int timeOfStudyStart;

    @ColumnInfo(name = "time_of_study_end")
    private int timeOfStudyEnd;

    private String subject;

    private String sks;

    @ColumnInfo(name = "class_name")
    private String className;

    public LecturerSchedule() {
    }

    @Ignore
    public LecturerSchedule(long id, long lecturerId, int dayOfWeek, int timeOfStudyStart, int timeOfStudyEnd, String subject, String sks, String className) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.dayOfWeek = dayOfWeek;
        this.timeOfStudyStart = timeOfStudyStart;
        this.timeOfStudyEnd = timeOfStudyEnd;
        this.subject = subject;
        this.sks = sks;
        this.className = className;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeOfStudyStart() {
        return timeOfStudyStart;
    }

    public void setTimeOfStudyStart(int timeOfStudyStart) {
        this.timeOfStudyStart = timeOfStudyStart;
    }

    public int getTimeOfStudyEnd() {
        return timeOfStudyEnd;
    }

    public void setTimeOfStudyEnd(int timeOfStudyEnd) {
        this.timeOfStudyEnd = timeOfStudyEnd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
