package com.example.paulwinjeba.kshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MyRequestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Button login,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.searches){
            final Intent search = new Intent(MyRequestActivity.this, SearchActivity.class);
            startActivity(search);

        } else
        if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(MyRequestActivity.this,ElectronicsActivity.class);
            startActivity(electronic);
        } else if (id == R.id.clothes) {
            final Intent upload = new Intent(MyRequestActivity.this,ClothesActivity.class);
            startActivity(upload);

        } else if (id == R.id.bike) {
            final Intent upload = new Intent(MyRequestActivity.this,BikesActivity.class);
            startActivity(upload);

        } else if (id == R.id.book) {
            final Intent upload = new Intent(MyRequestActivity.this,BooksActivity.class);
            startActivity(upload);

        } else if(id == R.id.miscellaneous){
            final Intent electronic = new Intent(MyRequestActivity.this,MiscellaneousActivity.class);
            startActivity(electronic);

        } else if (id == R.id.donation){
            final Intent electronic = new Intent(MyRequestActivity.this, DonationActivity.class);
            startActivity(electronic);
        }else if (id == R.id.myprofile) {
            if (mAuth.getCurrentUser() != null) {
                final Intent profile = new Intent(MyRequestActivity.this, MyProfileActivity.class);
                String UUid = mAuth.getCurrentUser().getUid().toString();
                profile.putExtra("uuid",UUid);
                startActivity(profile);
            }
            else{
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyRequestActivity.this);
                    //Set the button id
                    login = (Button) alertLayout.findViewById(R.id.log_in);
                    signin = (Button) alertLayout.findViewById(R.id.sign_in);
                    alert.setTitle("Login or Signin");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent nxtlogin = new Intent(MyRequestActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyRequestActivity.this, PostSigninActivity.class);
                            startActivity(nxtsignin);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            /*final Intent cancel = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(cancel);*/

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                finally {

                }
            }
        } else if(id == R.id.mypost){
            if (mAuth.getCurrentUser() != null) {
                final Intent upload = new Intent(MyRequestActivity.this, MyPostActivity.class);
                startActivity(upload);
            }
            else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyRequestActivity.this);
                    //Set the button id
                    login = (Button) alertLayout.findViewById(R.id.log_in);
                    signin = (Button) alertLayout.findViewById(R.id.sign_in);
                    alert.setTitle("Login or Signin");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent nxtlogin = new Intent(MyRequestActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyRequestActivity.this, PostSigninActivity.class);
                            startActivity(nxtsignin);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            /*final Intent cancel = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(cancel);*/

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                finally {

                }
            }
        } else if (id == R.id.upload) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(MyRequestActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyRequestActivity.this);
                    //Set the button id
                    login = (Button) alertLayout.findViewById(R.id.log_in);
                    signin = (Button) alertLayout.findViewById(R.id.sign_in);
                    alert.setTitle("Login or Signin");
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent nxtlogin = new Intent(MyRequestActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyRequestActivity.this, PostSigninActivity.class);
                            startActivity(nxtsignin);
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            /*final Intent cancel = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(cancel);*/

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if (id == R.id.logout) {

            if(mAuth.getCurrentUser() != null){
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(MyRequestActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(MyRequestActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            }
            else
                Toast.makeText(MyRequestActivity.this,"Log in to Log out !",Toast.LENGTH_LONG).show();

        }else if (id == R.id.about){
            Intent about = new Intent(MyRequestActivity.this, AboutActivity.class);
            startActivity(about);
        }else if (id == R.id.requirement){
            Intent requ = new Intent(MyRequestActivity.this, ViewRequestsActivity.class);
            startActivity(requ);

        } else if (id == R.id.request){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(MyRequestActivity.this, RequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(MyRequestActivity.this, "Log in to request a requirement... !", Toast.LENGTH_LONG).show();
        } else if (id == R.id.termsncond){
            Intent tnc = new Intent(MyRequestActivity.this, TermsAndConditionsActivity.class);
            startActivity(tnc);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
