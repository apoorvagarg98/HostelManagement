package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class setrealtime extends AppCompatActivity {
    DatabaseReference mUserRef,dalo,completedrequestkadb;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    Random rn;
    int range;
    ImageView imageView;
    Calendar calendar,c2;
    SimpleDateFormat simpleDateFormat,s2;
    TextView entryDatesh, Addresssh, exitDatesh,entrytimesh, exittimesh;
    Button realexittimeanddate,realentrytimeanddate;
    String entrytime,entryDate, exittime,exitDate,Address,studentuserid,useridparent,userid,date,d2,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setrealtime);
        imageView = findViewById(R.id.i);
      userid = getIntent().getStringExtra("userkey67");

        dalo = FirebaseDatabase.getInstance().getReference().child("OutpassRequesttoParent");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("databaseofwarden");
        completedrequestkadb = FirebaseDatabase.getInstance().getReference().child("completedjourneysDetails");
        calendar = Calendar.getInstance();
        c2 = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        s2 = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        date = simpleDateFormat.format(calendar.getTime());
        d2 = simpleDateFormat.format(calendar.getTime());
        rn= new Random();
        range = 40000 - 0+1;
        entryDatesh = (TextView) findViewById(R.id.eedate);
        exitDatesh = (TextView) findViewById(R.id.eexdate);
        entrytimesh =(TextView)  findViewById(R.id.eetime);
        exittimesh =(TextView)  findViewById(R.id.eextime);
        Addresssh = (TextView) findViewById(R.id.aaddress);
        realexittimeanddate = findViewById(R.id.realexittimeanddate);
        realentrytimeanddate = findViewById(R.id.realcamebacktimeanddate);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        LoadUser();
       realexittimeanddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfromaction1(userid,studentuserid,useridparent);

            }
        });
        realentrytimeanddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfromaction2(userid,studentuserid,useridparent);
                imageView.setVisibility(View.VISIBLE);

            }
        });
       /* mUserRef.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child("realentrydateandtime").getValue().toString()!=null)
                {
                    realentrytimeanddate.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
                if(snapshot.child("realexitdateandtime").getValue().toString()!=null)
                { realexittimeanddate.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(setrealtime.this,"error occured",Toast.LENGTH_LONG).show();

            }
        });*/
    }

    private void perfromaction2(String userid, String studentuserid, String useridparent) {
        imageView.setVisibility(View.VISIBLE);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("realentrydateandtime",d2);
        hashMap2.put("status","journey completed succesfully");
        dalo.child(studentuserid).child(useridparent).updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(setrealtime.this,"you gave the real entry time",Toast.LENGTH_LONG).show();
                Toast.makeText(setrealtime.this,"trip completed",Toast.LENGTH_LONG).show();

            }
        });
        mUserRef.child(userid).updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(setrealtime.this,"you gave the real entry time",Toast.LENGTH_LONG);
                Toast.makeText(setrealtime.this,"trip completed",Toast.LENGTH_LONG);

            }
        });


       realentrytimeanddate.setVisibility(View.GONE);
       int requesuniqueid = rn.nextInt(range)+0;

        HashMap hashMap3 = new HashMap();
        hashMap3.put("name",name);
        hashMap3.put("adress",Address);
        hashMap3.put("exitTime",exittime);
        hashMap3.put("exitDate",exitDate);
        hashMap3.put("entryTime",entrytime);
        hashMap3.put("entryDate",entryDate);

        hashMap3.put("useridparents",useridparent);
        hashMap3.put("useridstudent",studentuserid);
        hashMap3.put("useridstudent",studentuserid);
        hashMap3.put("realentrydateandtime",d2);
        hashMap3.put("realexitdateandtime",date);



        completedrequestkadb.child(studentuserid).child(String.valueOf(requesuniqueid)).updateChildren(hashMap3).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(setrealtime.this,"alldone",Toast.LENGTH_LONG).show();


            }
        });
    }

    private void perfromaction1(String userid, String studentuserid, String useridparent) {
        HashMap hashMap = new HashMap();
        hashMap.put("realexitdateandtime",date);
        dalo.child(studentuserid).child(useridparent).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(setrealtime.this,"you gave the real exit time",Toast.LENGTH_LONG).show();

            }
        });
        mUserRef.child(userid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                Toast.makeText(setrealtime.this,"you gave the real exit time",Toast.LENGTH_LONG).show();

            }
        });

        realexittimeanddate.setVisibility(View.GONE);


    }

    private void LoadUser() {

            mUserRef.child(userid).addValueEventListener(new ValueEventListener() {
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

                        entryDatesh.setText(entryDate);
                        exitDatesh.setText(exitDate);
                        entrytimesh.setText( entrytime);
                        exittimesh.setText(exittime);
                        Addresssh.setText(Address);

                    }
                    else {
                        Toast.makeText(setrealtime.this,"data not found",Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(setrealtime.this,""+error.getMessage().toString(),Toast.LENGTH_LONG).show();



                }
            });

        }
}