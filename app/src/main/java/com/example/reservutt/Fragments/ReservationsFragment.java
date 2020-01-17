package com.example.reservutt.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservutt.Adapter.MyBookingAdapter;
import com.example.reservutt.Adapter.MySalleAdapter;
import com.example.reservutt.Common.Common;
import com.example.reservutt.Common.SpaceItemDecoration;
import com.example.reservutt.Interface.IBookingLoadListener;
import com.example.reservutt.MainActivity;
import com.example.reservutt.Models.BookingInformation;
import com.example.reservutt.Models.Salle;
import com.example.reservutt.Models.User;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsFragment extends Fragment implements IBookingLoadListener, View.OnClickListener {

    private FirebaseAuth mAuth;

    ViewPager viewPager;

    RecyclerView recyclerView;

    IBookingLoadListener iBookingLoadListener;

    Button btn_logout;

    Button button_delete;

    public ReservationsFragment() {
        // Required empty public constructor

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null)
        {
            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reservations, container, false);

        //this.viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        TextView txtvUsername = (TextView)rootView.findViewById(R.id.txt_user_name2);

        iBookingLoadListener = this;

        txtvUsername.setText(Common.currentUserName);

        TextView txtvProfession = (TextView)rootView.findViewById(R.id.txt_member_type2);

        txtvProfession.setText(Common.currentProfession);

        recyclerView = rootView.findViewById(R.id.recycler_booking);

        btn_logout = (Button)rootView.findViewById(R.id.logoutBtn2);

        button_delete = (Button)rootView.findViewById(R.id.button_delete);

        btn_logout.setOnClickListener(this);

        button_delete.setOnClickListener(this);

        initView();

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        //dialog.show();
        getReservations();

        return rootView;
    }


    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
    }

    public void getReservations()
    {
        CollectionReference userBooking = FirebaseFirestore.getInstance()
                .collection("User")
                .document(Common.currentUserId)
                .collection("Booking");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);

        Timestamp toDayTimeStamp = new Timestamp(calendar.getTime());

        userBooking
                .whereGreaterThanOrEqualTo("timestamp",toDayTimeStamp)
                .whereEqualTo("done",false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<BookingInformation> list = new ArrayList<>();
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                            {
                                BookingInformation booking = documentSnapshot.toObject(BookingInformation.class);
                                booking.setUser(documentSnapshot.getString("user"));
                                booking.setSalleName(documentSnapshot.getString("salleName"));
                                booking.setSalleId(documentSnapshot.getId());
                                list.add(booking);
                            }
                            iBookingLoadListener.onBookingLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBookingLoadListener.onBookingLoadFailed(e.getMessage());
            }
        });
    }

    public void deleteBookingFromUser()
    {
        if(Common.currentBooking != null)
        {

        }
        else
        {
            Toast.makeText(getContext(), "Pas de réservations selectionnées", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBookingLoadSuccess(List<BookingInformation> bookingList) {
        MyBookingAdapter adapter = new MyBookingAdapter(getActivity(),bookingList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        System.out.println(bookingList.size());
        //dialog.dismiss();
    }

    @Override
    public void onBookingLoadFailed(String message) {

    }

    @Override
    public void onClick(View v) {
        FirebaseUser user = mAuth.getCurrentUser();

        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.logoutBtn2)
        {
            mAuth.getInstance().signOut();

            Intent i = new Intent(getContext(), MainActivity.class);

            if(user != null)
            {
                System.out.println("user id : "+ user.getUid());
            }
            else
            {
                System.out.println("user is disconnected");
            }

            startActivity(i);

        }
        if(v.getId() == R.id.button_delete)
        {
            System.out.println("Button delete set for "+Common.slotToDelete);
            DocumentReference doc = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(Common.currentUserId)
                    .collection("Booking")
                    .document(String.valueOf(Common.slotToDelete));

            doc.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();


        }
    }
}
