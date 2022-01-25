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

public class wardenlogin extends AppCompatActivity  implements View.OnClickListener{
    private EditText edTextemail, edTextPassword;
    private TextView edregister,edforgotpassword;
    private Button edsignin;
    private FirebaseAuth mAuth;
    private ProgressBar edprogressBar;
    String checkbox3;
    CheckBox remember3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardenlogin);
        edregister =(TextView) findViewById(R.id.lreg);
        edregister.setOnClickListener(this);

        edforgotpassword = (TextView) findViewById(R.id.lforpass);
        edforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(wardenlogin.this, forgotpassword.class));
            }
        });
        edsignin= (Button) findViewById(R.id.lbutton);
        edsignin.setOnClickListener(this);
        remember3 = findViewById(R.id.remember3);

        edTextemail = (EditText) findViewById(R.id.lemail);
        edTextPassword = (EditText) findViewById(R.id.lpass);
        edprogressBar = (ProgressBar) findViewById(R.id.lprogress);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences3 = getSharedPreferences("checkbox3",MODE_PRIVATE);
        checkbox3 = preferences3.getString("remember3","");

        if(checkbox3.equals("true"))
        {
            startActivity(new Intent(wardenlogin.this,HomeWarden.class));
        }
        else if (checkbox3.equals("false"))
        {
            Toast.makeText(this,"please login",Toast.LENGTH_LONG).show();
        }

        remember3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences3 = getSharedPreferences("checkbox3",MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = preferences3.edit();
                    editor3.putString("remember3","true");
                    editor3.apply();
                    Toast.makeText(wardenlogin.this,"checked",Toast.LENGTH_LONG).show();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences3 = getSharedPreferences("checkbox3",MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = preferences3.edit();
                    editor3.putString("remember3","false");
                    editor3.apply();
                    Toast.makeText(wardenlogin.this,"Unchecked",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.lreg:
                startActivity(new Intent(this,wardenregisterpage.class));
                break;

            case R.id.lbutton:
                userLogin();
                break;

            case R.id.lforpass:
                startActivity(new Intent(this,forgotpassword.class));
                break;
        }

    }

    private void userLogin() {

        String email = edTextemail.getText().toString().trim();
        String password = edTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            edTextemail.setError("emai; required");
            edTextemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edTextemail.setError("please provide valid email");
            edTextemail.requestFocus();
            return;

        }
        if(password.isEmpty())
        {
            edTextPassword.setError("password required");
            edTextPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            edTextPassword.setError("min pass require length should be 6");
            edTextPassword.requestFocus();
            return;
        }
        edprogressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        startActivity(new Intent(wardenlogin.this,HomeWarden.class));

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(wardenlogin.this,"check you mail to confirm it",Toast.LENGTH_LONG).show();
                    }

                }else
                {
                    Toast.makeText(wardenlogin.this,"failed to login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
