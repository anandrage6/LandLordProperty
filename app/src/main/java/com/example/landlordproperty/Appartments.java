package com.example.landlordproperty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class Appartments extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    FloatingActionButton btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appartments);
        //set title on toolbar
        this.setTitle("My Properties");

        btnadd = findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddAppartments();

            }
        });

        //querying from database and get result
        recyclerView = findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Appartments"), PostModel.class)
                        .build();
        adapter = new RecyclerAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void openActivityAddAppartments(){
        Intent i = new Intent(this, AddAppartment.class);
        startActivity(i);
    }
}