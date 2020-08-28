package com.example.landlordproperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDataApartments extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtProperty, edtOwner, edtAddress, edtCity, edtZipCode, edtDescription ;
    Spinner edtSpinner;
    Button btnSave;
    String cPropertyName, cOwnerName, cAddress, cCity, cZipCode, cDescription, cId, cImage;
    String cState;
    ImageButton imagebtn;
    private Uri imageUri = null;
    private static final int Gallery_Code = 1;
    //FirebaseDatabase mDatabase;
    DatabaseReference mReDatabaseference;
    StorageReference mStorageReference;
    String imageUrl;
    String t;

    ArrayList<String> states;
    ArrayAdapter<String> adapter;
    String selectedState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_apartments);
        edtProperty = findViewById(R.id.updatePropertyNameEditText);
        edtOwner = findViewById(R.id.updateOwnerNameEditText);
        edtAddress = findViewById(R.id.updateAddressEditText);
        edtCity = findViewById(R.id.updateCityEditText);
        edtSpinner = findViewById(R.id.updateStateEditText);
        edtZipCode = findViewById(R.id.updateZipCodeEditText);
        edtDescription = findViewById(R.id.updateDescriptionEditText);
        imagebtn = findViewById(R.id.updateImageButton);

        btnSave = findViewById(R.id.updateBtn);



        Intent intent = getIntent();
        cPropertyName = intent.getStringExtra("PropertyName");
        cOwnerName = intent.getStringExtra("OwnerName");
        cAddress = intent.getStringExtra("Address");
        cCity = intent.getStringExtra("City");
        cState = intent.getStringExtra("State");
        cZipCode = intent.getStringExtra("Zipcode");
        cDescription = intent.getStringExtra("Description");
        cId = intent.getStringExtra("Id");
        cImage = intent.getStringExtra("Image");

        edtProperty.setText(cPropertyName);
        edtOwner.setText(cOwnerName);
        edtAddress.setText(cAddress);
        edtCity.setText(cCity);
        edtZipCode.setText(cZipCode);
        edtDescription.setText(cDescription);
        //edtSpinner.setAdapter(adapter);

        Picasso.get().load(cImage).into(imagebtn);

        states = new ArrayList<String>();
        states.add(cState);
        List<String> allStates =  Arrays.asList(getResources().getStringArray(R.array.States));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);

        edtSpinner.setAdapter(adapter);
       // adapter.clear();
        adapter.addAll(allStates);
        edtSpinner.setOnItemSelectedListener(this);



        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String propertyName = edtProperty.getText().toString().trim();
                final String ownerName = edtOwner.getText().toString().trim();
                final String address = edtAddress.getText().toString().trim();
                final String city = edtCity.getText().toString().trim();
                final String state = selectedState;
                final String zipcode = edtZipCode.getText().toString().trim();
                final String description = edtDescription.getText().toString().trim();
                if (!propertyName.isEmpty() && !ownerName.isEmpty() && !address.isEmpty() && !city.isEmpty() && !state.equalsIgnoreCase("Select State") && !zipcode.isEmpty() && !description.isEmpty() || description.isEmpty() ) {

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("PropertyName", propertyName);
                    map.put("OwnerName",ownerName );
                    map.put("Address", address );
                    map.put("City", city );
                    map.put("State", state);
                    map.put("Zipcode", zipcode );
                    map.put("Description", description );
                    //map.put("Image", s);
                    Log.i("result image ", map.toString());
                    FirebaseDatabase.getInstance().getReference("Appartments").child(cId)
                            .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                             Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UpdateDataApartments.this, Appartments.class);
                            startActivity(i);
                        }

                    });
                }else{
                    Toast.makeText(getApplicationContext(), "UpdateInvalid", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedState = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}