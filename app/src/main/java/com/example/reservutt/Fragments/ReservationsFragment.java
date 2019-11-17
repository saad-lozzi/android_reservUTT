package com.example.reservutt.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reservutt.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsFragment extends Fragment {

    ViewPager viewPager;


    public ReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        this.viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        return rootView;
    }

}
