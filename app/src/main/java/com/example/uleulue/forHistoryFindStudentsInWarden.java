package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

public class forHistoryFindStudentsInWarden extends AppCompatActivity {
    FirebaseRecyclerOptions<parentsdhundo> options;
    FirebaseRecyclerAdapter<parentsdhundo,matapitadhundoHolder>adapter;
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser muser;
    RecyclerView recyclerView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_history_find_students_in_warden);
        recyclerView1 = findViewById(R.id.rc0);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        mUserRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();

        loadUsers("");
    }

    private void loadUsers(String s) {

        Query query = mUserRef.orderByChild("email").startAt(s).endAt(s+ "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<parentsdhundo>().setQuery(query,parentsdhundo.class).build();
        adapter = new FirebaseRecyclerAdapter<parentsdhundo, matapitadhundoHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  matapitadhundoHolder holder, @SuppressLint("RecyclerView") int position, @NonNull  parentsdhundo model) {

                    holder.username.setText(model.getFullname());
                    holder.profession.setText(model.getPhonenumber());
                    holder.studentsname.setText(model.getParentsname());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(forHistoryFindStudentsInWarden.this,cardViewwaliHistory.class);
                            intent.putExtra("userkey9810",getRef(position).getKey().toString());
                            startActivity(intent);

                        }
                    });

            }



            @NonNull

            @Override
            public matapitadhundoHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_parents,parent,false);



                return new matapitadhundoHolder(view);
            }
        };
        adapter.startListening();
        recyclerView1.setAdapter(adapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadUsers("");
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                process(query);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                process(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);


    }

    private void process(String s) {
        Query query = mUserRef.orderByChild("fullname").startAt(s).endAt(s+ "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<parentsdhundo>().setQuery(query,parentsdhundo.class).build();
        adapter = new FirebaseRecyclerAdapter<parentsdhundo, matapitadhundoHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  matapitadhundoHolder holder, @SuppressLint("RecyclerView") int position, @NonNull  parentsdhundo model) {
               if(s.equals(model.getFullname())){
                holder.username.setText(model.getFullname());
                holder.profession.setText(model.getPhonenumber());
                holder.studentsname.setText(model.getParentsname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(forHistoryFindStudentsInWarden.this,cardViewwaliHistory.class);
                        intent.putExtra("userkey9810",getRef(position).getKey().toString());
                        startActivity(intent);

                    }
                });}
               else
               {
                   holder.itemView.setVisibility(View.GONE);
                   holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
               }

            }



            @NonNull

            @Override
            public matapitadhundoHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_parents,parent,false);



                return new matapitadhundoHolder(view);
            }
        };
        adapter.startListening();
        recyclerView1.setAdapter(adapter);


    }

}