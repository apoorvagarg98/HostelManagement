package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParentsHome extends AppCompatActivity {
    private FirebaseUser user4;
    public DatabaseReference reference;
    private String userId;
    private Button logout;
    private Button buttons;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_home);


        buttons = (Button) findViewById(R.id.buttons);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences2 = getSharedPreferences("checkbox2",MODE_PRIVATE);

                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.putString("remember2","false");
                editor2.apply();
                finish();
            }
        });



         user4 = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("pusers");
        userId = user4.getUid();

        final TextView fullnameTextView = (TextView) findViewById(R.id.nameLabel);
        final TextView emailTextView = (TextView) findViewById(R.id.Emailadressdlabel);
        final TextView phonenumberTextView = (TextView) findViewById(R.id.phonenumberLabel);
        final TextView usnTextView = (TextView) findViewById(R.id.USNlabel);
        final TextView studentname = (TextView) findViewById(R.id.studentname);
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user4 user4profile = snapshot.getValue(user4.class);
                if (user4profile != null) {
                    String fullname = user4profile.parentsname;
                    String email = user4profile.email;
                    String phone = user4profile.phonenumber;
                    String usn = user4profile.usn;
                    String student = user4profile.studentname;

                    fullnameTextView.setText("Name - " + fullname);
                    emailTextView.setText("email - " + email);
                    phonenumberTextView.setText("phoneNumber- " + phone);
                    usnTextView.setText("usn- " + usn);
                    studentname.setText("Student Name - " + student);
                    buttons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ParentsHome.this, studentsDhundo.class);
                            intent.putExtra("s", fullname);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ParentsHome.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
    }
