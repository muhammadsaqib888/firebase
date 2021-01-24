package com.example.abhi_g.abseeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class crop extends AppCompatActivity implements View.OnClickListener {
    private Button pic;
    private EditText cropname,murabanumber,date,adrees,meting,id;
    private ImageView imgview;
    private TextView send;
    private static final int PICK_IMAGE=100;
    Uri imgUri;
    private DatabaseReference mdatabase;
    private StorageReference mStor;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        send=(TextView) findViewById(R.id.send) ;

        progressDialog= new ProgressDialog(this);
        mStor = FirebaseStorage.getInstance().getReference();

        id= (EditText) findViewById(R.id.id);
        cropname= (EditText) findViewById(R.id.cropname);
        murabanumber=(EditText) findViewById(R.id.murabanumber);
        date= (EditText) findViewById( R.id.srwaydate);
        adrees=(EditText) findViewById(R.id.address);
        meting= (EditText) findViewById(R.id.meting);

        pic=(Button) findViewById(R.id.imgbtn);
        imgview=(ImageView) findViewById(R.id.imgview);
        send.setOnClickListener(this);
        pic.setOnClickListener(this);
    }
public void upload(){ //Data Will Upload..
    progressDialog.setMessage("Data Uploading...");
    final String DATE = date.getText().toString().trim();
    String MURABA= murabanumber.getText().toString().trim();
    String CROP= cropname.getText().toString().trim();
    String ADDRESS= adrees.getText().toString().trim();
    String METING= meting.getText().toString().trim();
    final String ID= id.getText().toString().trim();
    if(TextUtils.isEmpty(ID) || TextUtils.isEmpty(MURABA) || TextUtils.isEmpty(CROP) || TextUtils.isEmpty(ADDRESS) || TextUtils.isEmpty(METING) || TextUtils.isEmpty(DATE)){
        Toast.makeText(this,"Please Fill All the Fields...",Toast.LENGTH_LONG).show();
    }
    else{
        progressDialog.show();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Survey Records").child(ID).child("Crop Survey").child(DATE);
        HashMap<String , String> hashMap= new HashMap<String, String>();
        hashMap.put("Crop Name", CROP);
        hashMap.put("Moraba N0",MURABA);
        hashMap.put("Address",ADDRESS);
        hashMap.put("Explanation",METING);
        mdatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    StorageReference filePath= mStor.child("Crop Survey Pictures").child(ID).child(DATE).child(imgUri.getLastPathSegment());
                    filePath.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(crop.this, "Image Uploaded Successfully!", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(crop.this, "Image Cannot Uploaded !", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                    Toast.makeText(crop.this,"Data Uploaded Successfully",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),Home.class));

                }
                else{ progressDialog.dismiss();
                    Toast.makeText(crop.this,"Data is Not Uploaded Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
    @Override
    public void onClick(View v) {
        if(v==pic){
            //image will upload..
            opengallery();
        }
        if(v==send){

            upload();
        }
    }
    protected void opengallery(){
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imgUri= data.getData();
            imgview.setImageURI(imgUri);
        }
    }
}
