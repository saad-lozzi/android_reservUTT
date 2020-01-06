package com.example.reservutt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.reservutt.Common.Common;
import com.example.reservutt.Models.User;
import com.example.reservutt.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    CollectionReference userRef;

    private FirebaseAuth mAuth;

    private Spinner spinner;

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.mAuth = FirebaseAuth.getInstance();

        if (this.mAuth.getCurrentUser() != null)
        {
            Intent i = new Intent(SignupActivity.this, HomeActivity.class);

            startActivity(i);
        }

        super.onCreate(savedInstanceState);

        //addItemsOnSpinner();

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
            EditText usernameET = (EditText) findViewById(R.id.edittxtUsername);
            Spinner professionSP = (Spinner) findViewById(R.id.spinnerProfession);
            EditText passwordConfET = (EditText) findViewById(R.id.edittxtPasswordConf);
            String passwordConf = passwordConfET.getText().toString();
            String profession = professionSP.getSelectedItem().toString();
            String username = usernameET.getText().toString();
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();

            if (email.matches("")|| password.matches("") || profession.matches("") || username.matches("") || passwordConf.matches(""))
            {

                Toast.makeText(getApplicationContext(), "Veuillez compléter toutes les informations.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (passwordConform() == true)
                {
                    if(checkPasswordMatch() == true)
                    {
                        createAccount(email, password, username, profession);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Les mots de passes ne sont pas identiques" ,
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "\n" +
                                    "    Votre mot de passe doit contenir\n" +
                                    "    8 caractères dont 1 chiffre\n" +
                                    "    1 majuscule\n" +
                                    "    1 lettre spéciale\n" +
                                    "    pas d'espaces\n" ,
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public Boolean checkPasswordMatch()
    {
        Boolean match;
        EditText passwordET = (EditText) findViewById(R.id.edittxtPassword);
        String password = passwordET.getText().toString();
        EditText passwordConfET = (EditText) findViewById(R.id.edittxtPasswordConf);
        String passwordConf = passwordConfET.getText().toString();

        match = (password.matches(passwordConf)) ? true : false;

        return match;
    }
    public Boolean passwordConform()
    {
        Boolean conform;
        EditText passwordET = (EditText) findViewById(R.id.edittxtPassword);
        String password = passwordET.getText().toString();

        String pattern = "((?=.*[a-z])(?=.*[A-Z]).{8,40})";

        conform = (password.matches(pattern)) ? true : false;

        return conform;
    }

    public void createAccount(final String email, String password, final String username, final String profession)
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
                            //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            //FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            //        .setTimestampsInSnapshotsEnabled(true)
                            //        .build();
                            //firestore.setFirestoreSettings(settings);

                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            userRef = FirebaseFirestore.getInstance().collection("User");

                            String userId = currentUser.getUid().toString();

                            User user = new User( userId,
                                    username,
                                    email,
                                    profession);

                            System.out.println("Creating users infos");

                            userRef.document(userId)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(SignupActivity.this, "Merci", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(SignupActivity.this, HomeActivity.class);

                                startActivity(i);
                            }
                            }).addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(SignupActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            //updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(getApplicationContext(), "Inscription échouée :"+ task.getException().getMessage(),

                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    /*public void addItemsOnSpinner(){
        spinner = (Spinner) findViewById(R.id.spinnerProfession);
        List<String> list = new ArrayList<String>();
        list.add("Étudiant");
        list.add("Professeur");
        list.add("Formateur");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }*/
}
