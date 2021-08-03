package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class studentrequestPreviewPage extends AppCompatActivity {
    DatabaseReference mUserRef,databaseofwarden;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    String userid1;
    TextView entryDatesh, Addresssh, exitDatesh,entrytimesh, exittimesh;
    Button accept,decline,gobacktohome,history;
    ImageView tick;
    ConstraintLayout constraintLayout;
    String entrytime,entryDate, exittime,exitDate,Address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentrequest_preview_page);
         userid1 = getIntent().getStringExtra("userkey1");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("OutpassRequesttoParent");
        databaseofwarden = FirebaseDatabase.getInstance().getReference().child("databaseofwarden");

        history = findViewById(R.id.history2);
        entryDatesh = (TextView) findViewById(R.id.edated);
        exitDatesh = (TextView) findViewById(R.id.d);
        entrytimesh =(TextView)  findViewById(R.id.entimedw);
        exittimesh =(TextView)  findViewById(R.id.extimed);
        Addresssh = (TextView) findViewById(R.id.addressd);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        accept = findViewById(R.id.acceptd);
        decline = findViewById(R.id.Declined);
        tick = findViewById(R.id.tick);
        gobacktohome = findViewById(R.id.gobacktohome);
        gobacktohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(studentrequestPreviewPage.this,ParentsHome.class));
                finish();

            }
        });
        constraintLayout = findViewById(R.id.constraintLayout1);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfromaction1(userid1);

            }
        });
       decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declinereq(userid1);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentrequestPreviewPage.this,cardViewwaliHistory.class);
                intent.putExtra("userkey9810",userid1);
                startActivity(intent);

            }
        });
        LoadUser();
        mUserRef.child(userid1).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child("status").getValue().toString().equals("AcceptedByParents") || snapshot.child("status").getValue().toString().equals("journey completed succesfully"))
                {
                    constraintLayout.setVisibility(View.GONE);
                    tick.setVisibility(View.VISIBLE);
                    gobacktohome.setVisibility(View.VISIBLE);
                }
                if(snapshot.child("status").getValue().toString().equals("Declined"))
                {
                    Toast.makeText(studentrequestPreviewPage.this,"you declined child request",Toast.LENGTH_LONG ).show();
                    gobacktohome.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.GONE);
                    tick.setBackgroundResource(R.drawable.ic_cross);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void declinereq(String userid1) {
        HashMap hashMap = new HashMap();
        hashMap.put("status","Declined");

        mUserRef.child(userid1).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(studentrequestPreviewPage.this,"you declined child request",Toast.LENGTH_LONG ).show();
                    gobacktohome.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(studentrequestPreviewPage.this,"some error occured",Toast.LENGTH_LONG ).show();
                }

            }
        });
    }

    private void perfromaction1(String userid1)
    {

        HashMap hashMap = new HashMap();

        hashMap.put("status","AcceptedByParents");
        mUserRef.child(userid1).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(studentrequestPreviewPage.this,"you accepted child request",Toast.LENGTH_LONG ).show();
                   constraintLayout.setVisibility(View.GONE);
                    tick.setVisibility(View.VISIBLE);
                    gobacktohome.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(studentrequestPreviewPage.this,"some error occured",Toast.LENGTH_LONG ).show();
                }

            }
        });
        mUserRef.child(userid1).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    HashMap hashMap = new HashMap();
                    hashMap.put("address", snapshot.child("address").getValue());
                    hashMap.put("exitDate", snapshot.child("exitDate").getValue());
                    hashMap.put("exitTime", snapshot.child("exitTime").getValue());
                    hashMap.put("entryTime", snapshot.child("entryTime").getValue());
                    hashMap.put("entryDate", snapshot.child("entryDate").getValue());
                    hashMap.put("name",snapshot.child("Name").getValue());
                    hashMap.put("useridstudent",userid1);
                    hashMap.put("status", "AcceptedByParents");
                    hashMap.put("status2", "pendingbywarden");
                    hashMap.put("useridparents", mUser.getUid().toString());
                    databaseofwarden.child(userid1).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task task) {
                            Toast.makeText(studentrequestPreviewPage.this,"you succesfully sent request to warden",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void LoadUser() {
        mUserRef.child(userid1).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                   entrytime = snapshot.child("entryTime").getValue().toString();
                      entryDate = snapshot.child("entryDate").getValue().toString();
                     exittime = snapshot.child("exitTime").getValue().toString();
                       exitDate = snapshot.child("exitDate").getValue().toString();
                      Address = snapshot.child("address").getValue().toString();
                    entryDatesh.setText(entryDate);
                    exitDatesh.setText(exitDate);
                    entrytimesh.setText( entrytime);
                    exittimesh.setText(exittime);
                    Addresssh.setText(Address);

                }
                else {
                    Toast.makeText(studentrequestPreviewPage.this,"data not found",Toast.LENGTH_LONG).show();

                }

               }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(studentrequestPreviewPage.this,""+error.getMessage().toString(),Toast.LENGTH_LONG).show();



            }
        });


    }


}