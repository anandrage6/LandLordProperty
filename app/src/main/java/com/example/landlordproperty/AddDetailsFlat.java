package com.example.landlordproperty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    //spinner variables
    Spinner spinner;
    DatabaseReference databaseReference;
    IfirebaseLoadDone ifirebaseloaddone;
    List<PostModel> AllAppartments;
    TextView propertynametv, addresstv, citytv, statetv, zipcodetv;
    EditText flatnoedttxt;
    Button btnsave;
    Boolean isFirstTimeClick = true;
    // insert data
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details_flat);
        //set title on toolbar
        this.setTitle("Enter Your Flat Details");

        //id flat.no
        flatnoedttxt = findViewById(R.id.flat_noEditText);
        btnsave = findViewById(R.id.saveButton);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Flats");

        //dialog progress
        mprogress = new ProgressDialog(this);

        //spinner

        propertynametv = findViewById(R.id.propertySelect);
        addresstv = findViewById(R.id.addressSelect);
        citytv = findViewById(R.id.citySelect);
        statetv = findViewById(R.id.stateSelect);
        zipcodetv = findViewById(R.id.zipcodeSelect);


        //insert data

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String flatpropertyname = propertynametv.getText().toString().trim();
                final String flataddress = addresstv.getText().toString().trim();
                final String flatcity = citytv.getText().toString().trim();
                final String flatstate = statetv.getText().toString().trim();
                final String flatzipcode = zipcodetv.getText().toString().trim();
                final String flatflatno = flatnoedttxt.getText().toString().trim();

                if(!flatpropertyname.isEmpty() && !flataddress.isEmpty() && !flatcity.isEmpty() && !flatstate.isEmpty() && !flatzipcode.isEmpty() && !flatflatno.isEmpty()){
                    mprogress.setMessage("Uploading.......");
                    mprogress.show();

                    DatabaseReference  flats = mReference.push();
                    flats.child("PropertyName").setValue(flatpropertyname);
                    flats.child("Address").setValue(flataddress);
                    flats.child("City").setValue(flatcity);
                    flats.child("State").setValue(flatstate);
                    flats.child("ZipPostalCode").setValue(flatzipcode);
                    flats.child("FlatNo").setValue(flatflatno);
                    Toast.makeText(getApplicationContext(),"Successfully Saved", Toast.LENGTH_LONG).show();

                    mprogress.dismiss();

                    Intent intent = new Intent(AddDetailsFlat.this, AddFlats.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(getApplicationContext(),"Error Ocurred", Toast.LENGTH_LONG).show();

                }

            }
        });

        //spinner part

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //fix first item Click

                    PostModel app = AllAppartments.get(i);
                    propertynametv.setText(app.getPropertyName());
                    addresstv.setText(app.getAddress());
                    citytv.setText(app.getCity());
                    statetv.setText(app.getState());
                    zipcodetv.setText(app.getZipcode());

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