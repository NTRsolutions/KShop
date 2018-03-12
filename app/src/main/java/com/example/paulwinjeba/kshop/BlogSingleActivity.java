package com.example.paulwinjeba.kshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogSingleActivity extends AppCompatActivity {

    private String post_key = null;

    private DatabaseReference databaseReference, mydatabase;
    private ImageView show_image;
    private TextView show_title, show_desc_1, show_desc_2, show_price,show_category, show_name,show_email,show_addr,show_phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        Toast.makeText(BlogSingleActivity.this,"Loading...",Toast.LENGTH_LONG).show();
        Bundle extras = getIntent().getExtras();

        post_key = extras.getString("blog_id");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post").child(post_key);

        show_image = (ImageView) findViewById(R.id.show_image);

        show_title = (TextView) findViewById(R.id.show_title);
        show_desc_1 = (TextView) findViewById(R.id.show_description1);
        show_desc_2 = (TextView) findViewById(R.id.show_description2);
        show_price = (TextView) findViewById(R.id.show_price);
        show_category = (TextView) findViewById(R.id.show_category);

        show_name = (TextView) findViewById(R.id.show_name);
        show_addr = (TextView) findViewById(R.id.show_addr);
        show_phoneno = (TextView) findViewById(R.id.show_phoneno);
        show_email = (TextView) findViewById(R.id.show_email);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show_Category = (String) dataSnapshot.child("Category").getValue();
                String show_Title = (String) dataSnapshot.child("Title").getValue();
                String show_Description_1 = (String) dataSnapshot.child("Description_1").getValue();
                String show_Description_2 = (String) dataSnapshot.child("Description_2").getValue();
                String show_Price = (String) dataSnapshot.child("Price").getValue();
                String show_Image = (String) dataSnapshot.child("Image").getValue();
                String show_Uid = (String) dataSnapshot.child("UID").getValue();

                show_title.setText(show_Title);
                show_desc_1.setText(show_Description_1);
                show_desc_2.setText(show_Description_2);
                show_price.setText(show_Price);
                show_category.setText(show_Category);

                Picasso.with(BlogSingleActivity.this).load(show_Image).into(show_image);

                mydatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(show_Uid);
                mydatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String show_Name = (String) dataSnapshot.child("Name").getValue();
                        String show_Email = (String) dataSnapshot.child("Email_ID").getValue();
                        String show_Phone = (String) dataSnapshot.child("Phone_Number").getValue();
                        String show_Addr = (String) dataSnapshot.child("Address").getValue();

                        show_name.setText(show_Name);
                        show_email.setText(show_Email);
                        show_phoneno.setText(show_Phone);
                        show_addr.setText(show_Addr);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BlogSingleActivity.this, "Unable to load the product ...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
