package com.example.reservutt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.reservutt.Adapter.MyViewPagerAdapter;
import com.example.reservutt.Common.Common;
import com.google.firebase.firestore.CollectionReference;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener{

    LocalBroadcastManager localBroadcastManager;

    int stepIndex = 0;

    ViewPager viewPager;

    StepView stepView;

    Button btn_next;

    CollectionReference timeRef;


    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if(step == 1){
                Common.currentSalle = intent.getParcelableExtra(Common.KEY_SALLE_STORE);
            }
            else if (step == 2)
            {
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT, -1);
            }
            Common.currentSalle = intent.getParcelableExtra(Common.KEY_SALLE_STORE);
            btn_next.setEnabled(true);
            setColorButton();
        }
    };

    @Override
    protected void onDestroy(){
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserve);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        stepView = findViewById(R.id.step_view);

        viewPager = findViewById(R.id.view_pager);

        btn_next = (Button) findViewById(R.id.btn_next_step);

        Button btn_previous = (Button) findViewById(R.id.btn_previous_step);

        btn_next.setOnClickListener(this);

        btn_previous.setOnClickListener(this);

        setColorButton();

        setupStepView();

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                Button btn_next = (Button) findViewById(R.id.btn_next_step);

                Button btn_previous = (Button) findViewById(R.id.btn_previous_step);

                if(i==0){
                    btn_previous.setEnabled(false);
                }
                else
                {
                    btn_previous.setEnabled(true);
                }
                if(i==1){
                    if(Common.currentSalle != null)
                        loadTimeSlotOfSalle(Common.currentSalle.getId());
                        //System.out.println("SALLE DETAAAAAAAAAAILS "+Common.currentSalle.getId() + Common.currentSalle.getName());
                    System.out.println("salle choosing");
                }
                if(i==2){
                    if(Common.currentTimeSlot != -1)
                        confirmBooking();
                }
                else
                {
                    //btn_next.setEnabled(true);
                }
                btn_next.setEnabled(false);
                setColorButton();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void confirmBooking(){
        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadTimeSlotOfSalle(String salleId) {
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void setColorButton()
    {
        Button btn_next = (Button) findViewById(R.id.btn_next_step);

        Button btn_previous = (Button) findViewById(R.id.btn_previous_step);

        if (btn_next.isEnabled())
        {
            btn_next.setBackgroundResource(R.color.txtColorWhite);
        }
        else
        {
            btn_next.setBackgroundResource(R.color.buttonColorGray);
        }
        if (btn_previous.isEnabled())
        {
            btn_previous.setBackgroundResource(R.color.txtColorWhite);
        }
        else
        {
            btn_previous.setBackgroundResource(R.color.buttonColorGray);
        }

    }

    public void onClick(View v)
    {
        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.btn_next_step)
        {
            System.out.println("adapter index" +stepIndex);
            stepIndex++;
            MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
            adapter.getItem(stepIndex);
            System.out.println("Button pressed");
            viewPager.setOffscreenPageLimit(4);
            viewPager.setCurrentItem(stepIndex);
            //Toast.makeText(this,""+Common.currentSalle.getId(),Toast.LENGTH_SHORT).show();
            stepView.go(stepIndex, true);

            if (stepIndex == 1){
                if(Common.currentSalle != null){
                    loadTimeSlotOfSalle(Common.currentSalle.getId());
                }
            }

        }
        else if(v.getId() == R.id.btn_previous_step)
        {
            System.out.println("adapter index" +stepIndex);
            stepIndex--;
            MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
            adapter.getItem(stepIndex);
            viewPager.setCurrentItem(stepIndex);
            stepView.go(stepIndex, true);
            if(stepIndex<2){
                btn_next.setEnabled(true);
                btn_next.setBackgroundColor(ContextCompat.getColor(ReserveActivity.this , android.R.color.white));
            }
        }
    }
    private void setupStepView()
    {
        StepView stepView = findViewById(R.id.step_view);
        List<String> stepList = new ArrayList<>();
        stepList.add("Département");
        stepList.add("Salle");
        stepList.add("Temps");
        stepList.add("Confirmation");
        stepView.setSteps(stepList);
    }
}
