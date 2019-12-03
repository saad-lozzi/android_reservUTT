package com.example.reservutt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.Common.Common;
import com.example.reservutt.Interface.IRecyclerItemSelectedListener;
import com.example.reservutt.Models.Salle;
import com.example.reservutt.Models.TimeSlot;
import com.example.reservutt.R;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter /*extends RecyclerView.Adapter<TimeAdapter.MyViewHolder>*/ {

    /*Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public TimeAdapter(Context context, List<TimeSlot> list) {
        this.context = context;
        this.timeSlotList = list;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salle,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        holder.txt_salle_name.setText(timeSlotList.get(i).getStatus().toString());
        if(!cardViewList.contains(holder.card_salle))
            cardViewList.add(holder.card_salle);

        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                for(CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));

                holder.card_salle.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));

                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                //intent.putExtra(Common.KEY_SALLE_STORE, timeSlotList.get(pos));
                intent.putExtra(Common.KEY_STEP, 1);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_salle_name;
        CardView card_salle;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            card_salle = (CardView) itemView.findViewById(R.id.card_salle);
            txt_salle_name = (TextView)itemView.findViewById(R.id.txt_salle_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
*/
}
