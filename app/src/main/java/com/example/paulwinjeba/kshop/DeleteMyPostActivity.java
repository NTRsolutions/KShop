package com.example.paulwinjeba.kshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DeleteMyPostActivity extends AppCompatActivity {

    String post_key= null,myUuid;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference,postdb;
    private ProgressBar progressBar;

    private ImageView post_image;
    private TextView post_title, show_description1, show_description2, post_price,show_category;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_my_post);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        Bundle extras = getIntent().getExtras();

        post_key = extras.getString("blog_id");

        post_image = (ImageView) findViewById(R.id.post_image);
        post_price = (TextView) findViewById(R.id.post_price);
        post_title = (TextView) findViewById(R.id.post_title);
        show_category = (TextView) findViewById(R.id.show_category);
        show_description1 = (TextView) findViewById(R.id.show_description1);
        show_description2 = (TextView) findViewById(R.id.show_description2);
        delete = (Button) findViewById(R.id.delete);

        mAuth = FirebaseAuth.getInstance();
        myUuid = mAuth.getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(myUuid).child("My Post").child(post_key);
        postdb = FirebaseDatabase.getInstance().getReference().child("Post").child(post_key);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show_Category = (String) dataSnapshot.child("Category").getValue();
                String show_Title = (String) dataSnapshot.child("Title").getValue();
                String show_Description_1 = (String) dataSnapshot.child("Description_1").getValue();
                String show_Description_2 = (String) dataSnapshot.child("Description_2").getValue();
                String show_Price = (String) dataSnapshot.child("Price").getValue();
                String show_Image = (String) dataSnapshot.child("Image").getValue();

                post_title.setText(show_Title);
                show_description1.setText(show_Description_1);
                show_description2.setText(show_Description_2);
                post_price.setText(show_Price);
                show_category.setText(show_Category);

                Picasso.with(DeleteMyPostActivity.this).load(show_Image).into(post_image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteMyPostActivity.this, "Unable to load the product ...", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                databaseReference.child(post_key).removeValue();
                postdb.child(post_key).removeValue();
                Intent homeintent = new Intent(DeleteMyPostActivity.this, HomeActivity.class);
                startActivity(homeintent);
            }
        });
    }
}
