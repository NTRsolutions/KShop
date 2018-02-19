package com.example.paulwinjeba.kshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PostLoginActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        editTextEmail = (EditText) findViewById((R.id.textemail));
        editTextPassword = (EditText) findViewById(R.id.textpassword);
        buttonRegister = (Button) findViewById(R.id.loginbtn);
        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        //finish();
    }

    private void checkLogin()
    {
        try
        {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)) {
                mProgress.setMessage("Logging In...");
                mProgress.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //mProgress.dismiss();

                            Intent postintent = new Intent(PostLoginActivity.this, PostActivity.class);
                            postintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(postintent);
                            Toast.makeText(PostLoginActivity.this, "Successfully Logged it...Ready to Post...", Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(PostLoginActivity.this, "Login Error... Enter Valid inputs", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        finally{
            finish();
        }
    }
}
