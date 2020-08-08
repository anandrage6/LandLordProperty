package com.example.landlordproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    //instance variables

    private CardView appartments, addappartments, tenants, payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //card id's
        appartments = (CardView) findViewById(R.id.appartments_card);
        addappartments = (CardView) findViewById(R.id.addapartments_card);
        tenants = (CardView) findViewById(R.id.tenants_card);
        payments = (CardView) findViewById(R.id.payments_card);


        //Add click listner to cards
        appartments.setOnClickListener(this);
        addappartments.setOnClickListener(this);
        tenants.setOnClickListener(this);
        payments.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        // Switching cards
        Intent i;
        switch(view.getId()) {
            case R.id.appartments_card:
                i = new Intent(this, Appartments.class);
                startActivity(i);
                break;
            case R.id.addapartments_card:
                i = new Intent(this, AddAppartment.class);
                startActivity(i);
                break;
            case R.id.tenants_card:
                i = new Intent(this, AddTenant.class);
                startActivity(i);
                break;
            case R.id.payments_card:
                i = new Intent(this, Payment.class);
                startActivity(i);
                break;
        }

    }
}


