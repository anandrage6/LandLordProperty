package com.example.landlordproperty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddAppartment extends AppCompatActivity {

    //instance variables
    ImageButton imageBtn;
    EditText edtfullname,edtphonenumber,edtflatno,edtaddress,edtcity,edtstate,edtzipcode,edtdescription;
    Button btnsave;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    StorageReference mStorage;
    private static final int Gallery_Code=1;
    private Uri imageUri=null;
    ProgressDialog mprogress;
    String MobilePattern = "[0-9]{10}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appartment);

        //finding id's
        imageBtn = findViewById(R.id.imageButton);
        edtfullname = findViewById(R.id.fullNameEditText);
        edtphonenumber = findViewById(R.id.phoneNumberEditText);
        //edtflatno = findViewById(R.id.flatNumberEditText);
        edtaddress = findViewById(R.id.addressEditText);
        edtcity = findViewById(R.id.cityEditText);
        edtstate = findViewById(R.id.stateEditText);
        edtzipcode = findViewById(R.id.zipCodeEditText);
        edtdescription = findViewById(R.id.descriptionEditText);
        btnsave = findViewById(R.id.saveBtn);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Appartments");
        mStorage = FirebaseStorage.getInstance().getReference();

        //dialog progress
        mprogress = new ProgressDialog(this);

        //to get image from phone
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Code && resultCode==RESULT_OK){
            imageUri = data.getData();
            imageBtn.setImageURI(imageUri);
        }

        //to save data into firebase by clicking save button
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = edtfullname.getText().toString().trim();
                //if(edtphonenumber.length() == 10) {
                    final String phonenumber = (edtphonenumber.getText().toString().trim());
                //}else {
                    //Toast.makeText(getApplicationContext(), "please enter valid Number",Toast.LENGTH_LONG).show();
                //}
                //final String flatno = edtflatno.getText().toString().trim();
                final String address = edtaddress.getText().toString().trim();
                final String city = edtcity.getText().toString().trim();
                final String state = edtstate.getText().toString().trim();
                final String zipcode = edtzipcode.getText().toString().trim();
                final String description = edtdescription.getText().toString().trim();

                if(!fullname.isEmpty() && !phonenumber.isEmpty()  && !address.isEmpty() && !city.isEmpty() &&!state.isEmpty() && !zipcode.isEmpty() && imageUri !=null && !description.isEmpty()||description.isEmpty()){

                    mprogress.setMessage("Uploading.......");
                    mprogress.show();

                    StorageReference filepath = mStorage.child("image_Appartments").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    String t =  task.getResult().toString();
                                    DatabaseReference  newPost = mReference.push();
                                    newPost.child("FullName").setValue(fullname);
                                    newPost.child("PhoneNumber").setValue(phonenumber);
                                    //newPost.child("FlatNo").setValue(flatno);
                                    newPost.child("Address").setValue(address);
                                    newPost.child("City").setValue(city);
                                    newPost.child("State").setValue(state);
                                    newPost.child("Zipcode").setValue(zipcode);
                                    newPost.child("Description").setValue(description);
                                    newPost.child("Image").setValue(task.getResult().toString());

                                    mprogress.dismiss();

                                    Intent intent = new Intent(AddAppartment.this, Appartments.class);
                                    startActivity(intent);


                                }
                            });

                        }
                    });

                }
            }
        });
    }
}