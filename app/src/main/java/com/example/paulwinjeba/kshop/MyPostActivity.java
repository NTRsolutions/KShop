package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.common.hash.HashingOutputStream;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyPostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView MyPostList;
    private int key=0;
    String post_key= null,myUuid;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference,postdb,donatedb;
    private ProgressBar progressBar;

    Button login,signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        mAuth = FirebaseAuth.getInstance();
        myUuid = mAuth.getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(myUuid).child("My Post");

        //String post_key = getIntent().getExtras().getString("blog_id");

        /*edit = (Button) findViewById(R.id.edit);*/

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        MyPostList = (RecyclerView) findViewById(R.id.my_posts);
        MyPostList.setHasFixedSize(true);
        MyPostList.setLayoutManager(new LinearLayoutManager(this));

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
        FirebaseRecyclerAdapter<MyBlog, MyBlogViewHolder> my_firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyBlog, MyBlogViewHolder>(
                MyBlog.class,
                R.layout.my_posts,
                MyBlogViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(MyPostActivity.MyBlogViewHolder viewHolder, MyBlog model, int position) {

                post_key = getRef(position).getKey().toString();
                postdb = FirebaseDatabase.getInstance().getReference().child("Post").child(post_key);
                donatedb = FirebaseDatabase.getInstance().getReference().child("Donation").child(post_key);

                viewHolder.setTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setDescription_1(model.getDescription_1());
                viewHolder.setDescription_2(model.getDescription_2());

                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        databaseReference.removeValue();
                        postdb.removeValue();

                        Toast.makeText(MyPostActivity.this, "Product Deleted ...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Intent homeintent = new Intent(MyPostActivity.this, HomeActivity.class);
                        startActivity(homeintent);
                    }
                });

                /*viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleBlogIntent = new Intent(MyPostActivity.this,DeleteMyPostActivity.class);
                        singleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(singleBlogIntent);
                    }
                });*/
            }
        };

        MyPostList.setAdapter(my_firebaseRecyclerAdapter);
    }


    public static class MyBlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button delete;

        public MyBlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            delete = (Button) mView.findViewById(R.id.delete);
        }

        public void setTitle(String Title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(Title);
        }

        public void setPrice(String Price){
            TextView post_price = (TextView) mView.findViewById(R.id.post_price);
            post_price.setText(Price);
        }

        public void setImage(Context ctx, String Image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx.getApplicationContext()).load(Image).into(post_image);
        }

        public void setDescription_1(String Description_1){
            TextView description_1 = (TextView) mView.findViewById(R.id.show_description1);
            description_1.setText(Description_1);
        }

        public void setDescription_2(String Description_2){
            TextView description_2 = (TextView) mView.findViewById(R.id.show_description2);
            description_2.setText(Description_2);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //finish();
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

        /*if(id == R.id.searches){
            final Intent search = new Intent(MyPostActivity.this, SearchActivity.class);
            startActivity(search);

        } else */if (id == R.id.electronics) {
            // Handle the electronics action
            final Intent electronic = new Intent(MyPostActivity.this,ElectronicsActivity.class);
            startActivity(electronic);
        } else if (id == R.id.clothes) {
            final Intent upload = new Intent(MyPostActivity.this,ClothesActivity.class);
            startActivity(upload);

        } else if (id == R.id.bike) {
            final Intent upload = new Intent(MyPostActivity.this,BikesActivity.class);
            startActivity(upload);

        } else if (id == R.id.book) {
            final Intent upload = new Intent(MyPostActivity.this,BooksActivity.class);
            startActivity(upload);

        } else if (id == R.id.miscellaneous) {
            final Intent upload = new Intent(MyPostActivity.this,MiscellaneousActivity.class);
            startActivity(upload);

        }else if (id == R.id.donation){
            final Intent electronic = new Intent(MyPostActivity.this, DonationActivity.class);
            startActivity(electronic);
        } else if (id == R.id.myprofile) {
            final Intent upload = new Intent(MyPostActivity.this,MyProfileActivity.class);
            startActivity(upload);

        } else if(id == R.id.mypost){
            final Intent upload = new Intent(MyPostActivity.this,MyPostActivity.class);
            startActivity(upload);

        }else if (id == R.id.upload) {
            if (mAuth.getCurrentUser().getUid() != null) {
                // User is logged in
                final Intent upload = new Intent(MyPostActivity.this,PostActivity.class);
                startActivity(upload);
            }
            else
            {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyPostActivity.this);
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
                            Intent nxtlogin = new Intent(MyPostActivity.this, PostLoginActivity.class);
                            key = 0;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent nxtsignin = new Intent(MyPostActivity.this, PostSigninActivity.class);
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
                final Intent upload = new Intent(MyPostActivity.this, DonateActivity.class);
                startActivity(upload);
            } else {
                try {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout;
                    alertLayout = inflater.inflate(R.layout.activity_inflater, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyPostActivity.this);
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
                            Intent nxtlogin = new Intent(MyPostActivity.this, PostLoginActivity.class);
                            key=1;
                            nxtlogin.putExtra("type_key",key);
                            startActivity(nxtlogin);
                        }
                    });

                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Log.d("sign in checking", "tested");
                            final Intent nxtsignin = new Intent(MyPostActivity.this, PostSigninActivity.class);
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
        } else if (id == R.id.about){
            final Intent about = new Intent(MyPostActivity.this, AboutActivity.class);
            startActivity(about);
        } else if (id == R.id.logout) {

            //End user session
            if(mAuth.getCurrentUser() != null){
                //End users session
                FirebaseAuth.getInstance().signOut();
                Intent homeagain = new Intent(MyPostActivity.this, FirstpageActivity.class);
                homeagain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(MyPostActivity.this,"Logged Out Successfully",Toast.LENGTH_LONG).show();
                startActivity(homeagain);
            }
            else
                Toast.makeText(MyPostActivity.this,"Log in to Log out !",Toast.LENGTH_LONG).show();

        }else if (id == R.id.requirement){
            Intent requ = new Intent(MyPostActivity.this, ViewRequestsActivity.class);
            startActivity(requ);

        } else if (id == R.id.request){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(MyPostActivity.this, RequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(MyPostActivity.this, "Log in to request a requirement... !", Toast.LENGTH_LONG).show();
        } else if (id == R.id.termsncond){
            Intent tnc = new Intent(MyPostActivity.this, TermsAndConditionsActivity.class);
            startActivity(tnc);
        }  else if (id == R.id.myreq){
            if (mAuth.getCurrentUser() != null) {
                Intent request = new Intent(MyPostActivity.this, MyRequestActivity.class);
                startActivity(request);
            }
            else
                Toast.makeText(MyPostActivity.this, "Log in to view your request... !", Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
