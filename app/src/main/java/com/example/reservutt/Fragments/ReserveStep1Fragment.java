package com.example.reservutt.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservutt.Adapter.MySalleAdapter;
import com.example.reservutt.Common.SpaceItemDecoration;
import com.example.reservutt.Interface.IAllSallesLoadListener;
import com.example.reservutt.Interface.IBatimentLoadListener;
import com.example.reservutt.Models.Salle;
import com.example.reservutt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ReserveStep1Fragment extends Fragment implements IAllSallesLoadListener, IBatimentLoadListener {
    CollectionReference allSallesRef;

    CollectionReference depRef;

    View rootView;

    IAllSallesLoadListener iAllSallesLoadListener;

    IBatimentLoadListener iBatimentLoadListener;

    MaterialSpinner spinner;

    List<String> depList;

    RecyclerView recyclerView;

    AlertDialog dialog;



    static ReserveStep1Fragment instance;

    public static ReserveStep1Fragment getInstance()
    {
        if (instance == null)
        {
            instance = new ReserveStep1Fragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allSallesRef = FirebaseFirestore.getInstance().collection("Salles");

        iAllSallesLoadListener = this;

        iBatimentLoadListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView =  inflater.inflate(R.layout.fragment_reserve_step_one, container, false);

        //rootView = inflater.inflate(R.layout.fragment_reserve_step_one, container, false);
        String[] depNameList = new String[]{"Veuillez choisir le Batiment", "Batiment A", "Batiment B", "Batiment C",
                "Batiment D", "Batiment E", "Batiment F", "Batiment M", "Batiment N", "Batiment X"};

        spinner = itemView.findViewById(R.id.spinnerr);

        recyclerView = itemView.findViewById(R.id.recycler_salle);

        spinner.setItems(depNameList);

        initView();
        loadAllSalle();

        return itemView;
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
    }


    private void loadAllSalle() {
        allSallesRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<String> list = new ArrayList<String>();
                            list.add("Veuillez choisir le département");
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                list.add(documentSnapshot.getId());
                                iAllSallesLoadListener.onAllSalleLoadSuccess(list);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllSallesLoadListener.onAllSalleLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalleLoadSuccess(List<String> depNameList) {
        spinner.setItems(depNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position > 0)
                {
                    loadSalleOfBatiment(item.toString());
                }
                else
                    recyclerView.setVisibility(View.GONE);
            }
        });
    }
    private void loadSalleOfBatiment(String salleName) {
        dialog.show();

        depRef = FirebaseFirestore.getInstance()
                .collection("Salles")
                .document(salleName)
                .collection("salle");

        depRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Salle> list = new ArrayList<>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                    {
                        Salle salle = documentSnapshot.toObject(Salle.class);
                        salle.setId(documentSnapshot.getId());
                        list.add(salle);
                    }
                    iBatimentLoadListener.onBatimentLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBatimentLoadListener.onBatimentLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalleLoadFailed(String message) {
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBatimentLoadSuccess(List<Salle> salleList) {
        MySalleAdapter adapter = new MySalleAdapter(getActivity(),salleList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        dialog.dismiss();
    }

    @Override
    public void onBatimentLoadFailed(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
