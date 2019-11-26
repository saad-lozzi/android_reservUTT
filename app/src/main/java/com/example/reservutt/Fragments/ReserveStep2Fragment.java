package com.example.reservutt.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ReserveStep2Fragment extends Fragment
{
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView =  inflater.inflate(R.layout.fragment_reserve_step_two, container, false);


        return inflater.inflate(R.layout.fragment_reserve_step_two, container, false);
    }
}
