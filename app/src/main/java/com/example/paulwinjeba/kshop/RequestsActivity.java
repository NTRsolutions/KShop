package com.example.paulwinjeba.kshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RequestsActivity extends AppCompatActivity {
    private String post_key = null;
    private DatabaseReference databaseReference, mydatabase;
    private TextView show_title, show_desc, show_price,show_category, show_name,show_email,show_phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        Bundle extras = getIntent().getExtras();

        post_key = extras.getString("blog_id");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests").child(post_key);

        show_title = (TextView) findViewById(R.id.show_title);
        show_desc = (TextView) findViewById(R.id.show_description);
        show_price = (TextView) findViewById(R.id.show_price);
        show_category = (TextView) findViewById(R.id.show_category);

        show_name = (TextView) findViewById(R.id.show_name);
        show_phoneno = (TextView) findViewById(R.id.show_phoneno);
        show_email = (TextView) findViewById(R.id.show_email);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String show_Category = (String) dataSnapshot.child("Category").getValue();
                String show_Title = (String) dataSnapshot.child("Title").getValue();
                String show_Description = (String) dataSnapshot.child("Description").getValue();
                String show_Price = (String) dataSnapshot.child("Expected_Price").getValue();
                String show_Uid = (String) dataSnapshot.child("UID").getValue();

                show_title.setText(show_Title);
                show_desc.setText(show_Description);
                show_price.setText(show_Price);
                show_category.setText(show_Category);

                mydatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(show_Uid);
                mydatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String show_Name = (String) dataSnapshot.child("Name").getValue();
                        String show_Email = (String) dataSnapshot.child("Email_ID").getValue();
                        String show_Phone = (String) dataSnapshot.child("Phone_Number").getValue();

                        show_name.setText(show_Name);
                        show_email.setText(show_Email);
                        show_phoneno.setText(show_Phone);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RequestsActivity.this, "Unable to load the requirement ...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
