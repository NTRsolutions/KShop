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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView myProfile;

    FirebaseAuth mAuth;
    String myUuid=" ";
    private DatabaseReference databaseReference;

    Button login,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
        myUuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(myUuid);

        myProfile = (RecyclerView) findViewById(R.id.my_profiles);
        myProfile.setHasFixedSize(true);
        myProfile.setLayoutManager(new LinearLayoutManager(this));

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
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<MyProfile, MyProfileActivity.MyProfileViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyProfile, MyProfileActivity.MyProfileViewHolder>(
                MyProfile.class,
                R.layout.my_profiles,
                MyProfileViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(MyProfileActivity.MyProfileViewHolder viewHolder, MyProfile model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setEmail_ID(model.getEmail_ID());
                viewHolder.setPhone_Number(model.getPhone_Number());
                viewHolder.setAddress(model.getAddress());
            }
        };

        myProfile.setAdapter(firebaseRecyclerAdapter);

    }

    public static class MyProfileViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MyProfileViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String Name){
            TextView text_name = (TextView) mView.findViewById(R.id.text_name);
            text_name.setText(Name);
        }

        public void setEmail_ID(String Email_ID){
            TextView text_email = (TextView) mView.findViewById(R.id.text_email);
            text_email.setText(Email_ID);
        }

        public void setPhone_Number(String Phone_Number){
            TextView text_phone = (TextView) mView.findViewById(R.id.text_phone);
            text_phone.setText(Phone_Number);
        }

        public void setAddress(String Address){
            TextView text_addr = (TextView) mView.findViewById(R.id.text_addr);
            text_addr.setText(Address);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            finish();
        }
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
            final Intent search = new Intent(MyProfileActivity.this, SearchActivity.class);
            startActivity(search);

        } else if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(MyProfileActivity.this,ElectronicsActivity.class);
            startActivity(electronic);
        } else if (id == R.id.clothes) {
            final Intent upload = new Intent(MyProfileActivity.this,ClothesActivity.class);
            startActivity(upload);

        } else if (id == R.id.bike) {
            final Intent upload = new Intent(MyProfileActivity.this,BikesActivity.class);
            startActivity(upload);

        } else if (id == R.id.book) {
            final Intent upload = new Intent(MyProfileActivity.this,BooksActivity.class);
            startActivity(upload);

        } else if (id == R.id.myprofile) {
            final Intent upload = new Intent(MyProfileActivity.this,PostActivity.class);
            startActivity(upload);

        } else if (id == R.id.upload) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(MyProfileActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyProfileActivity.this);
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
                            Intent nxtlogin = new Intent(MyProfileActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyProfileActivity.this, PostSigninActivity.class);
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

            //End user session
            FirebaseAuth.getInstance().signOut();
            Intent homeagain = new Intent(MyProfileActivity.this, FirstpageActivity.class);
            homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(MyProfileActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
            startActivity(homeagain);

        }else if (id == R.id.sett) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
