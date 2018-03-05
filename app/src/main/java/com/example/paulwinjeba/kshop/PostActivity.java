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
import com.google.firebase.auth.FirebaseAuth;
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
    TextInputLayout companyname,specific,specific_cloth,modelname,gear_deatils,noofgear,brakes_details,details_rims,bookname,authorname,bookdesc,desc,desc_bike;
    EditText company_name,device_specification,specific_clothes,model_name,gear,noof_gear,brakes,rims,book_name,author_name,book_desc,description,description_bike;
    LinearLayout case_2,case_3,case3_1;
    private ImageButton post_img;
    private EditText post_title, post_price;
    private Button post_btn;
    private Uri imageUri = null;
    private final int PICK_IMAGE_REQUEST = 7;
    private Spinner category,cloth_type,cloth_size;
    private ProgressDialog mProgress;
    private String spin_category;
    private String desc_cloth_1,desc_cloth_2;

    String description_1,description_2;
    //FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference,mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


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
        description_bike = (EditText) findViewById(R.id.description_bike);

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
        desc_bike = (TextInputLayout) findViewById(R.id.desc_bike);

        case_2 = (LinearLayout) findViewById(R.id.case_2);
        case_3 = (LinearLayout) findViewById(R.id.case_3);
        case3_1 = (LinearLayout) findViewById(R.id.case3_1);

        post_price = (EditText) findViewById(R.id.post_price);

        //Spinner element 1
        category = (Spinner) findViewById(R.id.category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_cat = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category.setAdapter(adapter_cat);
        //Sppinner Drop down elements for spinner
        List<String> categories = new ArrayList<String>();
        categories.add("Electronics");
        categories.add("Clothes");
        categories.add("Bikes");
        categories.add("Books");
        categories.add("Miscellaneous");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        //Spinner element 2
        cloth_type = (Spinner) findViewById(R.id.spinner_clothes);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_clothes = ArrayAdapter.createFromResource(this,
                R.array.cloth, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_clothes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cloth_type.setAdapter(adapter_clothes);
        List<String> clothes_type = new ArrayList<String>();
        clothes_type.add("Cotton");
        clothes_type.add("Wool");
        clothes_type.add("Silk");
        clothes_type.add("Nylon");
        clothes_type.add("Linen");
        clothes_type.add("Chiffon");
        clothes_type.add("Polyester");
        ArrayAdapter<String> clothtype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clothes_type);
        clothtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cloth_type.setAdapter(clothtype);

        //Spinner element 3
        cloth_size = (Spinner) findViewById(R.id.spinner_size);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter_size = ArrayAdapter.createFromResource(this,
                R.array.size, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        cloth_size.setAdapter(adapter_size);
        List<String> clothsize = new ArrayList<String>();
        clothsize.add("S");
        clothsize.add("M");
        clothsize.add("L");
        clothsize.add("XL");
        clothsize.add("XXL");
        ArrayAdapter<String> clothsizes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clothsize);
        clothsizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cloth_size.setAdapter(clothsizes);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        //in every case try doing the reverse also i.e if in case they select last and then first then desc is still visible
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //case 0
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
            final String category = spin_category;


            /* ---------------------------------------------------------------------------------------------------------------------------------------------- */

            mProgress.show();

            StorageReference filepath = storageReference.child("Post_Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            if (category.equals("Electronics")) {
                                description_1 = company_name.getText().toString().trim();
                                description_2 = device_specification.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Post");

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);

                                String key = databaseReference.child("Post").push().getKey();

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                                //Upload in Electronics
                                DatabaseReference newPostE = databaseReference.child("Electronics").child(key).push();

                                newPostE.child("Title").setValue(title);
                                newPostE.child("Image").setValue(downloadUrl.toString());
                                newPostE.child("Category").setValue(category);
                                newPostE.child("Description_1").setValue(description_1);
                                newPostE.child("Description_2").setValue(description_2);
                                newPostE.child("Price").setValue(price);

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key).push();
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                            }

                            else if(category.equals("Clothes")) {
                                description_1 = "Material : " + desc_cloth_2 + " Size : " + desc_cloth_1;
                                description_2 = specific_clothes.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Post");

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);

                                String key = databaseReference.child("Post").push().getKey();

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                                //Upload in Electronics
                                DatabaseReference newPostE = databaseReference.child("Clothes").child(key).push();

                                newPostE.child("Title").setValue(title);
                                newPostE.child("Image").setValue(downloadUrl.toString());
                                newPostE.child("Category").setValue(category);
                                newPostE.child("Description_1").setValue(description_1);
                                newPostE.child("Description_2").setValue(description_2);
                                newPostE.child("Price").setValue(price);

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key).push();
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                            }

                            else if(category.equals("Bikes")){

                                String desc_model = model_name.getText().toString().trim();
                                String desc_gear = gear.getText().toString().trim();
                                String nogear = noof_gear.getText().toString().trim();
                                String brake_s = brakes.getText().toString().trim();
                                String rim_s = rims.getText().toString().trim();
                                description_1 = "Model : "+desc_model+" with Gear : "+desc_gear+ " Number of gears : "+nogear+ " , with Brakes : " +brake_s+ " and Rims : "+rim_s;
                                description_2 = description_bike.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Post");

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);

                                String key = databaseReference.child("Post").push().getKey();

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                                //Upload in Bikes
                                DatabaseReference newPostE = databaseReference.child("Bikes").child(key).push();

                                newPostE.child("Title").setValue(title);
                                newPostE.child("Image").setValue(downloadUrl.toString());
                                newPostE.child("Category").setValue(category);
                                newPostE.child("Description_1").setValue(description_1);
                                newPostE.child("Description_2").setValue(description_2);
                                newPostE.child("Price").setValue(price);

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key).push();
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                            }

                            else if(category.equals("Books")){

                                String bookname = book_name.getText().toString().trim();
                                String author = author_name.getText().toString().trim();
                                description_1 = "Book : "+bookname+ " Author : "+author;
                                description_2 = book_desc.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Post");

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);

                                String key = databaseReference.child("Post").push().getKey();

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                                //Upload in Books
                                DatabaseReference newPostE = databaseReference.child("Books").child(key).push();

                                newPostE.child("Title").setValue(title);
                                newPostE.child("Image").setValue(downloadUrl.toString());
                                newPostE.child("Category").setValue(category);
                                newPostE.child("Description_1").setValue(description_1);
                                newPostE.child("Description_2").setValue(description_2);
                                newPostE.child("Price").setValue(price);

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key).push();
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);
                            }

                            else if(category.equals("Miscellaneous")){

                                description_1 = company_name.getText().toString().trim();
                                description_2 = device_specification.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Post");

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);

                                String key = databaseReference.child("Post").push().getKey();

                                mProgress.dismiss();
                                Intent home_again = new Intent(PostActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(PostActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                                //Upload in Books
                                DatabaseReference newPostE = databaseReference.child("Miscellaneous").child(key).push();

                                newPostE.child("Title").setValue(title);
                                newPostE.child("Image").setValue(downloadUrl.toString());
                                newPostE.child("Category").setValue(category);
                                newPostE.child("Description_1").setValue(description_1);
                                newPostE.child("Description_2").setValue(description_2);
                                newPostE.child("Price").setValue(price);

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key).push();
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                            }
                        }
                    });
            filepath.putFile(imageUri).addOnFailureListener((new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Intent homeintent = new Intent(PostActivity.this, HomeActivity.class);
                    homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeintent);
                    Toast.makeText(PostActivity.this, "Unable to Upload Files...", Toast.LENGTH_LONG).show();
                }
            }));

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
