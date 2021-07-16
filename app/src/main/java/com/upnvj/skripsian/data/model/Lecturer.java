package com.upnvj.skripsian.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "lecturers")
public class Lecturer {

    public static DiffUtil.ItemCallback<Lecturer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Lecturer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Lecturer oldItem, @NonNull Lecturer newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Lecturer oldItem, @NonNull Lecturer newItem) {
            return false;
        }
    };

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String nip;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "study_program")
    private String studyProgram;

    public Lecturer() {
    }

    @Ignore
    public Lecturer(long id, String fullName, String nip, String studyProgram) {
        this.id = id;
        this.fullName = fullName;
        this.nip = nip;
        this.studyProgram = studyProgram;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

}
