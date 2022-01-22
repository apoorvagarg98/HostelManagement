package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class GatekeeperLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText editemail, editPassword;
    private TextView ttregister, forgotpassword;
    private Button signinn;
    private FirebaseAuth mAuth;
    private ProgressBar progresssBar;
    String checkbox5;


    CheckBox remember5,c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gatekeeper_login);
        remember5 = findViewById(R.id.r4);
        ttregister = (TextView) findViewById(R.id.cregister2);
        ttregister.setOnClickListener(this);
        forgotpassword = (TextView) findViewById(R.id.forgotPassword2);
        signinn = (Button) findViewById(R.id.btnLogin2);
        signinn.setOnClickListener(this);
        c2 = findViewById(R.id.checkBoxg);
        editemail = (EditText) findViewById(R.id.Cemail);
        editPassword = (EditText) findViewById(R.id.cpassword2);
        progresssBar = (ProgressBar) findViewById(R.id.cprogressBar3);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences5 = getSharedPreferences("checkbox5",MODE_PRIVATE);
        checkbox5 = preferences5.getString("remember5","");

        if(checkbox5.equals("true"))
        {
            startActivity(new Intent(GatekeeperLogin.this,GatekeeperHome.class));
        }
        else if (checkbox5.equals("false"))
        {
            Toast.makeText(this,"please login",Toast.LENGTH_LONG).show();
        }
        remember5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences5 = getSharedPreferences("checkbox5",MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = preferences5.edit();
                    editor5.putString("remember5","true");
                    editor5.apply();
                    Toast.makeText(GatekeeperLogin.this,"checked",Toast.LENGTH_LONG).show();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences5 = getSharedPreferences("checkbox5",MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = preferences5.edit();
                    editor5.putString("remember","false");
                    editor5.apply();
                    Toast.makeText(GatekeeperLogin.this,"Unchecked",Toast.LENGTH_LONG).show();
                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editPassword.setTransformationMethod(null);
                } else {
                    editPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GatekeeperLogin.this,forgotpassword.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cregister2:
                startActivity(new Intent(this, GatekeeperRegister.class));
                break;

            case R.id.btnLogin2:
                userLoginn();
                break;


        }

    }

    private void userLoginn() {

        String email = editemail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editemail.setError("email required");
            editemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("please provide valid email");
            editemail.requestFocus();
            return;

        }
        if (password.isEmpty()) {
            editPassword.setError("password required");
            editPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editPassword.setError("min pass require length should be 6");
            editPassword.requestFocus();
            return;
        }
        progresssBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(GatekeeperLogin.this, GatekeeperHome
                                .class));
                        finish();
                    } else
                    { user.sendEmailVerification();
                    Toast.makeText(GatekeeperLogin.this, "check you mail to confirm it", Toast.LENGTH_LONG).show();}
                } else {
                    Toast.makeText(GatekeeperLogin.this, "failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }}

 