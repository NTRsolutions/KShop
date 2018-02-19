package com.example.paulwinjeba.kshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostSigninActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText emailField;
    private EditText phoneField;
    private EditText passField;
    private EditText repassField;
    private Button signupBtn;
    private EditText addr;
    private AwesomeValidation awesomeValidation;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_signin);

        nameField = (EditText) findViewById(R.id.username);
        emailField = (EditText) findViewById(R.id.email);
        phoneField = (EditText) findViewById(R.id.phno);
        passField = (EditText) findViewById(R.id.password);
        addr = (EditText) findViewById(R.id.address);
        repassField = (EditText) findViewById(R.id.repassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mProgress = new ProgressDialog(this);
        signupBtn=(Button)findViewById(R.id.registerbtn);
        awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password, "^.{8,}$", R.string.passworderror);//.{8,} //[a-zA-Z]\w{4,15}
        awesomeValidation.addValidation(this, R.id.phno, "^[0-9]{10}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.repassword,R.id.password, R.string.repassworderror);
        awesomeValidation.addValidation(this,R.id.address,"^(([a-zA-Z0-9,.]+)*( )+)$",R.string.addrerror);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkValidate();
            }
        });
    }

    private void checkValidate() {
        if(awesomeValidation.validate())
        {
            Signin();
        }
    }

    private void Signin() {
        final String name = nameField.getText().toString().trim();
        final String email = emailField.getText().toString().trim();
        String password = passField.getText().toString().trim();
        String repassword = repassField.getText().toString().trim();
        final String address = addr.getText().toString().trim();
        final String phonenumber = phoneField.getText().toString().trim();

        if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(phonenumber) && !TextUtils.isEmpty(email) && (!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(repassword)))
        {

            mProgress.setMessage("Signing Up...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("Name").setValue(name);
                        current_user_db.child("Phone Number").setValue(phonenumber);
                        current_user_db.child("Email ID").setValue(email);
                        current_user_db.child("Address").setValue(address);

                        //mProgress.dismiss();

                        Intent postsignin = new Intent(PostSigninActivity.this, PostActivity.class);
                        postsignin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(postsignin);
                        Toast.makeText(PostSigninActivity.this,"Successfully Registered... Ready to Post...",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
