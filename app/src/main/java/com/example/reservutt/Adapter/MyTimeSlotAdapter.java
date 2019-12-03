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
import com.example.reservutt.Models.TimeSlot;
import com.example.reservutt.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyTimeSlotAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
        System.out.println("Im theeeere");
    }

    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
        System.out.println("Im theeeere2");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_time_slot, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        System.out.println("it enterrrs");
        holder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(i)).toString());
        if(timeSlotList.size() == 0){
            System.out.println("I'm heeeeeeeeeeeere");
            holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            holder.txt_time_slot_description.setText("Disponible");
            holder.txt_time_slot_description.setTextColor(ContextCompat.getColor(context, android.R.color.white));
            holder.txt_time_slot.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
        else
        {
            for(TimeSlot slotValue:timeSlotList)
            {
                System.out.println("slotvalue is "+ slotValue.getSlot());
                if(slotValue.getSlot() != null) {
                    int slot = Integer.parseInt(slotValue.getSlot().toString());
                    if (slot == i) {
                        holder.card_time_slot.setTag(Common.DISABLE_TAG);
                        holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                        holder.txt_time_slot_description.setText("Non Disponible");
                        holder.txt_time_slot_description.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                        holder.txt_username.setText(slotValue.getUser().toString());
                        holder.txt_time_slot.setTextColor(ContextCompat.getColor(context, android.R.color.white));
                    }
                }
            }
        }
        if(!cardViewList.contains(holder.card_time_slot))
            cardViewList.add(holder.card_time_slot);

        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int i) {
                for(CardView cardView:cardViewList)
                {
                    if(cardView.getTag() == null)
                        cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
                }
                holder.card_time_slot.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark));

                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_TIME_SLOT,i);
                intent.putExtra(Common.KEY_STEP, 2);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_time_slot, txt_time_slot_description, txt_username;
        CardView card_time_slot;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_time_slot = (CardView)itemView.findViewById(R.id.card_time_slot);
            txt_time_slot = (TextView)itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = (TextView)itemView.findViewById(R.id.txt_time_slot_description);
            txt_username = (TextView) itemView.findViewById(R.id.txt_book_user_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }

    }
}
