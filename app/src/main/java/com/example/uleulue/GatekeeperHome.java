package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GatekeeperHome extends AppCompatActivity {
    private FirebaseUser user3;
    private DatabaseReference reference3;
    private String userId3;
    private Button logout3,viewacc,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatekeeper_home);
        logout3 = (Button) findViewById(R.id.crequests);
        logout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences5 = getSharedPreferences("checkbox5",MODE_PRIVATE);

                SharedPreferences.Editor editor5 = preferences5.edit();
                editor5.putString("remember5","false");
                editor5.apply();
                finish();
            }
        });

    user3 = FirebaseAuth.getInstance().getCurrentUser();
    reference3 = FirebaseDatabase.getInstance().getReference("gusers");
    viewacc=findViewById(R.id.crequests4);
    userId3 = user3.getUid();
        viewacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GatekeeperHome.this, viewacceptedrequest.class);

                startActivity(intent);
            }
                });
        history = findViewById(R.id.chistories);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GatekeeperHome.this, forHistoryFindStudentsInWarden.class));

            }
        });

    final TextView greetingTextView = (TextView) findViewById(R.id.cmeratext);
    final TextView fullnameTextView = (TextView) findViewById(R.id.cnameLabel2);
    final TextView emailTextView = (TextView) findViewById(R.id.cemailadressdlabel);
    final TextView phonenumberTextView = (TextView) findViewById(R.id.cphonenumberLabel2);
    final TextView adress= (TextView) findViewById(R.id.caddress);


        reference3.child(userId3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user3 user3profile = snapshot.getValue(user3.class);
                if(user3profile != null)
                {
                    String fullname1 = user3profile.fullname1;
                    String email1 = user3profile.email1;
                    String phonenumber1 = user3profile.phonenumber1;
                    String address1 = user3profile.address;



                    greetingTextView.setText("WELCOME " + fullname1.toUpperCase());
                    fullnameTextView.setText("Name - " + fullname1.toUpperCase());
                    emailTextView.setText("Email - " + email1);
                    phonenumberTextView.setText("Phone Number - " + phonenumber1);
                    adress.setText("Address: " + address1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GatekeeperHome.this,"something went wrong",Toast.LENGTH_LONG).show();
            }
        });

    }
}
