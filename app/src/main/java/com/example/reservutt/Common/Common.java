package com.example.reservutt.Common;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class Common
{
    public static String IS_LOGIN = "isLogin";

    public Common(FirebaseAuth mAuth)
    {
        this.mAuth = mAuth;

        mAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth mAuth;



}
