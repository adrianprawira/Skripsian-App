package com.upnvj.skripsian.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "thesis_schedules")
public class ThesisSchedule {

    public static DiffUtil.ItemCallback<ThesisSchedule> DIFF_CALLBACK = new DiffUtil.ItemCallback<ThesisSchedule>() {
        @Override
        public boolean areItemsTheSame(@NonNull ThesisSchedule oldItem, @NonNull ThesisSchedule newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ThesisSchedule oldItem, @NonNull ThesisSchedule newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "supervisor_lecturer_id")
    private long supervisorLecturerId;

    @ColumnInfo(name = "examiner_1_lecturer_id")
    private long examiner1LecturerId;

    @ColumnInfo(name = "examiner_2_lecturer_id")
    private long examiner2LecturerId;

    private long timestamp;

    @ColumnInfo(name = "time_of_study_start")
    private int timeOfStudyStart;

    @ColumnInfo(name = "full_name")
    private String fullName;

    private String nim;

    @ColumnInfo(name = "study_program")
    private String studyProgram;

    @ColumnInfo(name = "thesis_title")
    private String thesisTitle;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    public ThesisSchedule() {
    }

    @Ignore
    public ThesisSchedule(long id, long supervisorLecturerId, long examiner1LecturerId, long examiner2LecturerId, long timestamp, int timeOfStudyStart, String fullName, String nim, String studyProgram, String thesisTitle, String phoneNumber) {
        this.id = id;
        this.supervisorLecturerId = supervisorLecturerId;
        this.examiner1LecturerId = examiner1LecturerId;
        this.examiner2LecturerId = examiner2LecturerId;
        this.timestamp = timestamp;
        this.timeOfStudyStart = timeOfStudyStart;
        this.fullName = fullName;
        this.nim = nim;
        this.studyProgram = studyProgram;
        this.thesisTitle = thesisTitle;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSupervisorLecturerId() {
        return supervisorLecturerId;
    }

    public void setSupervisorLecturerId(long supervisorLecturerId) {
        this.supervisorLecturerId = supervisorLecturerId;
    }

    public long getExaminer1LecturerId() {
        return examiner1LecturerId;
    }

    public void setExaminer1LecturerId(long examiner1LecturerId) {
        this.examiner1LecturerId = examiner1LecturerId;
    }

    public long getExaminer2LecturerId() {
        return examiner2LecturerId;
    }

    public void setExaminer2LecturerId(long examiner2LecturerId) {
        this.examiner2LecturerId = examiner2LecturerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimeOfStudyStart() {
        return timeOfStudyStart;
    }

    public void setTimeOfStudyStart(int timeOfStudyStart) {
        this.timeOfStudyStart = timeOfStudyStart;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
