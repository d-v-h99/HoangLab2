package com.hoangdoviet.hoanglab2.createAlarm;

import android.content.Context;

import com.hoangdoviet.hoanglab2.BasePresenter;
import com.hoangdoviet.hoanglab2.BaseView;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import java.util.List;

public interface createAlarm {
    interface Presenter extends BasePresenter {
        void saveAlarm(Alarm alarm);
        void clear();

    }
    interface View extends BaseView<createAlarm.Presenter>{
        void close();
        Context getContext();
    }
}
