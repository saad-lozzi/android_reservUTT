package com.example.reservutt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservutt.Common.Services;
import com.example.reservutt.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.OnClick;

public class Login2Activity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;

    private static final String TAG = "Login2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.btnLogin)
        {
            EditText emailET = (EditText) findViewById(R.id.edittxtLoginEmail);

            EditText passwordET = (EditText) findViewById(R.id.edittxtLoginPassword);

            String email = emailET.getText().toString();

            String password = passwordET.getText().toString();

            if (email.matches("")|| password.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Email or password not specified.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            loginUser(email, password);

            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);
        }


        }
    }

    public String loginUser(String email, String password)
    {
        System.out.println("Email "+email);

            mAuth.signInWithEmailAndPassword(email, password)

                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                //updateUI(null);
                            }

                            // ...
                        }
                    });

        return email;
    }
}
