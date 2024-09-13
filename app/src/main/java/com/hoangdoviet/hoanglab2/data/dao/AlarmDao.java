package com.hoangdoviet.hoanglab2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hoangdoviet.hoanglab2.data.model.Alarm;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AlarmDao {
    @Insert
    Completable insert(Alarm alarm);
    @Delete
    Completable delete(Alarm alarm);

    @Query("delete from alarm_table")
    Completable deleteAll();
    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    Single<List<Alarm>> getAlarms();
    @Update
    Completable update(Alarm alarm);
}
