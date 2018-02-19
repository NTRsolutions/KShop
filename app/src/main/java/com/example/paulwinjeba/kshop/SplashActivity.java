package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private FirebaseAuth mAuth;
    boolean isOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.splashtxt);
        iv = (ImageView) findViewById(R.id.splashimg);

        mAuth = FirebaseAuth.getInstance();

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        isOnline = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() && connectivityManager.getActiveNetworkInfo().isAvailable());

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this,FirstpageActivity.class);
        final Intent home = new Intent(this,HomeActivity.class);
        Thread timer = new Thread(){
            public void run () {
                try {
                    sleep(5000);
                    if (isOnline) {
                        //connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() && connectivityManager.getActiveNetworkInfo().isAvailable()
                        //internet is available
                        {
                            if (mAuth.getCurrentUser() != null) {
                                // User is logged in
                                startActivity(home);
                            }
                            else
                            {
                                startActivity(i);
                            }
                        }
                    }
                    else
                    if (!isOnline)
                    {
                        // no Internet
                        startActivity(home);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        timer.start();
    }
}
