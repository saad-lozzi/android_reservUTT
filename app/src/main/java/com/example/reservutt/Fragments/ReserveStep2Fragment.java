package com.example.reservutt.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.Adapter.MyTimeSlotAdapter;
import com.example.reservutt.Common.Common;
import com.example.reservutt.Common.SpaceItemDecoration;
import com.example.reservutt.Interface.ITimeSlotLoadListener;
import com.example.reservutt.Models.TimeSlot;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dmax.dialog.SpotsDialog;

public class ReserveStep2Fragment extends Fragment implements ITimeSlotLoadListener {
    DocumentReference reservDoc;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;

    LocalBroadcastManager localBroadcastManager;

    RecyclerView recycler_time_slot;

    HorizontalCalendarView calendarView;

    SimpleDateFormat simpleDateFormat;

    BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0);
            loadAvailableTimeSlotOfSalle(Common.currentSalle.getId(), simpleDateFormat.format(date.getTime()));
        }
    };

    private void loadAvailableTimeSlotOfSalle(String id,final String bookDate) {
        dialog.show();
        reservDoc = FirebaseFirestore.getInstance()
                .collection("Salles")
                .document(Common.Batiment)
                .collection("salle")
                .document(Common.currentSalle.getId());

        Common.currentSalleName = Common.currentSalle.getName();

        Common.currentSalleId = Common.currentSalle.getId();

        System.out.println("reservDoc is :"+ reservDoc);

        reservDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists())
                    {
                        CollectionReference date = FirebaseFirestore.getInstance()
                                .collection("Salles")
                                .document(Common.Batiment)
                                .collection("salle")
                                .document(Common.currentSalle.getId())
                                .collection(bookDate);

                        date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if(querySnapshot.isEmpty())
                                    {
                                        iTimeSlotLoadListener.onTimeSlotLoadEmpty();
                                    }
                                    else
                                    {
                                        List<TimeSlot> timeSlots = new ArrayList<>();
                                        for(QueryDocumentSnapshot document:task.getResult())
                                            timeSlots.add(document.toObject(TimeSlot.class));
                                        iTimeSlotLoadListener.onTimeSlotLoadSuccess(timeSlots);
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }

    static ReserveStep2Fragment instance;

    public static ReserveStep2Fragment getInstance()
    {
        if (instance == null)
        {
            instance = new ReserveStep2Fragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iTimeSlotLoadListener = this;

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(displayTimeSlot, new IntentFilter(Common.KEY_DISPLAY_TIME_SLOT));
        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView =  inflater.inflate(R.layout.fragment_reserve_step_two, container, false);

        recycler_time_slot = itemView.findViewById(R.id.recycler_time_slot);

        calendarView = itemView.findViewById(R.id.calendarView);


        //init(itemView);

        return itemView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {


        recycler_time_slot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recycler_time_slot.setLayoutManager(gridLayoutManager);
        recycler_time_slot.addItemDecoration(new SpaceItemDecoration(8));

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE,0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,3);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(getActivity(),R.id.calendarView)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if(Common.currentDate.getTimeInMillis() != date.getTimeInMillis())
                {
                    Common.currentDate = date;
                    loadAvailableTimeSlotOfSalle(Common.currentSalle.getId(), simpleDateFormat.format(date.getTime()));
                }
            }
        });
    }

    @Override
    public void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList) {
        System.out.println("succesful load timeslot");
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getActivity(), timeSlotList);
        recycler_time_slot.setAdapter(adapter);
        System.out.println("SALLLLLLLE IISSSSSSS "+ Common.currentSalle.getName());
        dialog.dismiss();
    }

    @Override
    public void onTimeSlotLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onTimeSlotLoadEmpty() {
        System.out.println("Empty slots and now");

        List<TimeSlot> list = new ArrayList<>();
        TimeSlot slot = new TimeSlot(){};
        list.add(slot);

        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getActivity(), list);
        recycler_time_slot.setAdapter(adapter);
        recycler_time_slot.setVisibility(View.VISIBLE);
        System.out.println("SALLLLLLLE IISSSSSSS "+ Common.currentSalle.getName());


        System.out.println("Adapter " + adapter);

        dialog.dismiss();
    }
}
