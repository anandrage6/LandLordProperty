package com.example.landlordproperty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsFlat extends AppCompatActivity implements IfirebaseLoadDone {
    Spinner spinner;
    DatabaseReference databaseReference;
    IfirebaseLoadDone ifirebaseloaddone;


    List<PostModel> AllAppartments;


    TextView propertynametv, addresstv, citytv, statetv, zipcodetv;
    EditText flatnoedttxt;
    String propertyname, address;
    Boolean isFirstTimeClick = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details_flat);

        propertynametv = findViewById(R.id.propertySelect);
        addresstv = findViewById(R.id.addressSelect);
        citytv = findViewById(R.id.citySelect);
        statetv = findViewById(R.id.stateSelect);
        zipcodetv = findViewById(R.id.zipcodeSelect);




        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //fix first item Click
                if(!isFirstTimeClick){
                    PostModel app = AllAppartments.get(i);
                    propertynametv.setText("Property Name   :   "+app.getPropertyName());
                    addresstv.setText("Address              :   "+app.getAddress());
                    citytv.setText("City                    :   "+app.getCity());
                    statetv.setText("State                  :   "+app.getState());
                    zipcodetv.setText("ZipCode              :   "+app.getZipcode());

                }else{
                    isFirstTimeClick = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Appartments");
        //interface
        ifirebaseloaddone = this;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PostModel> appartmentslist = new ArrayList<>();
                for(DataSnapshot Appartmentssnapshot : snapshot.getChildren()){
                    appartmentslist.add(Appartmentssnapshot.getValue(PostModel.class));
                }
                ifirebaseloaddone.onFireBaseLoadSuccess(appartmentslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ifirebaseloaddone.onFireBaseLoadFailed(databaseError.getmessage());

            }
        });

        Intent intent = new Intent(AddDetailsFlat.this, AddFlats.class);
        startActivity(intent);
    }

    @Override
    public void onFireBaseLoadSuccess(List<PostModel> appartments) {
        AllAppartments = appartments;
        //Get All name
        List<String> PropertyNameselect = new ArrayList<>();

        //looping all appartments
        for(PostModel appart : appartments){
            PropertyNameselect.add(appart.getPropertyName());

            //create Adapter and set for spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,PropertyNameselect);
            spinner.setAdapter(adapter);

        }

    }

    @Override
    public void onFireBaseLoadFailed(String message) {

    }
}