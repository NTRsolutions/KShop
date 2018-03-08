package com.example.paulwinjeba.kshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogSingleActivity extends AppCompatActivity {

    private String post_key = null;

    private DatabaseReference databaseReference;
    private ImageView show_image;
    private TextView show_title, show_desc_1, show_desc_2, show_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        post_key = getIntent().getExtras().getString("blog_id");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post").child(post_key);

        show_image = (ImageView) findViewById(R.id.show_image);
        show_title = (TextView) findViewById(R.id.show_title);
        show_desc_1 = (TextView) findViewById(R.id.show_description1);
        show_desc_2 = (TextView) findViewById(R.id.show_description2);
        show_price = (TextView) findViewById(R.id.show_price);

        Toast.makeText(BlogSingleActivity.this,"Loaded...",Toast.LENGTH_LONG).show();

        databaseReference.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String show_Title = (String) dataSnapshot.child("Title").getValue();
                String show_Description_1 = (String) dataSnapshot.child("Description_1").getValue();
                String show_Description_2 = (String) dataSnapshot.child("Description_2").getValue();
                String show_Price = (String) dataSnapshot.child("Price").getValue();
                String show_Image = (String) dataSnapshot.child("Image").getValue();

                show_title.setText(show_Title);
                show_desc_1.setText(show_Description_1);
                show_desc_2.setText(show_Description_2);
                show_price.setText(show_Price);

                Picasso.with(BlogSingleActivity.this).load(show_Image).into(show_image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
