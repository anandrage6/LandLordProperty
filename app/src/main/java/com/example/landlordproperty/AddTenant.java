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

public class AddTenant extends AppCompatActivity {
    RecyclerView recyclerView;
    TenantRecyclerAdapter tenantAdapter;
    FloatingActionButton floattingbtnadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        //set title on toolbar
        this.setTitle("Tenants");

        floattingbtnadd = findViewById(R.id.addbtnTenant);
        floattingbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenantDetails();

            }
        });


        //querying from database and get result in to recyclerview
        recyclerView = findViewById(R.id.tenantrecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<TenantPostModel> options =
                new FirebaseRecyclerOptions.Builder<TenantPostModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tenants"), TenantPostModel.class)
                        .build();
        tenantAdapter = new TenantRecyclerAdapter(options, this);
        recyclerView.setAdapter(tenantAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        tenantAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        tenantAdapter.stopListening();
    }


    public void openActivityAddTenantDetails(){
        Intent i = new Intent(this, AddTenantDetails.class);
        startActivity(i);
    }
}