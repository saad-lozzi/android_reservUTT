package com.example.reservutt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reservutt.Adapter.MyViewPagerAdapter;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserve);

        StepView stepView = findViewById(R.id.step_view);

        ViewPager viewPager = findViewById(R.id.view_pager);

        Button btn_next = (Button) findViewById(R.id.btn_next_step);

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

    private void setColorButton()
    {
        Button btn_next = (Button) findViewById(R.id.btn_next_step);

        Button btn_previous = (Button) findViewById(R.id.btn_previous_step);

        if (btn_next.isEnabled())
        {
            btn_next.setBackgroundResource(R.color.buttonColor);
        }
        else
        {
            btn_next.setBackgroundResource(R.color.buttonColorGray);
        }
        if (btn_previous.isEnabled())
        {
            btn_previous.setBackgroundResource(R.color.buttonColor);
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

        }
        else if(v.getId() == R.id.btn_previous_step)
        {
            //code
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
