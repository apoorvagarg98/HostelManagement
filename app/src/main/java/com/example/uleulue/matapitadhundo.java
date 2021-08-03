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
import android.view.View;
import android.view.ViewGroup;
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

public class matapitadhundo extends AppCompatActivity {
    FirebaseRecyclerOptions<parentsdhundo> options;
    FirebaseRecyclerAdapter<parentsdhundo,matapitadhundoHolder>adapter;
   DatabaseReference mUserRef;
   FirebaseAuth mAuth;
   FirebaseUser muser;
   RecyclerView recyclerView;
    parentsdhundo obj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matapitadhundo);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserRef = FirebaseDatabase.getInstance().getReference().child("pusers");

        mAuth = FirebaseAuth.getInstance();
         obj = new parentsdhundo();
         String parentsname = getIntent().getStringExtra("p");
        muser = mAuth.getCurrentUser();

        loadUsers( "", parentsname);


    }




    private void loadUsers(String s,String parentsname) {

       Toast.makeText(matapitadhundo.this,parentsname,Toast.LENGTH_LONG).show();
     Query query = mUserRef.orderByChild("email").startAt(s).endAt(s+ "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<parentsdhundo>().setQuery(query,parentsdhundo.class).build();
        adapter = new FirebaseRecyclerAdapter<parentsdhundo, matapitadhundoHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull matapitadhundoHolder holder, @SuppressLint("RecyclerView") int position, @NonNull parentsdhundo model) {
                if(parentsname.equals(model.getParentsname().toString())){

                    holder.username.setText(model.getParentsname());
                    holder.profession.setText(model.getPhonenumber());
                    holder.studentsname.setText(model.getStudentname());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(matapitadhundo.this, matapitaprofileView.class);
                            intent.putExtra("userkey", getRef(position).getKey().toString());
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
                public matapitadhundoHolder onCreateViewHolder (@NonNull ViewGroup parent,
                int viewType){
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_parents, parent, false);


                    return new matapitadhundoHolder(view);
                }
            }

            ;
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
}