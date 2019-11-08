package com.example.reservutt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.reservutt.Common.Common;
import com.example.reservutt.Fragments.HomeFragment;
import com.example.reservutt.Fragments.ReservationsFragment;
import com.example.reservutt.Fragments.ReserveFragment;
import com.example.reservutt.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
    private FirebaseAuth mAuth;

    protected BottomNavigationView navigationView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

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
}
