package com.example.paulwinjeba.kshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById((R.id.textemail));
        editTextPassword = (EditText) findViewById(R.id.textpassword);
        buttonRegister = (Button) findViewById(R.id.loginbtn);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    public void checkLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password))
        {
            progressbar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressbar.setVisibility(View.GONE);

                        Intent homeintent = new Intent(LoginActivity.this, HomeActivity.class);
                        homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeintent);
                        finish();
                        Toast.makeText(LoginActivity.this,"Successfully Logged it...",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(LoginActivity.this,"Login Error... Enter Valid inputs",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
