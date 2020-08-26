package com.example.landlordproperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

public class UpdateDataApartments extends AppCompatActivity {
    EditText edtProperty, edtOwner, edtAddress, edtCity, edtZipCode,edtDescription;
    Button btnSave;
String propertyName, ownerName, address, city, zipCode, description, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_apartments);
        edtProperty = findViewById(R.id.updatePropertyNameEditText);
        edtOwner = findViewById(R.id.updateOwnerNameEditText);
        edtAddress = findViewById(R.id.updateAddressEditText);
        edtCity =findViewById(R.id.updateCityEditText);
       edtZipCode = findViewById(R.id.updateZipCodeEditText);
       edtDescription = findViewById(R.id.updateDescriptionEditText);
       btnSave= findViewById(R.id.updateBtn);

        Intent intent = getIntent();
        propertyName = intent.getStringExtra("PropertyName");
        ownerName = intent.getStringExtra("OwnerName");
        address = intent.getStringExtra("Address");
        city = intent.getStringExtra("City");
        zipCode = intent.getStringExtra("Zipcode");
        description = intent.getStringExtra("Description");
        id = intent.getStringExtra("Id");

        edtProperty.setText(propertyName);
        edtOwner.setText(ownerName);
        edtAddress.setText(address);
        edtCity.setText(city);
        edtZipCode.setText(zipCode);
        edtDescription.setText(description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                DatabaseReference dbR = FirebaseDatabase.getInstance().getReference("Appartments").child(propertyName);
                String PropertyName;
                String OwnerName;
                String Address;
                String City;
               // String State;
                String Zipcode;
                String Description;
               String uname, uowner, uadress, ucity, uzipcode, udescription;
                PropertyName = edtProperty.getText().toString();
                OwnerName = edtOwner.getText().toString();
                Address = edtAddress.getText().toString();
                City = edtCity.getText().toString();
                Zipcode = edtZipCode.getText().toString();
                Description = edtDescription.getText().toString();
                PostModel model = new PostModel(PropertyName,OwnerName,Address,City,Zipcode,Description);
                dbR.setValue(model);
*/

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("PropertyName", edtProperty.getText().toString());
                map.put("OwnerName", edtOwner.getText().toString());
                map.put("Address",edtAddress.getText().toString());
                map.put("City", edtCity.getText().toString());
                //map.put("State",state.setAdapter(adapter));
                map.put("Zipcode", edtZipCode.getText().toString());
                map.put("Description", edtDescription.getText().toString());
                FirebaseDatabase.getInstance().getReference("Appartments").child(id)
                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



    }
}