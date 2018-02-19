package com.example.paulwinjeba.kshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstpageActivity extends AppCompatActivity {

    private Button btnSignup ;
    private Button btnLogin;
    private Button btnskiphome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        btnSignup = (Button) findViewById(R.id.signupbtn);
        btnLogin = (Button) findViewById(R.id.loginbtn);
        btnskiphome = (Button) findViewById(R.id.skipbtn);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(FirstpageActivity.this,SignupActivity.class);
                startActivity(signup);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(FirstpageActivity.this,LoginActivity.class);
                startActivity(login);
            }
        });
        btnskiphome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skiphome = new Intent(FirstpageActivity.this,HomeActivity.class);
                startActivity(skiphome);
                finish();
            }
        });
    }
}
