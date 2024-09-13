package com.hoangdoviet.hoanglab2.createAlarm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.hoangdoviet.hoanglab2.BasePresenter;
import com.hoangdoviet.hoanglab2.R;
import com.hoangdoviet.hoanglab2.data.AlarmRepository;
import com.hoangdoviet.hoanglab2.data.AppDatabase;
import com.hoangdoviet.hoanglab2.data.model.Alarm;
import com.hoangdoviet.hoanglab2.databinding.FragmentCreateAlarmBinding;

import java.util.Objects;
import java.util.Random;

public class CreateAlarmFragment extends Fragment implements createAlarm.View {
    private FragmentCreateAlarmBinding binding;
    private createAlarm.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_create_alarm, container, false);
        binding= FragmentCreateAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlarmRepository db = new AlarmRepository(requireActivity().getApplication());
        mPresenter = new createAlarmPresenter(db, this);
        binding.fragmentCreatealarmRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.fragmentCreatealarmRecurringOptions.setVisibility(View.VISIBLE);
                } else {
                    binding.fragmentCreatealarmRecurringOptions.setVisibility(View.GONE);
                }
            }
        });
        binding.fragmentCreatealarmScheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();
            }
        });
    }

    @Override
    public void close() {
        Navigation.findNavController(requireView()).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                TimePickerUtil.getTimePickerHour(binding.fragmentCreatealarmTimePicker),
                TimePickerUtil.getTimePickerMinute(binding.fragmentCreatealarmTimePicker),
                binding.fragmentCreatealarmTitle.getText().toString(),
                System.currentTimeMillis(),
                true,
                binding.fragmentCreatealarmRecurring.isChecked(),
                binding.fragmentCreatealarmCheckMon.isChecked(),
                binding.fragmentCreatealarmCheckTue.isChecked(),
                binding.fragmentCreatealarmCheckWed.isChecked(),
                binding.fragmentCreatealarmCheckThu.isChecked(),
                binding.fragmentCreatealarmCheckFri.isChecked(),
                binding.fragmentCreatealarmCheckSat.isChecked(),
                binding.fragmentCreatealarmCheckSun.isChecked()
        );

        mPresenter.saveAlarm(alarm);

        alarm.schedule(getContext());
    }


    @Override
    public void setPresenter(createAlarm.Presenter presenter) {
        mPresenter = presenter;
    }
    @Override
    public Context getContext() {
        return isAdded() ? super.getContext() : null;
    }
}