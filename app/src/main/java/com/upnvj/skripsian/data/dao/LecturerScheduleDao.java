package com.upnvj.skripsian.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.upnvj.skripsian.data.model.LecturerSchedule;
import com.upnvj.skripsian.data.model.LecturerScheduleWithLecturer;

import java.util.List;

@Dao
public abstract class LecturerScheduleDao implements BaseDao<LecturerSchedule> {

    @Query("SELECT * FROM lecturer_schedules WHERE id = :id")
    public abstract LecturerSchedule getLecturerSchedule(long id);

    @Transaction
    @Query("SELECT * FROM lecturer_schedules WHERE lecturer_id IN (:lecturerIds)")
    public abstract List<LecturerScheduleWithLecturer> getLecturerSchedules(List<Long> lecturerIds);

    @Query("SELECT * FROM lecturer_schedules WHERE id = :id")
    public abstract LiveData<LecturerSchedule> getObservableLecturerSchedule(long id);

    @Query("SELECT * FROM lecturer_schedules WHERE lecturer_id = :lecturerId")
    public abstract LiveData<List<LecturerSchedule>> getObservableLecturerSchedules(long lecturerId);

}
