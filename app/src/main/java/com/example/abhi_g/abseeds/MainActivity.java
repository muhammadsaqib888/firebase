package com.example.abhi_g.abseeds;

import android.app.Dialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//Creating Instances of Wadges
    private Button login;
    private EditText cnic;
    private EditText password;
    private TextView register;
    //Progress Diload to Show Masseges for siging in to dely the user for internet Connection..
    private ProgressDialog progressDialog;
    //firebase Auth Instance
    private FirebaseAuth firebaseAuth;
    //private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //Geting firebase Instance...
        firebaseAuth= FirebaseAuth.getInstance();
//checking wether user loged in or not...
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home.class));
        }
        //geting instances of other wadges....
        progressDialog= new ProgressDialog(this);
        login = (Button) findViewById(R.id.login);
        cnic= (EditText) findViewById(R.id.cnic);
        password= (EditText)findViewById(R.id.password);
        register=(TextView) findViewById(R.id.signup);
        //seting up Click Listeners...
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view == login){
        //after clocking the login Button it will executed...
            signin();
        }
        if(view==register)
        {
            //after click on Register Text the Register Activty will open....
            finish();
            startActivity(new Intent(this, logup.class));
        }
    }
    //Signing in Process...
    public void signin(){
      //converting the filed data into Strings..
       String email= cnic.getText().toString().trim();
        String pass= password.getText().toString().trim();
        progressDialog.setMessage("Siging in...");
        //Checking wether the fileds are filed or not??
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Plese Enter Email and Password",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            //calling the Firebase Signin method by using email and password...
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Email or Password Invalid !",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}