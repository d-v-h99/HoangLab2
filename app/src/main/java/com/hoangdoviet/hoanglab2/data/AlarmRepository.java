package com.hoangdoviet.hoanglab2.data;

import android.app.Application;

import com.hoangdoviet.hoanglab2.data.dao.AlarmDao;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlarmRepository {
    private final AlarmDao alarmDao;
    public AlarmRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
    }
    public Completable insert(Alarm alarm){
        return alarmDao.insert(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable update(Alarm alarm){
        return alarmDao.update(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable delete(Alarm alarm){
        return alarmDao.delete(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<Alarm>> getAlarms(){
        return alarmDao.getAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
