package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth ;
    Button resendCode;
    TextView verifyMsg;
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth= FirebaseAuth.getInstance();
//        resendCode=findViewById(R.id.verifyNow);
//        verifyMsg=findViewById(R.id.verifyMsg);

        FirebaseUser user =fAuth.getCurrentUser();
       // receyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recview);
//   mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FirebaseRecyclerOptions<formation> options =
//                new FirebaseRecyclerOptions.Builder<formation>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foramtions"), formation.class)
//                        .build();
//
//
//       mAdapter=new WordListAdapter(options);
      // mRecyclerView.setAdapter(mAdapter);

        //verification de email


        //menu
        BottomNavigationView btnNav=findViewById(R.id.navigationView);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_lyout,new HomeFragment()).commit();




    }



    //menuMethode
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.item1:

                    setFragment(new HomeFragment());
                    return true;

                case R.id.item2:
                    setFragment(new AchFragment());
                    return true;

                case R.id.item3:
                    setFragment(new settFragment());
                    return true;
                default:
                    return false;

            }

        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction  fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_lyout,fragment);
        fragmentTransaction.commit();

    }


    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAdapter.stopListening();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item =menu.findItem(R.id.search);

        SearchView searchview=(SearchView)item.getActionView();

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void processsearch(String s){
        FirebaseRecyclerOptions<formation> options =
                new FirebaseRecyclerOptions.Builder<formation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foramtions").orderByChild("formationName").startAt(s).endAt(s+"\uf8ff"), formation.class)
                        .build();

        mAdapter=new WordListAdapter(options);
        mAdapter.startListening();
        mRecyclerView.setAdapter(mAdapter);

    }



}

