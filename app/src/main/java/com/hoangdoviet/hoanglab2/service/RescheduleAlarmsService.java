package com.hoangdoviet.hoanglab2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.hoangdoviet.hoanglab2.data.AlarmRepository;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import io.reactivex.rxjava3.disposables.Disposable;

public class RescheduleAlarmsService extends Service {
    private Disposable disposable;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmRepository alarmRepository= new AlarmRepository(getApplication());
        disposable = alarmRepository.getAlarms()
                .subscribe(alarms -> {
                    for (Alarm a : alarms) {
                        if (a.isStarted()) {
                            a.schedule(getApplicationContext());
                        }
                    }
                }, throwable -> {
                });
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
