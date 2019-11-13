package com.example.reservutt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.Models.TypeSalle;
import com.example.reservutt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TypeSalleAdapter extends RecyclerView.Adapter<TypeSalleAdapter.MyViewHolder> {

    Context context;
    List<TypeSalle> typeSalles;

    public TypeSalleAdapter(Context context, List<TypeSalle> typeSalles) {
        this.context = context;
        this.typeSalles = typeSalles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salle_type, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(typeSalles.get(i).getImage()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return typeSalles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_type_salle);
        }
    }
}
