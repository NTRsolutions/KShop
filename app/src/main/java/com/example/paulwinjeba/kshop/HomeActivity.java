package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView PostList;

    private boolean isOnline,mProcessStar = false;
    private FirebaseAuth mAuth;
    private String mAuthuser,post_key;
    private DatabaseReference databaseReference,myDatabaseRef,likedatabase;
    private Button login,signin;
    private int key=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");
        mAuth = FirebaseAuth.getInstance();
        mAuthuser = mAuth.getCurrentUser().getUid(); // get the current user
        likedatabase = FirebaseDatabase.getInstance().getReference().child("Likes");
        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuthuser).child("My Likes");

        //String display = String.valueOf(mAuth.getCurrentUser());

        PostList = (RecyclerView) findViewById(R.id.post_list);

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        linearlayout.setReverseLayout(true);
        linearlayout.setStackFromEnd(true);

        PostList.setHasFixedSize(true);
        PostList.setLayoutManager(linearlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        isOnline = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected() && connectivityManager.getActiveNetworkInfo().isAvailable());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            if (!isOnline)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("No Internet");
                alertDialogBuilder.setMessage("Please check your Internet Connection and try again.");
                alertDialogBuilder.setPositiveButton("Reload",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(HomeActivity.this,"Reconnecting",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        final FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.post_row,
                BlogViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder, Blog model, final int position) {

                post_key = getRef(position).getKey().toString();
//                final String post_key_uid = databaseReference.child(post_key).child("UID").getKey().toString();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.setLikeBtn(post_key);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Intent singleBlogIntent = new Intent(HomeActivity.this,BlogSingleActivity.class);
                        singleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(singleBlogIntent);
                    }
                });

                viewHolder.star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mProcessStar = true;
                        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuthuser).child("My Likes");
                        myDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (mProcessStar) {
                                    if (dataSnapshot.hasChild(post_key)) {
                                        Log.i("D Diary", "User has already Liked. So it can be considered as Unliked.");
                                        myDatabaseRef.child(post_key).removeValue();
                                        //viewHolder.star.setImageResource(R.drawable.star_hollow);
                                        mProcessStar = false;
                                    } else {
                                        Log.i("D Diary", "User has Liked");
                                        myDatabaseRef.child(post_key).setValue("Post");
                                        //viewHolder.star.setImageResource(R.drawable.star_purple);
                                        mProcessStar = false;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };

        PostList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton star;
        DatabaseReference myDatabaseRef;
        private FirebaseAuth mAuth;
        private String mAuthuser;

        public BlogViewHolder(View itemView) {
                super(itemView);
                mView = itemView;

                star = (ImageButton) mView.findViewById(R.id.star_h);

                mAuth = FirebaseAuth.getInstance();
                mAuthuser = mAuth.getCurrentUser().getUid();

                myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuthuser).child("My Likes");
                myDatabaseRef.keepSynced(true);

        }

        public void setLikeBtn(final String post_key){
            myDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myDatabaseRef.keepSynced(true);
                    if(dataSnapshot.hasChild(post_key)){
                        star.setImageResource(R.drawable.star_purple);
                    } else {
                        star.setImageResource(R.drawable.star_hollow);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            myDatabaseRef.keepSynced(true);
        }

        public void setTitle(String Title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(Title);
        }

        public void setPrice(String Price){
            TextView post_price = (TextView) mView.findViewById(R.id.post_price);
            post_price.setText(Price);
        }

        public void setImage(Context ctx,String Image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx.getApplicationContext()).load(Image).into(post_image);
            //Glide.with(HomeActivity.this).load(Image).dontAnimate().into(post_image);
            //Picasso.with(ctx.getApplicationContext()).load(Image).into(post_image);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.logout) {

            if (mAuth.getCurrentUser() != null) {
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(HomeActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(HomeActivity.this, "Logged Out Successfully", Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            } else
                Toast.makeText(HomeActivity.this, "Log in to Log out !", Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.signin){
            if (mAuth.getCurrentUser() != null) {
                //End users session
                Toast.makeText(HomeActivity.this, "Log Out to signin...", Toast.LENGTH_LONG).show();
            } else{
                Intent homeagain = new Intent(HomeActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeagain);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if(id == R.id.searches){
            final Intent search = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(search);

        } else*/
        if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(HomeActivity.this,ElectronicsActivity.class);
            startActivity(electronic);
        } else if (id == R.id.clothes) {
            final Intent upload = new Intent(HomeActivity.this,ClothesActivity.class);
            startActivity(upload);

        } else if (id == R.id.bike) {
            final Intent upload = new Intent(HomeActivity.this,BikesActivity.class);
            startActivity(upload);

        } else if (id == R.id.book) {
            final Intent upload = new Intent(HomeActivity.this,BooksActivity.class);
            startActivity(upload);

        } else if(id == R.id.miscellaneous){
            final Intent electronic = new Intent(HomeActivity.this,MiscellaneousActivity.class);
            startActivity(electronic);

        } else if (id == R.id.donation){
            final Intent electronic = new Intent(HomeActivity.this, DonationActivity.class);
            startActivity(electronic);
        }else if (id == R.id.myprofile) {
            if (mAuth.getCurrentUser() != null) {
                final Intent profile = new Intent(HomeActivity.this, MyProfileActivity.class);
                String UUid = mAuth.getCurrentUser().getUid().toString();
                profile.putExtra("uuid",UUid);
                startActivity(profile);
            }
            else{
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
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
                            Intent nxtlogin = new Intent(HomeActivity.this, PostLoginActivity.class);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent nxtsignin = new Intent(HomeActivity.this, PostSigninActivity.class);
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
                final Intent upload = new Intent(HomeActivity.this, MyPostActivity.class);
                startActivity(upload);
            }
            else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
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
                            Intent nxtlogin = new Intent(HomeActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(HomeActivity.this, PostSigninActivity.class);
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
                final Intent upload = new Intent(HomeActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
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
                            Intent nxtlogin = new Intent(HomeActivity.this, PostLoginActivity.class);
                            Log.d("login", "testing");
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(HomeActivity.this, PostSigninActivity.class);
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
        } else if (id == R.id.gift) {
            if (mAuth.getCurrentUser() != null) {
                // User is logged in
                final Intent upload = new Intent(HomeActivity.this, DonateActivity.class);
                startActivity(upload);
            } else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
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
                            Intent nxtlogin = new Intent(HomeActivity.this, PostLoginActivity.class);
                            key=1;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(HomeActivity.this, PostSigninActivity.class);
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
        }   else if (id == R.id.logout) {

            if(mAuth.getCurrentUser() != null){
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(HomeActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(HomeActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            }
            else
                Toast.makeText(HomeActivity.this,"Log in to Log out !",Toast.LENGTH_LONG).show();

        }else if (id == R.id.about){
            Intent about = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(about);
        }else if (id == R.id.requirement){
            Intent requ = new Intent(HomeActivity.this, ViewRequestsActivity.class);
            startActivity(requ);

        } else if (id == R.id.request){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(HomeActivity.this, RequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(HomeActivity.this, "Log in to request a requirement... !", Toast.LENGTH_LONG).show();
        } else if (id == R.id.termsncond){
            Intent tnc = new Intent(HomeActivity.this, TermsAndConditionsActivity.class);
            startActivity(tnc);
        } else if (id == R.id.myreq){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(HomeActivity.this, MyRequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(HomeActivity.this, "Log in to view your request... !", Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
