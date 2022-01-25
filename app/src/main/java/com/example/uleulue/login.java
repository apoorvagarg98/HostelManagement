package com.example.uleulue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextemail, editTextPassword;
    private TextView  register,editTextForgotpassword;

    private Button  signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    String checkbox;


    CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = (TextView) findViewById(R.id.registerstudent);
        register.setOnClickListener(this);

        editTextForgotpassword = (TextView) findViewById(R.id.forgotPassword2student);
        editTextForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, forgotpassword.class));
            }
        });
        signin = (Button) findViewById(R.id.btnLogin2student);
        signin.setOnClickListener(this);


        editTextemail = (EditText) findViewById(R.id.inputEmail2student);
        editTextPassword = (EditText) findViewById(R.id.inputPassword2student);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2student);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        checkbox = preferences.getString("remember","");

        if(checkbox.equals("true"))
        {
            startActivity(new Intent(login.this,HomeStudents.class));
        }
        else if (checkbox.equals("false"))
        {
            Toast.makeText(this,"please login",Toast.LENGTH_LONG).show();
        }

        remember = findViewById(R.id.remember2student);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(login.this,"checked",Toast.LENGTH_LONG).show();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(login.this,"Unchecked",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerstudent:
                startActivity(new Intent(this,studentreg.class));

                break;

            case R.id.btnLogin2student:
                userLogin();
                break;

            case R.id.forgotPassword2student:
                startActivity(new Intent(this,forgotpassword.class));
                break;

        }

    }

    private void userLogin() {

        String email = editTextemail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextemail.setError("email; required");
            editTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("please provide valid email");
            editTextemail.requestFocus();
            return;

        }
        if(password.isEmpty())
        {
            editTextPassword.setError("password required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPassword.setError("min pass require length should be 6");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        Intent intent = new Intent(login.this,HomeStudents.class);

                        startActivity(intent);
                        finish();
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(login.this,"check you mail to confirm it",Toast.LENGTH_LONG).show();
                    }

                }else
                {
                    Toast.makeText(login.this,"failed to login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}