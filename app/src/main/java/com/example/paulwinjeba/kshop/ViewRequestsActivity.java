package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewRequestsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private int key=0;
    private Button login,signin;
    private DatabaseReference databaseReference;
    private RecyclerView request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");

        request = (RecyclerView) findViewById(R.id.request);

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        linearlayout.setReverseLayout(true);
        linearlayout.setStackFromEnd(true);

        request.setHasFixedSize(true);
        request.setLayoutManager(linearlayout);
    }

    @Override
    protected void onStart(){
        super.onStart();
        final FirebaseRecyclerAdapter<Request, RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Request, RequestViewHolder>(
                Request.class,
                R.layout.viewrequests,
                RequestViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, Request model, int position) {

                final String req_key = getRef(position).getKey().toString();
//                final String post_key_uid = databaseReference.child(post_key).child("UID").getKey().toString();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setCategory(model.getCategory());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleBlogIntent = new Intent(ViewRequestsActivity.this,RequestsActivity.class);
                        singleBlogIntent.putExtra("blog_id",req_key);
                        startActivity(singleBlogIntent);
                    }
                });
            }
        };

        request.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public RequestViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String Title){
            TextView title = (TextView) mView.findViewById(R.id.requesttitle);
            title.setText(Title);
        }

        public void setCategory(String Category){
            TextView category = (TextView) mView.findViewById(R.id.requestcategory);
            category.setText(Category);
        }
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
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if(id == R.id.searches){
            final Intent search = new Intent(ViewRequestsActivity.this, SearchActivity.class);
            startActivity(search);

        } else*/
        if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(ViewRequestsActivity.this,ElectronicsActivity.class);
            startActivity(electronic);

        } else if (id == R.id.clothes) {
            final Intent electronic = new Intent(ViewRequestsActivity.this,ClothesActivity.class);
            startActivity(electronic);

        } else if (id == R.id.bike) {
            final Intent electronic = new Intent(ViewRequestsActivity.this,BikesActivity.class);
            startActivity(electronic);

        } else if (id == R.id.book) {
            final Intent electronic = new Intent(ViewRequestsActivity.this,BooksActivity.class);
            startActivity(electronic);

        } else if(id == R.id.miscellaneous){
            final Intent electronic = new Intent(ViewRequestsActivity.this,MiscellaneousActivity.class);
            startActivity(electronic);

        } else if (id == R.id.donation){
            final Intent electronic = new Intent(ViewRequestsActivity.this, DonationActivity.class);
            startActivity(electronic);
        }else if (id == R.id.myprofile) {
            final Intent upload = new Intent(ViewRequestsActivity.this,MyProfileActivity.class);
            startActivity(upload);

        } else if(id == R.id.mypost){
            final Intent upload = new Intent(ViewRequestsActivity.this,MyPostActivity.class);
            startActivity(upload);

        } else if (id == R.id.upload) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(ViewRequestsActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewRequestsActivity.this);
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
                            Intent nxtlogin = new Intent(ViewRequestsActivity.this, PostLoginActivity.class);
                            key = 0;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent nxtsignin = new Intent(ViewRequestsActivity.this, PostSigninActivity.class);
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
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        } else if (id == R.id.gift){
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(ViewRequestsActivity.this, DonateActivity.class);
                startActivity(upload);
            } else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewRequestsActivity.this);
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
                            Intent nxtlogin = new Intent(ViewRequestsActivity.this, PostLoginActivity.class);
                            key=1;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(ViewRequestsActivity.this, PostSigninActivity.class);
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
        }else if (id == R.id.logout) {

            //End user session
            if(mAuth.getCurrentUser() != null){
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(ViewRequestsActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(ViewRequestsActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            }
            else
                Toast.makeText(ViewRequestsActivity.this,"Log in to Log out !",Toast.LENGTH_LONG).show();
        }else if (id == R.id.about){
            final Intent about = new Intent(ViewRequestsActivity.this, AboutActivity.class);
            startActivity(about);
        }else if (id == R.id.requirement){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            /*Intent requ = new Intent(AboutActivity.this, ViewRequestsActivity.class);
            startActivity(requ);*/

        } else if (id == R.id.request){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(ViewRequestsActivity.this, RequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(ViewRequestsActivity.this, "Log in to request a requirement... !", Toast.LENGTH_LONG).show();
        } else if (id == R.id.termsncond){
            Intent tnc = new Intent(ViewRequestsActivity.this, TermsAndConditionsActivity.class);
            startActivity(tnc);
        }  else if (id == R.id.myreq){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(ViewRequestsActivity.this, MyRequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(ViewRequestsActivity.this, "Log in to view your request... !", Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
