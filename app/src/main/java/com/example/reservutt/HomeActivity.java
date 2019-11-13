package com.example.reservutt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservutt.Common.Common;
import com.example.reservutt.Fragments.HomeFragment;
import com.example.reservutt.Fragments.ReservationsFragment;
import com.example.reservutt.Fragments.ReserveFragment;
import com.example.reservutt.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
    private FirebaseAuth mAuth;

    protected BottomNavigationView navigationView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;

    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //showData();

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(this);

        ButterKnife.bind(HomeActivity.this);

        userRef = FirebaseFirestore.getInstance().collection("User");

        if(getIntent() != null)
        {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if(isLogin)
            {

            }
        }


        //showUpdateDialog(user.getUid());
        Fragment fragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();


        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                if(menuItem.getItemId() == R.id.action_home)

                    fragment = new HomeFragment();

                else if (menuItem.getItemId() == R.id.action_reservations)

                    fragment = new ReservationsFragment();

                else if (menuItem.getItemId() == R.id.action_reserve)

                    fragment = new ReserveFragment();
                return loadFragment(fragment);
            }
        }); */

        //Button btnLogout = (Button) findViewById(R.id.logoutBtn);

        //btnLogout.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        FirebaseUser user = mAuth.getCurrentUser();

        v.setSelected(!v.isSelected());

        if(v.getId() == R.id.logoutBtn)
        {
            mAuth.getInstance().signOut();

            Intent i = new Intent(HomeActivity.this, MainActivity.class);

            if(user != null)
            {
                System.out.println("user id : "+ user.getUid());
            }
            else
            {
                System.out.println("user is disconnected");
            }



            startActivity(i);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        Fragment fragment = null;

        if(menuItem.getItemId() == R.id.action_home)

            fragment = new HomeFragment();

        else if (menuItem.getItemId() == R.id.action_reservations)

            fragment = new ReservationsFragment();

        else if (menuItem.getItemId() == R.id.action_reserve)

            fragment = new ReserveFragment();
        return loadFragment(fragment);

    }


    private boolean loadFragment(Fragment fragment)
    {
        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
            .commit();
            return true;
        }
        return false;
    }

    private void showUpdateDialog(final String userId)
    {
        System.out.println("Update is open");

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        final View sheetView = getLayoutInflater().inflate(R.layout.layout_update_information, null);

        Button btn_update = (Button)sheetView.findViewById(R.id.btn_update);
        final TextInputEditText edt_name = (TextInputEditText)sheetView.findViewById(R.id.edt_name);
        final TextInputEditText edt_phone = (TextInputEditText) sheetView.findViewById(R.id.edt_phone);
        final TextInputEditText edt_profession = (TextInputEditText) sheetView.findViewById(R.id.edt_profession);

        btn_update.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public void onClick(View view)
           {
               User user = new User( userId,
                       edt_name.getText().toString(),
                       edt_phone.getText().toString(),
                       edt_profession.getText().toString());
               userRef.document(userId)
                       .set(user)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               bottomSheetDialog.dismiss();
                               Toast.makeText(HomeActivity.this, "Merci", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       bottomSheetDialog.dismiss();
                       Toast.makeText(HomeActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                   }
               });

               bottomSheetDialog.setContentView(sheetView);
               bottomSheetDialog.show();
           }
        });
    }

    public void showData()
    {
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        userRef = FirebaseFirestore.getInstance().collection("User");

        DocumentReference docRef = userRef.document(user.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists())
                    {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        TextView txtvUsername = (TextView) findViewById(R.id.txt_user_name);

                        User currentUser = document.toObject(User.class);

                        txtvUsername.setText(currentUser.getUsername());

                        TextView txtvProfession = (TextView) findViewById(R.id.txt_member_type);

                        txtvProfession.setText(currentUser.getProfession());
                    } else
                        {
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
