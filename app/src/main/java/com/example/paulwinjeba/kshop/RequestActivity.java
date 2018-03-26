package com.example.paulwinjeba.kshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class RequestActivity extends AppCompatActivity {

    private EditText title_name,descri,price_exp;
    private Spinner category;
    private TextView mstlabel;
    private String item;
    private DatabaseReference mydatabase;
    private FirebaseAuth myauth;
    private Button upload_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        myauth = FirebaseAuth.getInstance();
        final String uuid = myauth.getCurrentUser().getUid();
        mydatabase = FirebaseDatabase.getInstance().getReference();

        title_name = (EditText) findViewById(R.id.title_name);
        descri = (EditText) findViewById(R.id.descri);
        price_exp = (EditText) findViewById(R.id.price_exp);

        upload_request = (Button) findViewById(R.id.post_request);

        mstlabel = (TextView) findViewById(R.id.must_label);

        category = (Spinner) findViewById(R.id.category);
        //spinner click listener
        //Sppinner Drop down elements for spinner
        final java.util.List<String> categories = new ArrayList<String>();
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

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
                switch (i) {
                    case 0:
                        mstlabel.setVisibility(View.VISIBLE);
                        break;
                    case 1 :
                    case 2 :
                    case 3 :
                    case 4 :
                    case 5 :
                        mstlabel.setVisibility(View.GONE);
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //case 0
            }

        });

        upload_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = title_name.getText().toString().trim();
                String description = descri.getText().toString().trim();
                String price = price_exp.getText().toString().trim();
                String category = item;

                DatabaseReference newRequest = mydatabase.child("Requests").push();
                //String key = newRequest.getKey();
                newRequest.child("Title").setValue(title);
                newRequest.child("Category").setValue(category);
                newRequest.child("Description").setValue(description);
                newRequest.child("Expected_Price").setValue(price);
                newRequest.child("User").setValue(uuid);

                //DatabaseReference newRequestUser = mydatabase.child("Users").child(uuid).child(key);
            }
        });
    }
}
