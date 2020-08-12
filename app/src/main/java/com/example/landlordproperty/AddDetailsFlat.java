package com.example.landlordproperty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsFlat extends AppCompatActivity {
    Spinner spinner;
    DatabaseReference databaseReference;
    List<String> propertyNames;
    EditText propertynameselected;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details_flat);



        //Spinner Values
        try {


            spinner = findViewById(R.id.spinner);
            int selectionCurrent = spinner.getSelectedItemPosition();
            propertynameselected = findViewById(R.id.propertyselect);

            propertyNames = new ArrayList<>();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Appartments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childdatasnapshot : snapshot.getChildren()) {
                        String spinnerpropertyname = childdatasnapshot.child("PropertyName").getValue(String.class);
                        propertyNames.add(spinnerpropertyname);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddDetailsFlat.this, android.R.layout.simple_spinner_item, propertyNames);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            result = spinner.getSelectedItem().toString();
                            Log.e("Result", result);
                            propertynameselected.setText(result);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("AddFlats Error",e.getMessage());
        }


        Intent intent = new Intent(AddDetailsFlat.this, AddFlats.class);
        startActivity(intent);
    }
}