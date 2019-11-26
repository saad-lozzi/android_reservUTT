package com.example.reservutt.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservutt.Models.Salle;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class Common
{
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALLE_STORE = "SALLE_SAVE";
    public static String IS_LOGIN = "isLogin";
    public static Salle currentSalle;

    public Common(FirebaseAuth mAuth)
    {
        this.mAuth = mAuth;

        mAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth mAuth;



}
