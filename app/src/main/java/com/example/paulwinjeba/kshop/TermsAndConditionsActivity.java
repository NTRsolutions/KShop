package com.example.paulwinjeba.kshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TermsAndConditionsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Button login,signin;
    FirebaseAuth mAuth;
    int key=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        mAuth = FirebaseAuth.getInstance();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.searches){
            final Intent search = new Intent(TermsAndConditionsActivity.this, SearchActivity.class);
            startActivity(search);

        } else if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, ElectronicsActivity.class);
            startActivity(electronic);

        } else if (id == R.id.clothes) {
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, ClothesActivity.class);
            startActivity(electronic);

        } else if (id == R.id.bike) {
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, BikesActivity.class);
            startActivity(electronic);

        } else if (id == R.id.book) {
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, BooksActivity.class);
            startActivity(electronic);

        } else if (id == R.id.miscellaneous) {
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, MiscellaneousActivity.class);
            startActivity(electronic);

        }else if (id == R.id.donation){
            final Intent electronic = new Intent(TermsAndConditionsActivity.this, DonationActivity.class);
            startActivity(electronic);
        } else if (id == R.id.upload) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(TermsAndConditionsActivity.this, PostActivity.class);
                startActivity(upload);
            } else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(TermsAndConditionsActivity.this);
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
                            Intent nxtlogin = new Intent(TermsAndConditionsActivity.this, PostLoginActivity.class);
                            key = 0;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent nxtsignin = new Intent(TermsAndConditionsActivity.this, PostSigninActivity.class);
                            key = 0;
                            nxtsignin.putExtra("type_key",key);
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
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        } else if (id == R.id.gift){
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(TermsAndConditionsActivity.this, DonateActivity.class);
                startActivity(upload);
            } else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(TermsAndConditionsActivity.this);
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
                            Intent nxtlogin = new Intent(TermsAndConditionsActivity.this, PostLoginActivity.class);
                            key=1;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent nxtsignin = new Intent(TermsAndConditionsActivity.this, PostSigninActivity.class);
                            key=1;
                            nxtsignin.putExtra("type_key",key);
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
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (id == R.id.myprofile) {
            if (mAuth.getCurrentUser() != null) {
                final Intent profile = new Intent(TermsAndConditionsActivity.this, MyProfileActivity.class);
                String UUid = mAuth.getCurrentUser().getUid().toString();
                profile.putExtra("uuid", UUid);
                startActivity(profile);
            } else {
                Toast.makeText(TermsAndConditionsActivity.this,"Log in to check your profile !",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.mypost) {
            if (mAuth.getCurrentUser() != null) {
                final Intent mypost = new Intent(TermsAndConditionsActivity.this, MyPostActivity.class);

                startActivity(mypost);
            } else {
                Toast.makeText(TermsAndConditionsActivity.this,"Log in to check your posts !",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.about){
            final Intent about = new Intent(TermsAndConditionsActivity.this, AboutActivity.class);
            startActivity(about);
        } else if (id == R.id.logout) {

            //End user session
            if(mAuth.getCurrentUser() != null){
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(TermsAndConditionsActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(TermsAndConditionsActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            }
            else
                Toast.makeText(TermsAndConditionsActivity.this,"Log in to Log out !",Toast.LENGTH_LONG).show();
        } else if (id == R.id.requirement){
            Intent requ = new Intent(TermsAndConditionsActivity.this, ViewRequestsActivity.class);
            startActivity(requ);

        } else if (id == R.id.request){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(TermsAndConditionsActivity.this, RequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(TermsAndConditionsActivity.this, "Log in to request a requirement... !", Toast.LENGTH_LONG).show();
        } else if (id == R.id.termsncond){
            Intent tnc = new Intent(TermsAndConditionsActivity.this, TermsAndConditionsActivity.class);
            startActivity(tnc);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
