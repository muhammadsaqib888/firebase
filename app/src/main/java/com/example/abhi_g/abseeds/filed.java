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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;

public class filed extends AppCompatActivity implements View.OnClickListener{
    private Button pic;
    private EditText muraba,address,date,meting,id;
    private RadioButton ratli,chikni,mera;
    private ProgressDialog progressDialog;
    private DatabaseReference mdatabase;
    private StorageReference mStor;
    private TextView send;
    private ImageView imgview;
    private static final int PICK_IMAGE=100;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filed);
        send=(TextView) findViewById(R.id.send);

        mStor= FirebaseStorage.getInstance().getReference();

        progressDialog= new ProgressDialog(this);
        pic=(Button) findViewById(R.id.imgbtn);
        muraba= (EditText) findViewById(R.id.murabanumber);
        date= (EditText) findViewById(R.id.srwaydate);
        meting=(EditText) findViewById(R.id.description);
        address=(EditText) findViewById(R.id.address);
        ratli=(RadioButton) findViewById(R.id.ratli);
        mera= (RadioButton) findViewById(R.id.mera);
        id= (EditText) findViewById(R.id.id);
        chikni= (RadioButton) findViewById(R.id.chekni);
        imgview=(ImageView) findViewById(R.id.imgview);
        send.setOnClickListener(this);
        pic.setOnClickListener(this);
    }
    public void upload(){
        //Data will send...
       progressDialog.setMessage("Data Uploading...");
        final String DATE = date.getText().toString().trim();
        String ADDRESS= address.getText().toString().trim();
        String MURABA= muraba.getText().toString().trim();
        String METING= meting.getText().toString().trim();
        final String ID= id.getText().toString().trim();
        String ZAMEEN= "چیکنی";
        if(chikni.isChecked()){
            ZAMEEN= "چیکنی";
        }
        else if(mera.isChecked()){
            ZAMEEN="میرا";
        }
        else if(ratli.isChecked()){
            ZAMEEN= "ریتلی";
        }
        else if(mera.isChecked() && chikni.isChecked()){
            ZAMEEN=" چیکنی میرا";
        }
        else if(mera.isChecked() && ratli.isChecked() && chikni.isChecked()){
            ZAMEEN=" ریتلی چیکنی میرا";
        }
        else if(mera.isChecked() && ratli.isChecked()){
            ZAMEEN="ریتلی میرا";
        }
        else {
            ZAMEEN="چیکنی ریتلی" ;
        }
        if(TextUtils.isEmpty(ADDRESS) || TextUtils.isEmpty(METING) || TextUtils.isEmpty(MURABA) ||TextUtils.isEmpty(DATE) || TextUtils.isEmpty(ID)){
            Toast.makeText(this,"Please Fill All the Fields!",Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.show();
            mdatabase= FirebaseDatabase.getInstance().getReference().child("Survey Records").child(ID).child("Field Survey").child(DATE);
            HashMap<String , String> hashMap= new HashMap<String, String>();
            hashMap.put("زمین کی قسم", ZAMEEN);
            hashMap.put(" مربہ نمبر ",MURABA);
            hashMap.put(" پتہ ",ADDRESS);
            hashMap.put(" وضاحت ",METING);
            mdatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        StorageReference filepath= mStor.child("Field Survey Pictures").child(ID).child(DATE).child(imgUri.getLastPathSegment());
                        filepath.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(filed.this, "Image Uploaded Successfully!", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                                else {
                                    Toast.makeText(filed.this, "Image Cannot Uploaded !", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }

                        });
                        Toast.makeText(filed.this,"Data Uploaded Successfully",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),Home.class));

                    }
                    else{ progressDialog.dismiss();
                        Toast.makeText(filed.this,"Data is Not Uploaded Successfully",Toast.LENGTH_LONG).show();
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
        if(v==pic){
            opengallery();
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
