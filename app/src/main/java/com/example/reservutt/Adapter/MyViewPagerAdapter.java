package com.example.reservutt.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.reservutt.Fragments.ReserveStep1Fragment;
import com.example.reservutt.Fragments.ReserveStep2Fragment;
import com.example.reservutt.Fragments.ReserveStep3Fragment;
import com.example.reservutt.Fragments.ReserveStep4Fragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter
{
    public MyViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i)
    {
        switch(i)
        {
            case 0 :
                return ReserveStep1Fragment.getInstance();
            case 1 :
                return ReserveStep2Fragment.getInstance();
            case 2 :
                return ReserveStep3Fragment.getInstance();
            case 3 :
                return ReserveStep4Fragment.getInstance();

        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
