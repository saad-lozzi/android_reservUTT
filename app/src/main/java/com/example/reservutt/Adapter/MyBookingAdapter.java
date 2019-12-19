package com.example.reservutt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.Interface.IRecyclerItemSelectedListener;
import com.example.reservutt.Models.BookingInformation;
import com.example.reservutt.Models.Salle;
import com.example.reservutt.R;

import java.util.ArrayList;
import java.util.List;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder>
{
    Context context;
    List<BookingInformation> bookingList;
    List<CardView> cardViewList;

    public MyBookingAdapter(Context context, List<BookingInformation> bookingList)
    {
        this.context = context;
        this.bookingList = bookingList;
        cardViewList = new ArrayList<>();
        System.out.println("In adapter");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.reservation_layout,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingAdapter.MyViewHolder holder, int i) {
        System.out.println("Salle name is  : "+bookingList.get(i).getSalleName());
        holder.txt_reservation_name.setText("Salle : "+bookingList.get(i).getSalleName());
        holder.txt_reservation_time.setText(bookingList.get(i).getTime());
        if(!cardViewList.contains(holder.card_reservation))
            cardViewList.add(holder.card_reservation);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_reservation_name;
        TextView txt_reservation_time;
        CardView card_reservation;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            card_reservation = (CardView) itemView.findViewById(R.id.card_reservation);
            txt_reservation_name = (TextView)itemView.findViewById(R.id.txt_reservation_name);
            txt_reservation_time = (TextView)itemView.findViewById(R.id.txt_reservation_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}
