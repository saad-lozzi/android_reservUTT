package com.example.reservutt.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.reservutt.Common.Common;
import com.example.reservutt.Models.BookingInformation;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReserveStep3Fragment extends Fragment implements View.OnClickListener {
    SimpleDateFormat simpleDateFormat;

    LocalBroadcastManager localBroadcastManager;

    TextView txt_booking_time_txt;

    TextView txt_booking_username_txt;

    TextView txt_booking_salle_txt;

    Button btn_confirm;

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
        .append(" à ")
        .append(simpleDateFormat.format(Common.currentDate.getTime())));

        txt_booking_salle_txt.setText(Common.currentSalleName);

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

        btn_confirm = itemView.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(this);

        return itemView;
    }

    @Override
    public void onClick(View v) {
        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.btn_confirm)
        {
            String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
            String[] convertTime = startTime.split("-");
            String[] startTimeConvert = convertTime[0].split(":");
            int startHourInt = Integer.parseInt(startTimeConvert[0].trim());
            int startMinInt = Integer.parseInt(startTimeConvert[1].trim());
            Calendar bookingDateWithSalle = Calendar.getInstance();
            bookingDateWithSalle.setTimeInMillis(Common.currentDate.getTimeInMillis());
            bookingDateWithSalle.set(Calendar.HOUR_OF_DAY,startHourInt);
            bookingDateWithSalle.set(Calendar.MINUTE,startMinInt);

            Timestamp timestamp = new Timestamp(bookingDateWithSalle.getTime());

            System.out.println("timestamp is "+ timestamp);

            BookingInformation bookingInformation = new BookingInformation();
            bookingInformation.setSalleId(Common.currentSalleId);
            bookingInformation.setTimestamp(timestamp);
            bookingInformation.setSalleName(Common.currentSalleName);
            bookingInformation.setUser(Common.currentUserName);
            bookingInformation.setUserId(Common.currentUserId);
            bookingInformation.setDone(false);
            bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                    .append(" à ")
                    .append(simpleDateFormat.format(bookingDateWithSalle.getTime())).toString());
            bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

            DocumentReference bookingDate = FirebaseFirestore.getInstance()
                .collection("Salles")
                .document(Common.Batiment)
                .collection("salle")
                .document(Common.currentSalleId)
                .collection(Common.simpleFormatDate.format(Common.currentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

            bookingDate.set(bookingInformation)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getActivity().finish();
                            Toast.makeText(getContext(), "Réservation faite avec succès", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            DocumentReference bookingDateUser = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(Common.currentUserId)
                    .collection("Booking")
                    .document();

            bookingDateUser.set(bookingInformation)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getActivity().finish();
                            Toast.makeText(getContext(), "Réservation fait avec succès", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
