package com.example.reservutt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.CollectionReference;

public class ReserveOneActivity extends AppCompatActivity {

    CollectionReference allSallesRef;

    CollectionReference depRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_one);

        String[] depNameList = new String[]{"Veuillez choisir le département", "A", "B", "C", "D", "E", "F", "M", "N", "X"};

        Spinner spinner = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, depNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
