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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class farmerMeting extends AppCompatActivity implements View.OnClickListener{
    private TextView send;
    private EditText cnic,name,phone,moraba,address,date,metting,id;
    private ProgressDialog progressDialog;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_meting);

        send=(TextView) findViewById(R.id.send);
        cnic= (EditText) findViewById(R.id.cnic);
        moraba= (EditText) findViewById(R.id.murabanumber);
        address= (EditText) findViewById(R.id.address);
        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phonnumber);
        metting= (EditText) findViewById(R.id.meting);
        date= (EditText) findViewById(R.id.srwaydate);
        id= (EditText) findViewById(R.id.id);

        progressDialog = new ProgressDialog(this);
        send.setOnClickListener(this);




    }
    public void upload(){
        //data  will uploaded...

        String CNIC= cnic.getText().toString().trim();
        String MORABA= moraba.getText().toString().trim();
        String DATE= date.getText().toString().trim();
        String ID= id.getText().toString().trim();
        String NAME = name.getText().toString().trim();
        String PHONE= phone.getText().toString().trim();
        String METTING= metting.getText().toString().trim();
        String ADDRESS= address.getText().toString().trim();
        if(TextUtils.isEmpty(CNIC) || TextUtils.isEmpty(NAME) || TextUtils.isEmpty(MORABA  )|| TextUtils.isEmpty(DATE)|| TextUtils.isEmpty(METTING) || TextUtils.isEmpty(PHONE)|| TextUtils.isEmpty(ADDRESS)){
            Toast.makeText(this,"Please Fill all the fields..",Toast.LENGTH_LONG).show();
        }
        else
        {mdatabase= FirebaseDatabase.getInstance().getReference().child("Survey Records").child(ID).child("Farmer Meting").child(DATE);
            progressDialog.setMessage("Data Uploading...");
            progressDialog.show();
            HashMap<String, String> hashMap= new HashMap<String, String>();
            hashMap.put("CNIC", CNIC);
            hashMap.put("Farmer Name", NAME);
            hashMap.put("Phone N0", PHONE);
            hashMap.put("Address", ADDRESS);
            hashMap.put("Moraba N0", MORABA);
            hashMap.put("Safarshaat", METTING);
            mdatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(farmerMeting.this,"Data have been Uploaded !" , Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        progressDialog.dismiss();

                    }
                    else {
                        Toast.makeText(farmerMeting.this,"Data Cannot Uploaded !" , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }

    }
    @Override
    public void onClick(View v) {
        if(v==send){
            upload();
        }
    }
}
