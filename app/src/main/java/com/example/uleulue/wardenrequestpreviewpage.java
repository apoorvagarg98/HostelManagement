package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;

public class wardenrequestpreviewpage extends AppCompatActivity {
    DatabaseReference mUserRef2,dalo;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    String userid2;
    TextView entryDateshw, Addressshw, exitDateshw,entrytimeshw, exittimeshw,namet;
    Button accept,decline,gobacktohome;
    ImageView tick,cross;

    String entrytime,entryDate, exittime,exitDate,Address,studentuserid,useridparent,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardenrequestpreviewpage);
        userid2 = getIntent().getStringExtra("userkey5");
        mUserRef2 = FirebaseDatabase.getInstance().getReference().child("databaseofwarden");
        dalo = FirebaseDatabase.getInstance().getReference().child("OutpassRequesttoParent");


        entryDateshw = (TextView) findViewById(R.id.edatedw);
        namet = (TextView) findViewById(R.id.nameDw);
        exitDateshw = (TextView) findViewById(R.id.dw);
        entrytimeshw =(TextView)  findViewById(R.id.entimedw);
        exittimeshw =(TextView)  findViewById(R.id.extimedw);
        Addressshw = (TextView) findViewById(R.id.addressdw);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        accept = findViewById(R.id.acceptdw);
        decline = findViewById(R.id.Declinedw);
        tick = findViewById(R.id.tickw);
        cross = findViewById(R.id.cross);

        loaduser();
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfromaction1(userid2,studentuserid,useridparent);

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cross.setVisibility(View.VISIBLE);
                perfromaction2(userid2,studentuserid,useridparent);

            }
        });
        mUserRef2.child(userid2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    if(snapshot.child("status2").getValue().toString().equals("Accepted By Warden"))
                    {
                        tick.setVisibility(View.VISIBLE);
                        accept.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                    }
                    else if(snapshot.child("status2").getValue().toString().equals("Declined By Warden"))
                    {
                        tick.setVisibility(View.GONE);
                        accept.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                        cross.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }


    private void perfromaction1(String userid2, String studentuserid, String useridparent) {

        HashMap hashMap = new HashMap();
        hashMap.put("status2","Accepted By Warden");
        dalo.child(studentuserid).child(useridparent).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(wardenrequestpreviewpage.this,"you accepted child reques",Toast.LENGTH_LONG);

            }
        });
        HashMap hashMap2 = new HashMap();
        hashMap2.put("status2","Accepted By Warden");
        mUserRef2.child(studentuserid).updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(wardenrequestpreviewpage.this,"you accepted child request",Toast.LENGTH_LONG);

            }
        });


    }
    private void perfromaction2(String userid2, String studentuserid, String useridparent) {
decline.setVisibility(View.GONE);
accept.setVisibility(View.GONE);
        HashMap hashMap = new HashMap();
        hashMap.put("status2","Declined By Warden");
        dalo.child(studentuserid).child(useridparent).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(wardenrequestpreviewpage.this,"you declined child request",Toast.LENGTH_LONG);

            }
        });
        HashMap hashMap2 = new HashMap();
        hashMap2.put("status2","Declined By Warden");
        mUserRef2.child(userid2).updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(wardenrequestpreviewpage.this,"you declined child request",Toast.LENGTH_LONG);

            }
        });

    }

    private void loaduser() {
        mUserRef2.child(userid2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    entrytime = snapshot.child("entryTime").getValue().toString();
                    entryDate = snapshot.child("entryDate").getValue().toString();
                    exittime = snapshot.child("exitTime").getValue().toString();
                    exitDate = snapshot.child("exitDate").getValue().toString();
                    Address = snapshot.child("address").getValue().toString();
                    studentuserid = snapshot.child("useridstudent").getValue().toString();
                    useridparent = snapshot.child("useridparents").getValue().toString();
                    name = snapshot.child("name").getValue().toString();

                    entryDateshw.setText(entryDate);
                    exitDateshw.setText(exitDate);
                    entrytimeshw.setText( entrytime);
                    exittimeshw.setText(exittime);
                    Addressshw.setText(Address);
                    namet.setText(name);

                }
                else {
                    Toast.makeText(wardenrequestpreviewpage.this,"data not found",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(wardenrequestpreviewpage.this,""+error.getMessage().toString(),Toast.LENGTH_LONG).show();



            }
        });


    }




}