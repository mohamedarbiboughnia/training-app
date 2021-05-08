package com.example.loginapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    View v;

    Button resendCode;
    TextView verifyMsg;
    FirebaseAuth fAuth ;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home2, container, false);

        // receyclerview
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<formation> options =
                new FirebaseRecyclerOptions.Builder<formation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foramtions"), formation.class)
                        .build();


        mAdapter=new WordListAdapter(options);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {

            resendCode= view.findViewById(R.id.verifyNow);
            verifyMsg=view.findViewById(R.id.verifyMsg);
            FirebaseUser user =fAuth.getCurrentUser();



            if (!user.isEmailVerified()){
                resendCode.setVisibility(View.VISIBLE);
                verifyMsg.setVisibility(View.VISIBLE);
                resendCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText( v.getContext(), "verification Email has been send", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Toast.makeText(v.getContext(), "Email not send" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("tag", "onFailure: email not send "+e.getMessage());
                            }
                        });

                    }
                });

            }


        }

    }
}