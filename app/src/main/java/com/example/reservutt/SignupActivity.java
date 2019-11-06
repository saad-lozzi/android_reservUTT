package com.example.reservutt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.reservutt.Common.Services;
import com.example.reservutt.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        Button btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

    //    updateUI(currentUser);
    }

    @Override
    public void onClick(View v)
    {
        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.btnSignup)
        {
            EditText emailET = (EditText) findViewById(R.id.edittxtEmail);

            EditText passwordET = (EditText) findViewById(R.id.edittxtPassword);

            String email = emailET.getText().toString();

            String password = passwordET.getText().toString();

            if (email.matches("")|| password.matches(""))
            {
                Toast.makeText(getApplicationContext(), "Email or password not specified.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                createAccount(email, password);

                Intent i = new Intent(this, LoginActivity.class);

                startActivity(i);
            }
        }
    }

    public void createAccount(String email, String password)
    {
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(getApplicationContext(), "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
