package com.example.abhi_g.abseeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class seeds extends AppCompatActivity {
    private Button seeds,fertilizer;
   private ListView list,list2;
    private DatabaseReference mdata;
    private ArrayList <String> mlist = new ArrayList<>();
    private ArrayList<String > mlist2= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);
        seeds = (Button) findViewById(R.id.seeds);
        list = (ListView) findViewById(R.id.list);
        list2 = (ListView) findViewById(R.id.list2);
        fertilizer = (Button) findViewById(R.id.fertilizer);

        mdata = FirebaseDatabase.getInstance().getReference().child("Seeds");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mlist);
        list.setAdapter(arrayAdapter);
        mdata.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value= dataSnapshot.getValue(String.class);
                mlist.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mdata = FirebaseDatabase.getInstance().getReference().child("Fertilizer");
        final ArrayAdapter<String> arrrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mlist2);
        list2.setAdapter(arrrayAdapter);
        mdata.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value= dataSnapshot.getValue(String.class);
                mlist2.add(value);
                arrrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
