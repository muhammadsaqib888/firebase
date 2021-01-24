package com.example.abhi_g.abseeds;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private ImageButton farmer,seed,crop,filed;
    private Button logout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        farmer=(ImageButton) findViewById(R.id.farmeri);
        seed=(ImageButton) findViewById(R.id.seedi);
        crop=(ImageButton) findViewById(R.id.cropi);
        filed=(ImageButton) findViewById(R.id.srwayi);
        logout= (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        farmer.setOnClickListener(this);
        seed.setOnClickListener(this);
        crop.setOnClickListener(this);
        filed.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
if(v==farmer){
    finish();
    //farmer meting information activity...
    startActivity(new Intent(this,farmerMeting.class));
    }
    else if(v==seed){
    //seeds & fertilizers informations activity...
    finish();
    startActivity(new Intent(this,seeds.class));
    }
    else if(v==crop){
    finish();
    //crop serway information activity...
    startActivity(new Intent(this, crop.class));
    }
    else if(v==filed){
    finish();
    //filed sarway information actvity...
    startActivity(new Intent(this, filed.class));
    }
    if(v==logout){
        // Log Out Function Calling
      firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    }

}
