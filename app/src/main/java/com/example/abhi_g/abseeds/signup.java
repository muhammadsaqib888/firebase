package com.example.abhi_g.abseeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity implements View.OnClickListener{
    private Button signup;
    private EditText cnicf,namef,fnamef,idf,dist,add;
    private ProgressDialog progressDialog;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog= new ProgressDialog(this);

        signup= (Button) findViewById(R.id.signup);
        cnicf= (EditText) findViewById(R.id.cnic);
        namef=(EditText) findViewById(R.id.name);
        fnamef=(EditText) findViewById(R.id.fname);
        idf=(EditText) findViewById(R.id.id);
        dist=(EditText) findViewById(R.id.dist);
        add=(EditText) findViewById(R.id.add);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String CNIC=cnicf.getText().toString().trim();
        String NAME=namef.getText().toString().trim();
        String FNAME=fnamef.getText().toString().trim();
        String ID=idf.getText().toString().trim();
        String ADDRESS=add.getText().toString().trim();
        String DIST=dist.getText().toString().trim();
        if(TextUtils.isEmpty(CNIC) || TextUtils.isEmpty(NAME) || TextUtils.isEmpty(FNAME  )|| TextUtils.isEmpty(DIST)|| TextUtils.isEmpty(ID) || TextUtils.isEmpty(ADDRESS)){
            Toast.makeText(this,"Please Fill all the fields..",Toast.LENGTH_LONG).show();
        }
        else
        {mdatabase= FirebaseDatabase.getInstance().getReference().child("Assistant Records").child(ID);
            progressDialog.setMessage("Data Uploading...");
            progressDialog.show();
            HashMap<String, String> hashMap= new HashMap<String, String>();
            hashMap.put("CNIC", CNIC);
            hashMap.put("Name", NAME);
            hashMap.put("Father Name", FNAME);
            hashMap.put("Assistant ID", ID);
            hashMap.put("Phone N0", DIST);
            hashMap.put("Address", ADDRESS);

            mdatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(signup.this,"Data have been Uploaded !" , Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        progressDialog.dismiss();

                    }
                    else {
                        Toast.makeText(signup.this,"Data Cannot Uploaded !" , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }
}
