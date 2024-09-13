package com.hoangdoviet.hoanglab2.alarmList;

import com.hoangdoviet.hoanglab2.BasePresenter;
import com.hoangdoviet.hoanglab2.BaseView;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import java.util.List;

public interface alarmList {
    interface Presenter extends BasePresenter{
       void populateAlarms();
       void openEditAlarms(Alarm alarm);
       void clear();
       void update(Alarm alarm);
       void delete(Alarm alarm);
    }
    interface View extends BaseView<alarmList.Presenter>{
        void showAddAlarm();
        void setAlarms(List<Alarm> alarmList);
    }
}
