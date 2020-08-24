package com.example.landlordproperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;

public class UpdateAppartments extends AppCompatActivity {
    EditText edtPropertyName, edtOwnerName, edtAddress, edtCity, edtState,  edtZipcode, edtDescription;
    Spinner updateState;
    Button btnUpdate;
    String PropertyName,ownerName,address,city,state,zipCode,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appartments);
        edtPropertyName = findViewById(R.id.updatePropertyNameEditText);
        edtOwnerName = findViewById(R.id.updateOwnerNameEditText);
        edtAddress = findViewById(R.id.updateAddressEditText);
        edtCity = findViewById(R.id.updateCityEditText);
        updateState = findViewById(R.id.updateStateEditText);
        edtZipcode = findViewById(R.id.updateZipCodeEditText);
        edtDescription = findViewById(R.id.updateDescriptionEditText);
        btnUpdate = findViewById(R.id.updateBtn);

        Intent intent = getIntent();
        PropertyName = intent.getStringExtra("PropertyName");
         ownerName = intent.getStringExtra("OwnerName");
         address = intent.getStringExtra("Address");
         city = intent.getStringExtra("City");
         state = intent.getStringExtra("State");
         zipCode = intent.getStringExtra("Zipcode");
         description = intent.getStringExtra("Description");

         edtPropertyName.setText(PropertyName);
        edtOwnerName.setText(ownerName);
        edtAddress.setText(address);
        edtCity.setText(city);
        //edtState.setText(state);
        edtZipcode.setText(zipCode);
        edtDescription.setText(description);
/*
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appartments").child(PropertyName);
                String uPropertyName,OwnerName,Address,City,State,ZipCode,Description;
                PropertyName = edtPropertyName.getText().toString();
                OwnerName = edtOwnerName.getText().toString();
                Address = edtAddress.getText().toString();
                City = edtCity.getText().toString();
                //uState = edtState.getText().toString().trim();
                ZipCode = edtZipcode.getText().toString();
                Description = edtDescription.getText().toString();
                PostModel postModel = new PostModel(PropertyName, OwnerName, Address, City, ZipCode, Description);
                databaseReference.setValue(postModel);
                Toast.makeText(UpdateAppartments.this, "Updated", Toast.LENGTH_LONG).show();

            }
        });
*/
    }
}