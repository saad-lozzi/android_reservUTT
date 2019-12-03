package com.example.reservutt.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.reservutt.Common.Common;
import com.example.reservutt.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

public class ReserveStep3Fragment extends Fragment
{
    SimpleDateFormat simpleDateFormat;

    LocalBroadcastManager localBroadcastManager;

    TextView txt_booking_time_txt;

    TextView txt_booking_username_txt;

    TextView txt_booking_salle_txt;

    private FirebaseAuth mAuth;

    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {

        mAuth = FirebaseAuth.getInstance();

        txt_booking_username_txt.setText(Common.currentUserName);

        txt_booking_time_txt.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append(" at ")
        .append(simpleDateFormat.format(Common.currentDate.getTime())));

        txt_booking_salle_txt.setText(Common.currentSalle.getName());
    }

    static ReserveStep3Fragment instance;

    public static ReserveStep3Fragment getInstance()
    {
        if (instance == null)
        {
            instance = new ReserveStep3Fragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver, new IntentFilter(Common.KEY_CONFIRM_BOOKING));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_reserve_step_three, container, false);

        txt_booking_time_txt = itemView.findViewById(R.id.txt_time_detail);

        txt_booking_username_txt = itemView.findViewById(R.id.txt_user_name_detail);

        txt_booking_salle_txt = itemView.findViewById(R.id.txt_salle_detail);

        return itemView;
    }
}
