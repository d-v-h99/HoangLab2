package com.hoangdoviet.hoanglab2.alarmList;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoangdoviet.hoanglab2.R;
import com.hoangdoviet.hoanglab2.createAlarm.createAlarmPresenter;
import com.hoangdoviet.hoanglab2.data.AlarmRepository;
import com.hoangdoviet.hoanglab2.data.model.Alarm;
import com.hoangdoviet.hoanglab2.databinding.FragmentAlarmsListBinding;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class AlarmsListFragment extends Fragment implements alarmList.View, OnToggleAlarmListener {
    private FragmentAlarmsListBinding binding;
    private alarmList.Presenter mPresenter;
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    public AlarmsListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_alarms_list, container, false);
        binding = FragmentAlarmsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlarmRepository db = new AlarmRepository(requireActivity().getApplication());
        mPresenter = new alarmListPresenter(db, this);
        binding.fragmentListalarmsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this);
        binding.fragmentListalarmsRecylerView.setAdapter(alarmRecyclerViewAdapter);
        binding.fragmentListalarmsAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_alarmsListFragment_to_createAlarmFragment);
            }
        });
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Alarm alarmDelete = null;
                alarmDelete = alarmRecyclerViewAdapter.listAlarm().get(position);
                mPresenter.delete(alarmDelete);
                alarmRecyclerViewAdapter.removeAlarm(alarmDelete);

            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.g_red))
                        // .addActionIcon(R.drawable.my_icon)
                        .addSwipeLeftLabel("Xoá")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.fragmentListalarmsRecylerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.populateAlarms();
    }

    @Override
    public void showAddAlarm() {

    }

    @Override
    public void setAlarms(List<Alarm> alarmList) {
        alarmRecyclerViewAdapter.setAlarms(alarmList);

    }

    @Override
    public void setPresenter(alarmList.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onToggle(Alarm alarm) {
        if(alarm.isStarted()){
            alarm.cancelAlarm(getContext());
            mPresenter.update(alarm);
        }
    }
}