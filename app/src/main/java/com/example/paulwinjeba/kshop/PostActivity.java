package com.example.paulwinjeba.kshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private Spinner spinner;
    private ImageButton post_img;
    private EditText post_title, post_desc, post_price;
    private Button post_btn;
    private Uri imageUri = null;
    private final int PICK_IMAGE_REQUEST = 7;
    private String spinner_category;

    private ProgressDialog mProgress;

    //FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");

        post_img = (ImageButton) findViewById(R.id.post_img);
        mProgress = new ProgressDialog(this);
        post_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryintent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        post_title = (EditText) findViewById(R.id.post_title);
        post_desc = (EditText) findViewById(R.id.post_description);
        post_price = (EditText) findViewById(R.id.post_price);
        //Spinner element
        spinner = (Spinner) findViewById(R.id.category);
        //Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_category = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Sppinner Drop down elements for spinner
        List<String> categories = new ArrayList<String>();
        categories.add("Electronics");
        categories.add("Clothes");
        categories.add("Books");
        categories.add("Bikes");
        categories.add("Accessories");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        post_btn = (Button) findViewById(R.id.post_btn);

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            post_img.setImageURI(imageUri);
        }
    }

    private void startPosting() {
        try {

            final String title = post_title.getText().toString().trim();
            final String description = post_desc.getText().toString().trim();
            final String price = post_price.getText().toString().trim();
            final String category = spinner_category;

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(category) && imageUri != null) {

                mProgress.show();

                StorageReference filepath = storageReference.child("Post_Images").child(imageUri.getLastPathSegment());

                filepath.putFile(imageUri).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                DatabaseReference newPost = databaseReference.push();

                                newPost.child("Title").setValue(title);
                                newPost.child("Description").setValue(description);
                                newPost.child("Price").setValue(price);
                                newPost.child("Category").setValue(category);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                //newPost.child("uid").setValue(FirebaseAuth.getInstance());

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this,"Successfully Uploaded...",Toast.LENGTH_LONG).show();

                            }
                        });
                filepath.putFile(imageUri).addOnFailureListener((new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Intent homeintent = new Intent(PostActivity.this, HomeActivity.class);
                        homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeintent);
                        Toast.makeText(PostActivity.this,"Inable to Upload Files...",Toast.LENGTH_LONG).show();
                    }
                }) );
            }
        }catch (Exception e){
            Toast.makeText(PostActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e("Error:",e.getMessage());
        }
        finally {

        }//NOT TO GO BACK
    }
    @Override
    public void onBackPressed(){
        //Toast.makeText(PostActivity.this,"Cancelled...Press back again to Exit.",Toast.LENGTH_LONG).show();
    }
}
