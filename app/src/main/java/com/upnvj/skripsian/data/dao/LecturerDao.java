package com.upnvj.skripsian.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.upnvj.skripsian.data.model.Lecturer;

import java.util.List;

@Dao
public abstract class LecturerDao implements BaseDao<Lecturer> {

    @Query("SELECT * FROM lecturers WHERE id = :id")
    public abstract Lecturer getLecturer(long id);

    @Query("SELECT * FROM lecturers WHERE id = :id")
    public abstract LiveData<Lecturer> getObservableLecturer(long id);

    @Query("SELECT * FROM lecturers")
    public abstract LiveData<List<Lecturer>> getObservableAllLecturers();

}
