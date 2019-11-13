package com.example.reservutt.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.reservutt.Adapter.TypeSalleAdapter;
import com.example.reservutt.Interface.ITypeSallesLoadListener;
import com.example.reservutt.Models.TypeSalle;
import com.example.reservutt.Models.User;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ITypeSallesLoadListener{

    Unbinder unbinder;

    @BindView(R.id.type_salle)
    RecyclerView recycler_type_salle;

    private FirebaseAuth mAuth;

    CollectionReference userRef;

    CollectionReference typeSallesRef;

    ITypeSallesLoadListener iTypeSallesLoadListener;

    RecyclerView recyclerView;

    public HomeFragment() {
        typeSallesRef = FirebaseFirestore.getInstance().collection("Type_Salles");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        showData();

        iTypeSallesLoadListener = this;

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.type_salle);

        loadTypeSalles();



        return rootView;
    }

    public void loadTypeSalles()
    {
        typeSallesRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<TypeSalle> typeSalles = new ArrayList<>();
                        if (task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot typesalleSnapShot:task.getResult())
                            {
                                TypeSalle typeSalle = typesalleSnapShot.toObject(TypeSalle.class);
                                typeSalles.add(typeSalle);
                            }
                            iTypeSallesLoadListener.onTypeSalleLoadSuccess(typeSalles);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iTypeSallesLoadListener.onTypeSalleLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onTypeSalleLoadSuccess(List<TypeSalle> typeSalles)
    {
        System.out.println("Here in success");

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter( new TypeSalleAdapter(getActivity(),typeSalles) );
    }


    @Override
    public void onTypeSalleLoadFailed(String message)
    {

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

                        TextView txtvUsername = (TextView)getView().findViewById(R.id.txt_user_name);

                        User currentUser = document.toObject(User.class);

                        txtvUsername.setText(currentUser.getUsername());

                        TextView txtvProfession = (TextView)getView().findViewById(R.id.txt_member_type);

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
