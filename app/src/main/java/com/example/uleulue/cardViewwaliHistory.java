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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class cardViewwaliHistory extends AppCompatActivity {
    FirebaseRecyclerOptions<sabconstructorhistoryke> options;
    FirebaseRecyclerAdapter<sabconstructorhistoryke,layoutHistoryHolder>adapter;
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser muser;
    RecyclerView recyclerView;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewwali_history);
        mAuth = FirebaseAuth.getInstance();

        muser = mAuth.getCurrentUser();

        userid = getIntent().getStringExtra("userkey9810");
        recyclerView = findViewById(R.id.r45);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserRef = FirebaseDatabase.getInstance().getReference().child("completedjourneysDetails").child(userid);



        loadUsers( "");


    }




    private void loadUsers(String s) {



        Query query = mUserRef.orderByChild("adress").startAt(s).endAt(s+ "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<sabconstructorhistoryke>().setQuery(query,sabconstructorhistoryke.class).build();
        adapter = new FirebaseRecyclerAdapter<sabconstructorhistoryke, layoutHistoryHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull layoutHistoryHolder holder,@SuppressLint("RecyclerView") int position, @NonNull sabconstructorhistoryke model) {

                holder.realexinh.setText("real exit time and date- " +model.getRealexitdateandtime());
                holder.realeninh.setText("real entry time and date-"+model.getRealentrydateandtime());
                holder.entrydateinh.setText("entry date- " + model.getEntrydate());
                holder.entrytimeinh.setText("entry time- " +model.getEntryTime());
                holder.exitdateinh.setText("exit date- " +model.getExitDate());
                holder.exittimeinh.setText("exit Time-" +model.getExitTime());
                holder.nameinh.setText("Name-" +model.getname());
                holder.addressinh.setText("Address- " +model.getAdress());

            }


            @NonNull

            @Override
            public layoutHistoryHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layoutof_history, parent, false);


                return new layoutHistoryHolder(view);
            }
        }

        ;
        adapter.startListening();
        recyclerView.setAdapter(adapter);


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

        Query query = mUserRef.orderByChild("entryDate").startAt(s).endAt(s+ "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<sabconstructorhistoryke>().setQuery(query,sabconstructorhistoryke.class).build();
        adapter = new FirebaseRecyclerAdapter<sabconstructorhistoryke, layoutHistoryHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull layoutHistoryHolder holder,@SuppressLint("RecyclerView") int position, @NonNull sabconstructorhistoryke model) {
               if(s.equals(model.getEntrydate())){
                holder.realexinh.setText("real exit time and date- " +model.getRealexitdateandtime());
                holder.realeninh.setText("real entry time and date-"+model.getRealentrydateandtime());
                holder.entrydateinh.setText("entry date- " + model.getEntrydate());
                holder.entrytimeinh.setText("entry time- " +model.getEntryTime());
                holder.exitdateinh.setText("exit date- " +model.getExitDate());
                holder.exittimeinh.setText("exit Time-" +model.getExitTime());
                holder.nameinh.setText("Name-" +model.getname());
                holder.addressinh.setText("Address- " +model.getAdress());

            }
               else
               {
                   holder.itemView.setVisibility(View.GONE);
                   holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
               }

            }


            @NonNull

            @Override
            public layoutHistoryHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layoutof_history, parent, false);


                return new layoutHistoryHolder(view);
            }
        }

        ;
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

}

