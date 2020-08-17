package com.example.landlordproperty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class AddTenantDetails extends AppCompatActivity  implements TenantInterface {
    List<FlatsPostModel> allAppartments;
    ArrayAdapter <String> arrayproperty;

    ArrayAdapter <String> arrayflats;
    TenantInterface tenantInterface;
    Spinner spinnerproperty, spinnerflats;
    TextView propertytv, flatstv;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant_details);
        Intent intent = new Intent(AddTenantDetails.this, AddTenant.class);
        startActivity(intent);

        /*
        spinnerproperty = findViewById(R.id.spinnerproperty);
        spinnerflats = findViewById(R.id.spinnerflat);
        propertytv = findViewById(R.id.spinnerPropertyNametv);
        flatstv = findViewById(R.id.spinnerFlatnotv);

        allAppartments = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Flats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot app : snapshot.getChildren()){
                    String name = app.child("PropertyName").getValue(String.class);
                    allAppartments.add(name);

                }
                arrayproperty = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,allAppartments);

                spinnerproperty.setAdapter(arrayproperty);
                //String value = String.valueOf(spinnerproperty);
                //
                //propertytv.setText(spinnerproperty.getSelectedItem().toString());
               // Log.e("spinnerproperty", String.valueOf(propertytv));
                spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        propertytv.setText(spinnerproperty.getItemAtPosition(i).toString());

                        spinnerproperty
                    }



                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/






        //Spinner Part
        propertytv = findViewById(R.id.spinnerPropertyNametv);
        flatstv = findViewById(R.id.spinnerFlatnotv);
        spinnerproperty = findViewById(R.id.spinnerproperty);
        spinnerflats = findViewById(R.id.spinnerflat);
        spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FlatsPostModel appartments = allAppartments.get(i);
                propertytv.setText(appartments.getPropertyName());

                spinnerflats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        FlatsPostModel flats = allAppartments.get(i);
                        flatstv.setText(flats.getFlatNo());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Flats");
        //interface
        tenantInterface = this;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FlatsPostModel> appartmentslist = new ArrayList<>();
                for(DataSnapshot Appartmentssnapshot : snapshot.getChildren()){
                    appartmentslist.add(Appartmentssnapshot.getValue(FlatsPostModel.class));
                }
                tenantInterface.onFireBaseLoadSuccess(appartmentslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ifirebaseloaddone.onFireBaseLoadFailed(databaseError.getmessage());

            }
        });


    }

    @Override
    public void onFireBaseLoadSuccess(List<FlatsPostModel> flats) {
        allAppartments = flats;
        List<String> propertynameselect = new ArrayList<>();
        List<String> Dupicate = new ArrayList<>();
        List<String> flatno = new ArrayList<>();
        //looping all appartments
        for (FlatsPostModel appart : flats) {
            Dupicate.add(appart.getPropertyName());
            flatno.add(appart.getFlatNo());

            for (String apparement : Dupicate) {
                if (!propertynameselect.contains(apparement)){
                    propertynameselect.add(apparement);


                //create Adapter and set for spinner
                arrayproperty = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertynameselect);
                spinnerproperty.setAdapter(arrayproperty);
                arrayflats = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, flatno);
                spinnerflats.setAdapter(arrayflats);
            }
        }

        }
    }

    @Override
    public void onFireBaseLoadFailed(String message) {

    }

}