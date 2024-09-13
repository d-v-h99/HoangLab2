package com.hoangdoviet.hoanglab2.alarmList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangdoviet.hoanglab2.R;
import com.hoangdoviet.hoanglab2.data.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder> {
    private List<Alarm> alarms;
    private OnToggleAlarmListener listener;

    public AlarmRecyclerViewAdapter(OnToggleAlarmListener listener) {
        this.listener = listener;
        this.alarms = new ArrayList<Alarm>();
    }

    public AlarmRecyclerViewAdapter(List<Alarm> alarms, OnToggleAlarmListener listener) {
        this.alarms = alarms;
        this.listener = listener;
    }
    public List<Alarm> listAlarm(){
        return this.alarms;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }
    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }
    public void removeAlarm(Alarm alarm){
        int position = alarms.indexOf(alarm);
        if (position != -1){
            alarms.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, alarms.size());
        }
    }
    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
        //Tùy chỉnh hành vi khi view được tái sử dụng: Bạn có thể sử dụng phương thức này để thực hiện các tác vụ đặc biệt khi một view được tái sử dụng. Ví dụ:
        //Đặt lại các giá trị của view về trạng thái mặc định.
        //Hủy bỏ các subscription hoặc listeners để tránh rò rỉ bộ nhớ.
        //Thực hiện các tác vụ khác tùy thuộc vào yêu cầu của ứng dụng.
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder{
        private final TextView alarmTime;
        private final ImageView alarmRecurring;
        private final TextView alarmRecurringDays;
        private final TextView alarmTitle;
        Switch alarmStarted;
        private OnToggleAlarmListener listener;
        public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.item_alarm_time);
            alarmStarted = itemView.findViewById(R.id.item_alarm_started);
            alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
            alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
            alarmTitle = itemView.findViewById(R.id.item_alarm_title);
            this.listener = listener;
        }
        public void bind(Alarm alarm){
            String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());
            alarmTime.setText(alarmText);
            alarmStarted.setChecked(alarm.isStarted());
            if(alarm.isRecurring()){
                alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
                alarmRecurringDays.setText(alarm.getRecurringDaysText());
            }else {
                alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
                alarmRecurringDays.setText("Once off");
            }
            if(alarm.getTitle().length() != 0){
//                alarmTitle.setText(String.format("%s | %d | %d", alarm.getTitle(), alarm.getAlarmID(), alarm.getCreated()));
                alarmTitle.setText(alarm.getTitle());
            }else {
//                alarmTitle.setText(String.format("%s | %d | %d", "Alarm", alarm.getAlarmID(), alarm.getCreated()));
                alarmTitle.setText("alarm");
            }
            alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onToggle(alarm);
                }
            });
        }
    }
}

