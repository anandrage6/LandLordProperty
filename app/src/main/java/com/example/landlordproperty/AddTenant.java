package com.example.landlordproperty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddTenant extends AppCompatActivity {
    FloatingActionButton floattingbtnadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        floattingbtnadd = findViewById(R.id.addbtnTenant);
        floattingbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenantDetails();

            }
        });
    }

    public void openActivityAddTenantDetails(){
        Intent i = new Intent(this, AddTenantDetails.class);
        startActivity(i);
    }
}