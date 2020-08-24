package com.example.landlordproperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;

public class Payment extends AppCompatActivity {
    FloatingActionButton btnadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //set title on toolbar
        this.setTitle("Payments");

        btnadd = findViewById(R.id.payAddbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityAddPayment();

            }
        });

    }
    public void openActivityAddPayment(){
        Intent i = new Intent(this, AddPayment.class);
        startActivity(i);
    }
}