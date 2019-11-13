package com.example.reservutt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reservutt.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.mAuth = FirebaseAuth.getInstance();

        if (this.mAuth.getCurrentUser() != null)
        {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);

            startActivity(i);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

        Button btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            System.out.println("User");
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();

            String email = user.getEmail();

            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        }
        else
        {
            System.out.println("No user");
        }


    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user)
    {

    }

    @Override
    public void onClick(View v)
    {
        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.btnLogin)
        {
            Intent i = new Intent(this, Login2Activity.class);

            startActivity(i);
        }
        else if(v.getId() == R.id.btnSignup)
        {
            Intent i = new Intent(this, SignupActivity.class);

            startActivity(i);
        }
    }
}

