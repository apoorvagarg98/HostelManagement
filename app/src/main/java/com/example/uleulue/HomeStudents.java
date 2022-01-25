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

public class HomeStudents extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
   private Button logout,opreq;
    String pname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_students);


        logout = (Button) findViewById(R.id.logoutstudent);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false") ;
                editor.apply();
                finish();

            }
        });
        opreq = (Button) findViewById(R.id.outpassrequeststudent);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.Headlinestudent);
        final TextView fullnameTextView = (TextView) findViewById(R.id.nameLabelstudent);
        final TextView emailTextView = (TextView) findViewById(R.id.Emailadressdlabelstudent);
        final TextView phonenumberTextView = (TextView) findViewById(R.id.phonenumberLabelstudent);
        final TextView usnTextView = (TextView) findViewById(R.id.USNlabelstudent);
        final TextView parentsname = (TextView) findViewById(R.id.pnamestudent);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user userprofile = snapshot.getValue(user.class);
                if(userprofile != null)
                {
                    String fullname = userprofile.fullname;
                    String email = userprofile.email;
                    String phone = userprofile.phonenumber;
                    String usn = userprofile.usn;
                  pname = userprofile.parentsname;

                    greetingTextView.setText("welcome " + fullname);
                    fullnameTextView.setText("Name - " + fullname);
                    emailTextView.setText("email - " + email);
                    phonenumberTextView.setText("phoneNumber- " + phone);
                    usnTextView.setText("usn- " +  usn);
                    parentsname.setText("parents name - "+ pname);



                }
                opreq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeStudents.this,matapitadhundo.class);
                        intent.putExtra("p",pname);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(HomeStudents.this,"something went wrong",Toast.LENGTH_LONG).show();
            }
        });

    }

}

