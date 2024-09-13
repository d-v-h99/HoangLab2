package com.hoangdoviet.hoanglab2.createAlarm;

import android.widget.Toast;

import com.hoangdoviet.hoanglab2.data.AlarmRepository;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import io.reactivex.rxjava3.disposables.Disposable;

public class createAlarmPresenter implements createAlarm.Presenter {
    private final AlarmRepository alarmRepository;
    private final createAlarm.View mView;
    private Disposable disposable;


    public createAlarmPresenter(AlarmRepository alarmRepository, createAlarm.View mView) {
        this.alarmRepository = alarmRepository;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void saveAlarm(Alarm alarm) {
        disposable = alarmRepository.insert(alarm)
                .subscribe(
                        () ->{
                            Toast.makeText(mView.getContext(), "Luu bao thuc thanh cong", Toast.LENGTH_SHORT).show();
                            mView.close();
                        },
                        throwable -> throwable.printStackTrace()
                );
    }

    @Override
    public void clear() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
