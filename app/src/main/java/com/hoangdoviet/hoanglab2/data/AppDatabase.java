package com.hoangdoviet.hoanglab2.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hoangdoviet.hoanglab2.data.dao.AlarmDao;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

@Database(entities = {Alarm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract AlarmDao alarmDao();
    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "alarm")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
