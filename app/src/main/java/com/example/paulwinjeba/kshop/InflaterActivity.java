package com.example.paulwinjeba.kshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InflaterActivity extends AppCompatActivity {

    private Button post_login;
    private Button post_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflater);

        post_login = (Button) findViewById(R.id.log_in);
        post_signin = (Button) findViewById(R.id.sign_in);

    }
}
