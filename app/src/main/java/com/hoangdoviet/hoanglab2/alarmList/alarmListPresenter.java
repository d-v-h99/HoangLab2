package com.hoangdoviet.hoanglab2.alarmList;

import com.hoangdoviet.hoanglab2.createAlarm.createAlarm;
import com.hoangdoviet.hoanglab2.data.AlarmRepository;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import io.reactivex.rxjava3.disposables.Disposable;

public class alarmListPresenter implements alarmList.Presenter{
    private final AlarmRepository alarmRepository;
    private final alarmList.View mView;
    private Disposable disposable;

    public alarmListPresenter(AlarmRepository alarmRepository, alarmList.View mView) {
        this.alarmRepository = alarmRepository;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void populateAlarms() {
        disposable = alarmRepository.getAlarms()
                .subscribe(
                        alarms -> {
                            mView.setAlarms(alarms);
                        },
                        throwable -> {
                            throwable.printStackTrace();
//                            mView.showError("Error loading alarms");
                        }
                );

    }

    @Override
    public void openEditAlarms(Alarm alarm) {

    }

    @Override
    public void clear() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void update(Alarm alarm) {
        disposable = alarmRepository.update(alarm)
                .subscribe(
                        () ->{

                        },
                        throwable -> throwable.printStackTrace()
                );
    }

    @Override
    public void delete(Alarm alarm) {
        disposable = alarmRepository.delete(alarm)
                .subscribe(
                        () ->{

                        },
                        throwable -> throwable.printStackTrace()
                );
    }
}
