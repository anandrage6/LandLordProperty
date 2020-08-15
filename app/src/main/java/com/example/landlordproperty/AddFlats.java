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

public class AddFlats extends AppCompatActivity {

    RecyclerView recyclerView;
    FlatsRecyclerView flatsAdapter;
    FloatingActionButton floattingbtnadd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flats);

        floattingbtnadd = findViewById(R.id.addflatsfloatingaddbtn);
        floattingbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddDetailsFlat();

            }
        });




        //querying from database and get result
        recyclerView = findViewById(R.id.addflatsrecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions <FlatsPostModel> options = new  FirebaseRecyclerOptions.Builder<FlatsPostModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Flats"), FlatsPostModel.class)
                .build();
        flatsAdapter = new FlatsRecyclerView(options, this);
        recyclerView.setAdapter(flatsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        flatsAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        flatsAdapter.stopListening();
    }




    public void openActivityAddDetailsFlat(){
        Intent i = new Intent(this, AddDetailsFlat.class);
        startActivity(i);
    }
}