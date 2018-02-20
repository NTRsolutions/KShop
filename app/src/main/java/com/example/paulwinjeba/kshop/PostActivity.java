package com.example.paulwinjeba.kshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
    boolean doubleBackToExitPressedOnce = false;

    TextView mstlabel;
    TextInputLayout companyname,specific,specific_cloth,modelname,gear_deatils,noofgear,brakes_details,details_rims,bookname,authorname,bookdesc,desc;
    EditText company_name,device_specification,specific_clothes,model_name,gear,noof_gear,brakes,rims,book_name,author_name,book_desc,description;
    LinearLayout case_2,case_3,case3_1;
    private ImageButton post_img;
    private EditText post_title, post_price;
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

        mstlabel = (TextView) findViewById(R.id.must_label);

        company_name = (EditText) findViewById(R.id.company_name);
        device_specification = (EditText) findViewById(R.id.specification);
        specific_clothes = (EditText) findViewById(R.id.specific_clothes);
        model_name = (EditText) findViewById(R.id.model_name);
        gear = (EditText) findViewById(R.id.gear);
        noof_gear = (EditText) findViewById(R.id.no_of_gear);
        brakes = (EditText) findViewById(R.id.brake);
        rims = (EditText) findViewById(R.id.rims);
        book_name = (EditText) findViewById(R.id.book_name);
        author_name = (EditText) findViewById(R.id.auther_name);
        book_desc = (EditText) findViewById(R.id.book_desc);
        description = (EditText) findViewById(R.id.description);


        companyname = (TextInputLayout) findViewById(R.id.companyname) ;
        specific = (TextInputLayout) findViewById(R.id.specific);
        specific_cloth = (TextInputLayout) findViewById(R.id.specific_cloth);
        modelname = (TextInputLayout) findViewById(R.id.modelname);
        gear_deatils = (TextInputLayout) findViewById(R.id.gear_details);
        noofgear = (TextInputLayout) findViewById(R.id.noofgear);
        brakes_details = (TextInputLayout) findViewById(R.id.brakes_details);
        details_rims = (TextInputLayout) findViewById(R.id.details_rims);
        bookname = (TextInputLayout) findViewById(R.id.bookname);
        authorname = (TextInputLayout) findViewById(R.id.authername);
        bookdesc = (TextInputLayout) findViewById(R.id.bookdesc);
        desc = (TextInputLayout) findViewById(R.id.desc);

        case_2 = (LinearLayout) findViewById(R.id.case_2);
        case_3 = (LinearLayout) findViewById(R.id.case_3);
        case3_1 = (LinearLayout) findViewById(R.id.case3_1);

        post_price = (EditText) findViewById(R.id.post_price);
        //Spinner element
        Spinner spinner_category = (Spinner) findViewById(R.id.category);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_cat = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_category.setAdapter(adapter_cat);
        //Sppinner Drop down elements for spinner
        List<String> categories = new ArrayList<String>();
        categories.add("Electronics");
        categories.add("Clothes");
        categories.add("Books");
        categories.add("Bikes");
        categories.add("Miscellaneous");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_category.setAdapter(adapter);

        Spinner spinner_clothes = (Spinner) findViewById(R.id.spinner_clothes);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_clothes = ArrayAdapter.createFromResource(this,
                R.array.cloth, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_clothes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_clothes.setAdapter(adapter_clothes);

        Spinner spinner_size = (Spinner) findViewById(R.id.spinner_size);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_size = ArrayAdapter.createFromResource(this,
                R.array.size, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_size.setAdapter(adapter_size);


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                switch(i) {
                    case 0:
                        mstlabel.setVisibility(View.VISIBLE);
                        /*mLabel.setVisibility(View.GONE);
                        mTextField.setVisibility(View.GONE);*/
                        break;

                    case 1:
                        mstlabel.setVisibility(View.GONE);
                        companyname.setVisibility(View.VISIBLE);
                        specific.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        mstlabel.setVisibility(View.GONE);
                        companyname.setVisibility(View.GONE);
                        specific.setVisibility(View.GONE);

                        case_2.setVisibility(View.VISIBLE);
                        specific_cloth.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        mstlabel.setVisibility(View.GONE);
                        companyname.setVisibility(View.GONE);
                        specific.setVisibility(View.GONE);
                        case_2.setVisibility(View.GONE);
                        specific_cloth.setVisibility(View.GONE);

                        modelname.setVisibility(View.VISIBLE);
                        case_3.setVisibility(View.VISIBLE);
                        case3_1.setVisibility(View.VISIBLE);
                        break;

                    case 4:
                        mstlabel.setVisibility(View.GONE);
                        companyname.setVisibility(View.GONE);
                        specific.setVisibility(View.GONE);
                        case_2.setVisibility(View.GONE);
                        specific_cloth.setVisibility(View.GONE);
                        modelname.setVisibility(View.GONE);
                        case_3.setVisibility(View.GONE);
                        case3_1.setVisibility(View.GONE);

                        bookname.setVisibility(View.VISIBLE);
                        authorname.setVisibility(View.VISIBLE);
                        bookdesc.setVisibility(View.VISIBLE);
                        break;

                    case 5 :
                        mstlabel.setVisibility(View.GONE);
                        companyname.setVisibility(View.GONE);
                        specific.setVisibility(View.GONE);
                        case_2.setVisibility(View.GONE);
                        specific_cloth.setVisibility(View.GONE);
                        modelname.setVisibility(View.GONE);
                        case_3.setVisibility(View.GONE);
                        case3_1.setVisibility(View.GONE);
                        bookname.setVisibility(View.GONE);
                        authorname.setVisibility(View.GONE);
                        bookdesc.setVisibility(View.GONE);

                        desc.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //cas 0
            }

        });

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

            final String price = post_price.getText().toString().trim();
            final String category = spinner_category;

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(category) && imageUri != null) {

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
