package com.example.landlordproperty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateDataApartments extends AppCompatActivity {
    EditText edtProperty, edtOwner, edtAddress, edtCity, edtZipCode, edtDescription;
    Button btnSave;
    String propertyName, ownerName, address, city, zipCode, description, id, image;
    ImageButton imagebtn;
    private Uri imageUri = null;
    private static final int Gallery_Code = 1;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    StorageReference mStorage;
    String imageUrl;
    String t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_apartments);
        edtProperty = findViewById(R.id.updatePropertyNameEditText);
        edtOwner = findViewById(R.id.updateOwnerNameEditText);
        edtAddress = findViewById(R.id.updateAddressEditText);
        edtCity = findViewById(R.id.updateCityEditText);
        edtZipCode = findViewById(R.id.updateZipCodeEditText);
        edtDescription = findViewById(R.id.updateDescriptionEditText);
        imagebtn = findViewById(R.id.updateImageButton);

        btnSave = findViewById(R.id.updateBtn);


        Intent intent = getIntent();
        propertyName = intent.getStringExtra("PropertyName");
        ownerName = intent.getStringExtra("OwnerName");
        address = intent.getStringExtra("Address");
        city = intent.getStringExtra("City");
        zipCode = intent.getStringExtra("Zipcode");
        description = intent.getStringExtra("Description");
        id = intent.getStringExtra("Id");
        image = intent.getStringExtra("Image");

        edtProperty.setText(propertyName);
        edtOwner.setText(ownerName);
        edtAddress.setText(address);
        edtCity.setText(city);
        edtZipCode.setText(zipCode);
        edtDescription.setText(description);
        Picasso.get().load(image).into(imagebtn);


        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==Gallery_Code && resultCode==RESULT_OK){
                imageUri = data.getData();
                imagebtn.setImageURI(imageUri);

            }

            btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*StorageReference mPicRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                    mPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            StorageReference filepath = mStorage.child("image_Appartments/");
                            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                          t = task.getResult().toString();
                                        }
                                    });
                                }
                            });

                        }
                    });*/
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("PropertyName", edtProperty.getText().toString());
                map.put("OwnerName", edtOwner.getText().toString());
                map.put("Address", edtAddress.getText().toString());
                map.put("City", edtCity.getText().toString());
                //map.put("State",state.setAdapter(adapter));
                map.put("Zipcode", edtZipCode.getText().toString());
                map.put("Description", edtDescription.getText().toString());
                //map.put("Image", t);
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