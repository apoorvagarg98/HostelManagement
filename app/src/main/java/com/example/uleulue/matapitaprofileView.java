package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Hex;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class matapitaprofileView extends AppCompatActivity {

    DatabaseReference mUserRef,requestRef,warden;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ListView listView;
    String username, phone,email,staddress,stexitDate,stentryDate,stentrytime,  stexittime ,nameinstudentutpass;
    TextView username9810,phone9810,email9810,adress,exitdate,entrydate,exittime,entrytime,nameinoutpass;
    Button btnperfrom,btndecline,historyofstudents;
    CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matapitaprofile_view);
        String userid = getIntent().getStringExtra("userkey");
        Toast.makeText(this , "" + userid, Toast.LENGTH_LONG).show();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("pusers").child(userid);
        requestRef = FirebaseDatabase.getInstance().getReference().child("OutpassRequesttoParent");
        warden = FirebaseDatabase.getInstance().getReference().child("databaseofwarden");

        historyofstudents = findViewById(R.id.ViewbacchokiHistory);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnperfrom = findViewById(R.id.request9810);
        btndecline = findViewById(R.id.cancelRequest);
        username9810 = findViewById(R.id.usernameparentsshow);
        phone9810 = findViewById(R.id.showPhone);
        email9810 = findViewById(R.id.showEmail);
        adress = findViewById(R.id.adress2);
        exitdate = findViewById(R.id.exitDate2);
        entrydate = findViewById(R.id.entryDate2);
        entrytime = findViewById(R.id.entryTime2);
        nameinoutpass = findViewById(R.id.nameinstudentutpass);
        exittime = findViewById(R.id.exitTime2);
        cardView = findViewById(R.id.cardView);





        listView = findViewById(R.id.list);
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listitem,list);
        listView.setAdapter(adapter);


        LoadUser();

        btnperfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfromaction(userid);

            }
        });
        btndecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletereq(userid);

            }
        });





        requestRef.child(mUser.getUid()).child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    list.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        list.add(snapshot1.getValue().toString());
                    }
                    adapter.notifyDataSetChanged();
                    historyofstudents.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(matapitaprofileView.this,cardViewwaliHistory.class);
                            intent.putExtra("userkey9810",mUser.getUid());

                            startActivity(intent);

                        }
                    });

                    if(snapshot.child("status").getValue().toString().equals("pending"))
                    {
                        cardView.setVisibility(View.GONE);
                        btnperfrom.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        listView.setBackgroundColor(Color.GRAY);
                       // pushreqtoparent.setVisibility(View.GONE);

                    }
                    else if(snapshot.child("status").getValue().toString().equals("Declined"))
                    {
                        cardView.setVisibility(View.GONE);
                        btnperfrom.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        listView.setBackgroundColor(Color.RED);
                      //  pushreqtoparent.setVisibility(View.GONE);


                    }
                    else if(snapshot.child("status").getValue().toString().equals("journey completed succesfully"))
                    {
                        cardView.setVisibility(View.GONE);
                        btnperfrom.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        listView.setBackgroundColor(Color.MAGENTA);

                    }
                    else if(snapshot.child("status").getValue().toString().equals("AcceptedByParents"))
                    {

                        cardView.setVisibility(View.GONE);
                        btnperfrom.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                         listView.setBackgroundColor(Color.GREEN);
                       // pushreqtoparent.setVisibility(View.VISIBLE);
                     /*   pushreqtoparent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(matapitaprofileView.this,wardendhundoViaStudents.class);
                                intent.putExtra("ad",snapshot.child("address").getValue().toString());
                                intent.putExtra("ext",snapshot.child("exitTime").getValue().toString());
                                intent.putExtra("exd",snapshot.child("exitDate").getValue().toString());
                                intent.putExtra("ent",snapshot.child("entryTime").getValue().toString());
                                intent.putExtra("end",snapshot.child("entryDate").getValue().toString());
                                intent.putExtra("n",snapshot.child("Name").getValue().toString());
                                intent.putExtra("useridparents",userid);
                                startActivity(intent);

                            }
                        });*/

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(matapitaprofileView.this,"some error occured in loading confirmation status by parents",Toast.LENGTH_LONG).show();

            }

        });



    }

    private void deletereq(String userid) {
        requestRef.child(mUser.getUid()).child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful())
                {Toast.makeText(matapitaprofileView.this, "request removed" , Toast.LENGTH_LONG).show();
                cardView.setVisibility(View.VISIBLE);
                btnperfrom.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);}
                else
                {
                    Toast.makeText(matapitaprofileView.this, "error in cancelling the request" , Toast.LENGTH_LONG).show();
                }


            }
        });
        warden.child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(matapitaprofileView.this, "request removed" , Toast.LENGTH_LONG).show();

            }
        });
    }

    private void perfromaction(String userid ) {
        staddress = adress.getText().toString();
        stexitDate = exitdate.getText().toString();
        stentryDate = entrydate.getText().toString();
        stentrytime = entrytime.getText().toString();
        stexittime = exittime.getText().toString();
        nameinstudentutpass = nameinoutpass.getText().toString();

        HashMap hashMap = new HashMap();
        hashMap.put("status", "pending");
        hashMap.put("address", staddress);
        hashMap.put("exitDate", stexitDate);
        hashMap.put("exitTime", stexittime);
        hashMap.put("entryTime", stentrytime);
        hashMap.put("entryDate", stentryDate);
        hashMap.put("Name",nameinstudentutpass);
        hashMap.put("status2","pending");


        requestRef.child(mUser.getUid()).child(userid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(matapitaprofileView.this, " request sent", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(matapitaprofileView.this, "" + task.getException(), Toast.LENGTH_LONG).show();

                }


            }
        });

    }




    private void LoadUser(){
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    username = snapshot.child("parentsname").getValue().toString();
                    phone = snapshot.child("phonenumber").getValue().toString();
                    email = snapshot.child("email").getValue().toString();
                    username9810.setText(username);
                    phone9810.setText(phone);
                    email9810.setText(email);


                }
                else {
                    Toast.makeText(matapitaprofileView.this,"data not found",Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(matapitaprofileView.this,""+error.getMessage().toString(),Toast.LENGTH_LONG).show();



            }
        });

    }}