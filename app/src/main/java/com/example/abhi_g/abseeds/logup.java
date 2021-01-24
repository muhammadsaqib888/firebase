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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logup extends AppCompatActivity implements View.OnClickListener{
    private Button conn;
    private EditText pass,email,rpass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home.class));
        }

       pass=(EditText) findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        conn= (Button) findViewById(R.id.login);
        rpass=(EditText) findViewById(R.id.rpassword);
        conn.setOnClickListener(this);
        progressDialog= new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if(v==conn){

            String emaila= email.getText().toString().trim();
            String password= pass.getText().toString().trim();
            String rpassword= rpass.getText().toString().trim();
            progressDialog.setMessage("Please Wait...");
            //Check weather the password match or not...
            if(password.equalsIgnoreCase(rpassword)) {
                //if password matched...
                if(TextUtils.isEmpty(emaila) || TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please Enter Email Password!",Toast.LENGTH_SHORT).show();
                }
                 else{
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(emaila,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(),signup.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(logup.this,"Registration is not Complete",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            else {
            Toast.makeText(this, "Password Not Matched!", Toast.LENGTH_SHORT).show();
            return;
            }
        }
    }
}
