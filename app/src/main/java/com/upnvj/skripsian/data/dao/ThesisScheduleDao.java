package com.upnvj.skripsian.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.upnvj.skripsian.data.model.ThesisSchedule;
import com.upnvj.skripsian.data.model.ThesisScheduleWithLecturers;

import java.util.List;

@Dao
public abstract class ThesisScheduleDao implements BaseDao<ThesisSchedule> {

    @Transaction
    @Query("SELECT * FROM thesis_schedules WHERE id = :id")
    public abstract ThesisScheduleWithLecturers getThesisSchedule(long id);

    @Transaction
    @Query("SELECT * FROM thesis_schedules")
    public abstract List<ThesisScheduleWithLecturers> getThesisSchedules();

    @Transaction
    @Query("SELECT * FROM thesis_schedules WHERE (supervisor_lecturer_id IN (:lecturerIds)) OR (examiner_1_lecturer_id IN (:lecturerIds)) OR (examiner_2_lecturer_id IN (:lecturerIds))")
    public abstract List<ThesisScheduleWithLecturers> getThesisSchedulesFromLecturers(List<Long> lecturerIds);

    @Transaction
    @Query("SELECT * FROM thesis_schedules WHERE id = :id")
    public abstract LiveData<ThesisScheduleWithLecturers> getObservableThesisSchedule(long id);

    @Transaction
    @Query("SELECT * FROM thesis_schedules")
    public abstract LiveData<List<ThesisScheduleWithLecturers>> getObservableThesisSchedules();

}
