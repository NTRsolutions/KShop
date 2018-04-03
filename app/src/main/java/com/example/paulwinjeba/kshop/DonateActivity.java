package com.example.paulwinjeba.kshop;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.*;

public class DonateActivity extends AppCompatActivity {
    TextView mstlabel;
    TextInputLayout companyname,specific,specific_cloth,modelname,gear_deatils,noofgear,brakes_details,details_rims,bookname,authorname,bookdesc,desc,desc_bike;
    EditText company_name,device_specification,specific_clothes,model_name,gear,noof_gear,brakes,rims,book_name,author_name,book_desc,description,description_bike;
    LinearLayout case_2,case_3,case3_1;
    private ImageButton post_img;
    private EditText post_title;
    private Button post_btn;
    private Uri imageUri = null;
    private final int PICK_IMAGE_REQUEST = 7;
    private Spinner category,cloth_type,cloth_size;
    //private ProgressDialog mProgress;
    private String item,clothtype1,clothsize1,key,post_price = "FREE";

    String description_1,description_2;
    //FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference,mDatabase;
    private FirebaseAuth mAuth;
    String uuid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

            storageReference = FirebaseStorage.getInstance().getReference();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            uuid=mAuth.getCurrentUser().getUid().toString();

            post_img = (ImageButton) findViewById(R.id.post_img);
            //mProgress = new ProgressDialog(this);
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

            //Spinner element 1
            category = (Spinner) findViewById(R.id.category);
            //spinner click listener
            //Sppinner Drop down elements for spinner
            java.util.List<String> categories = new ArrayList<String>();
            categories.add("Select One Category");
            categories.add("Electronics");
            categories.add("Clothes");
            categories.add("Bikes");
            categories.add("Books");
            categories.add("Miscellaneous");
            //CReating adapter for spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(adapter);

            //Spinner element 2
            cloth_type = (Spinner) findViewById(R.id.spinner_clothes);
            // Apply the adapter to the spinner
            java.util.List<String> clothes_type = new ArrayList<String>();
            clothes_type.add("Cotton");
            clothes_type.add("Wool");
            clothes_type.add("Silk");
            clothes_type.add("Nylon");
            clothes_type.add("Linen");
            clothes_type.add("Chiffon");
            clothes_type.add("Polyester");
            final ArrayAdapter<String> clothtype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clothes_type);
            clothtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cloth_type.setAdapter(clothtype);
            cloth_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    clothtype1 = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Spinner element 3
            cloth_size = (Spinner) findViewById(R.id.spinner_size);
            java.util.List<String> clothsize = new ArrayList<String>();
            clothsize.add("S");
            clothsize.add("M");
            clothsize.add("L");
            clothsize.add("XL");
            clothsize.add("XXL");
            ArrayAdapter<String> clothsizes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clothsize);
            clothsizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cloth_size.setAdapter(clothsizes);
            cloth_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    clothsize1 = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    item = adapterView.getItemAtPosition(i).toString();
                    switch(i) {
                        case 0:
                            mstlabel.setVisibility(View.VISIBLE);
                        /*mLabel.setVisibility(View.GONE);
                        mTextField.setVisibility(View.GONE);*/
                            break;

                        case 1:
                            mstlabel.setVisibility(View.GONE);
                            case_2.setVisibility(View.GONE);
                            specific_cloth.setVisibility(View.GONE);
                            modelname.setVisibility(View.GONE);
                            case_3.setVisibility(View.GONE);
                            case3_1.setVisibility(View.GONE);
                            bookname.setVisibility(View.GONE);
                            authorname.setVisibility(View.GONE);
                            bookdesc.setVisibility(View.GONE);
                            desc.setVisibility(View.GONE);

                            companyname.setVisibility(View.VISIBLE);
                            specific.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            mstlabel.setVisibility(View.GONE);
                            companyname.setVisibility(View.GONE);
                            specific.setVisibility(View.GONE);
                            modelname.setVisibility(View.GONE);
                            case_3.setVisibility(View.GONE);
                            case3_1.setVisibility(View.GONE);
                            bookname.setVisibility(View.GONE);
                            authorname.setVisibility(View.GONE);
                            bookdesc.setVisibility(View.GONE);
                            desc.setVisibility(View.GONE);

                            case_2.setVisibility(View.VISIBLE);
                            specific_cloth.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            mstlabel.setVisibility(View.GONE);
                            companyname.setVisibility(View.GONE);
                            specific.setVisibility(View.GONE);
                            case_2.setVisibility(View.GONE);
                            specific_cloth.setVisibility(View.GONE);
                            bookname.setVisibility(View.GONE);
                            authorname.setVisibility(View.GONE);
                            bookdesc.setVisibility(View.GONE);
                            desc.setVisibility(View.GONE);

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
                            desc.setVisibility(View.GONE);

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
                        //DONE
                        // /in every case try doing the reverse also i.e if in case they select last and then first then desc is still visible
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
            final String price = post_price;
            final String category = item;


            /* ---------------------------------------------------------------------------------------------------------------------------------------------- */

            //mProgress.show();

            StorageReference filepath = storageReference.child("Post_Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            if (category.equals("Electronics")) {
                                description_1 = company_name.getText().toString().trim();
                                description_2 = device_specification.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Donation").push();

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);
                                newPost.child("UID").setValue(uuid);

                                String key = databaseReference.child("Donation").push().getKey();

                                //mProgress.dismiss();
                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key);
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                                Intent home_again = new Intent(DonateActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(DonateActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                            }

                            else if(category.equals("Clothes")) {
                                description_1 = "Material : " + clothtype1 + " Size : " + clothsize1;
                                description_2 = specific_clothes.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Donation").push();

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);
                                newPost.child("UID").setValue(uuid);

                                key = databaseReference.child("Donation").push().getKey();

                                //mProgress.dismiss();
                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key);
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                                Intent home_again = new Intent(DonateActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(DonateActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                            }

                            else if(category.equals("Bikes")){

                                String desc_model = model_name.getText().toString().trim();
                                String desc_gear = gear.getText().toString().trim();
                                String nogear = noof_gear.getText().toString().trim();
                                String brake_s = brakes.getText().toString().trim();
                                String rim_s = rims.getText().toString().trim();
                                description_1 = "Model : "+desc_model+" with Gear : "+desc_gear+ " Number of gears : "+nogear+ " , with Brakes : " +brake_s+ " and Rims : "+rim_s;
                                description_2 = description_bike.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Donation").push();

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);
                                newPost.child("UID").setValue(uuid);

                                key = databaseReference.child("Donation").push().getKey();

                                //mProgress.dismiss();

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key);
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                                Intent home_again = new Intent(DonateActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(DonateActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                            }

                            else if(category.equals("Books")){

                                String bookname = book_name.getText().toString().trim();
                                String author = author_name.getText().toString().trim();
                                description_1 = "Book : "+bookname+ " Author : "+author;
                                description_2 = book_desc.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Donation").push();

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);
                                newPost.child("UID").setValue(uuid);

                                key = databaseReference.child("Donation").push().getKey();

                                //mProgress.dismiss();

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key);
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                                Intent home_again = new Intent(DonateActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(DonateActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();
                            }

                            else if(category.equals("Miscellaneous")){

                                description_1 = post_title.getText().toString().trim();
                                description_2 = description.getText().toString().trim();

                                DatabaseReference newPost = databaseReference.child("Donation").push();

                                key = databaseReference.child("Donation").push().getKey().toString();

                                newPost.child("Title").setValue(title);
                                newPost.child("Image").setValue(downloadUrl.toString());
                                newPost.child("Category").setValue(category);
                                newPost.child("Description_1").setValue(description_1);
                                newPost.child("Description_2").setValue(description_2);
                                newPost.child("Price").setValue(price);
                                newPost.child("UID").setValue(uuid);
                                //mProgress.dismiss();

                                //Upload under user
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference myPost = mDatabase.child(user_id).child("My Post").child(key);
                                myPost.child("Title").setValue(title);
                                myPost.child("Image").setValue(downloadUrl.toString());
                                myPost.child("Category").setValue(category);
                                myPost.child("Description_1").setValue(description_1);
                                myPost.child("Description_2").setValue(description_2);
                                myPost.child("Price").setValue(price);

                                Intent home_again = new Intent(DonateActivity.this, HomeActivity.class);
                                startActivity(home_again);
                                Toast.makeText(DonateActivity.this, "Successfully Uploaded...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
            filepath.putFile(imageUri).addOnFailureListener((new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Intent homeintent = new Intent(DonateActivity.this, HomeActivity.class);
                    homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeintent);
                    Toast.makeText(DonateActivity.this, "Unable to Upload Files...", Toast.LENGTH_LONG).show();
                }
            }));

        }catch (Exception e){
            Toast.makeText(DonateActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e("Error:",e.getMessage());
        }
        finally {
            /*Intent homeintent = new Intent(PostActivity.this, HomeActivity.class);
            homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeintent);*/
        }//NOT TO GO BACK
    }
    @Override
    public void onBackPressed(){
        //Toast.makeText(PostActivity.this,"Cancelled...Press back again to Exit.",Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}
