package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class wardenregisterpage extends AppCompatActivity implements View.OnClickListener {
    private EditText rTextname, rTextemail, rTextpassword, rTextphonenumber, rTextsharing, rTexthostelname;
    private TextView rregister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardenregisterpage);

        mAuth = FirebaseAuth.getInstance();
        rregister = (Button) findViewById(R.id.registeer);
        rregister.setOnClickListener(this);
        rTextname = (EditText) findViewById(R.id.namee);
        rTextemail = (EditText) findViewById(R.id.Emaill);

        rTextsharing = (EditText) findViewById(R.id.sharring);
        rTexthostelname = (EditText) findViewById(R.id.hostelnamee);


    }

    @Override
    public void onClick(View view) {


        switch(view.getId()){
        case R.id.registeer:
            RRegisteruser();
    }
}

    private void RRegisteruser() {
        String eemail = rTextemail.getText().toString();
        String ssharing= rTextsharing.getText().toString();
        String nname = rTextname.getText().toString();
        String pphone = rTextphonenumber.getText().toString();

        String ppassword = rTextpassword.getText().toString();
        String hhostelname = rTexthostelname.getText().toString();

        if(nname.isEmpty()) {
            rTextname.setError("full name required");
            rTextname.requestFocus();
            return;
        }
            if(ppassword.isEmpty())
            {
                rTextpassword.setError("password required");
                rTextpassword.requestFocus();
                return;
            }

        if(pphone.isEmpty())
        {
            rTextphonenumber.setError("phonenumber required");
            rTextphonenumber.requestFocus();
            return;
        }
        if(eemail.isEmpty())
        {
            rTextemail.setError("email; required");
            rTextemail.requestFocus();
            return;
        }
        if(ssharing.isEmpty())
        {
            rTextsharing.setError("Sharing is  required");
            rTextsharing.requestFocus();
            return;
        }
        if(hhostelname.isEmpty())
        {
            rTexthostelname.setError("hostelname is required");
            rTexthostelname.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(eemail).matches()) {
            rTextemail.setError("please provide valid email");
            rTextemail.requestFocus();
            return;

        }
        if(ppassword.length()<6)
        {
            rTextpassword.setError("min pass require length should be 6");
            rTextpassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(eemail,ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user2 user2 = new user2(nname, eemail, pphone,ssharing,hhostelname);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(wardenregisterpage.this, "registered", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(wardenregisterpage.this,login.class));
                            } else {
                                Toast.makeText(wardenregisterpage.this, "registration failed", Toast.LENGTH_LONG).show();

                            }
                        }
                    });


                } else {
                    Toast.makeText(wardenregisterpage.this, "registration failed due", Toast.LENGTH_LONG).show();
                }
            }

        });}}










