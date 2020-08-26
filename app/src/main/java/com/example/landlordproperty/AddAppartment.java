package com.example.landlordproperty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddAppartment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //instance variables
    ImageButton imageBtn;
    EditText edtpropertyName, edtownerName, edtaddress, edtcity,  edtzipcode, edtdescription;
    Spinner statespinn;
    Button btnsave;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    StorageReference mStorage;
    private static final int Gallery_Code=1;
    private Uri imageUri=null;
    ProgressDialog mprogress;
    String textstate;

    //Awesome Validation
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appartment);
        //set title on toolbar
        this.setTitle("Enter Your Property Details");


        //finding id's
        imageBtn = findViewById(R.id.imageButton);
        edtpropertyName = findViewById(R.id.propertyNameEditText);
        edtownerName = findViewById(R.id.ownerNameEditText);
        edtaddress = findViewById(R.id.addressEditText);
        edtcity = findViewById(R.id.cityEditText);

        edtzipcode = findViewById(R.id.zipCodeEditText);
        edtdescription = findViewById(R.id.descriptionEditText);
        btnsave = findViewById(R.id.saveBtn);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Appartments");
        mStorage = FirebaseStorage.getInstance().getReference();
        //Awesome
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(AddAppartment.this, R.id.propertyNameEditText, "[a-zA-Z\\s]+", R.string.err_propertyName);
        awesomeValidation.addValidation(AddAppartment.this, R.id.ownerNameEditText, "[a-zA-Z\\s]+", R.string.err_ownerName);
        awesomeValidation.addValidation(AddAppartment.this, R.id.cityEditText, "[a-zA-Z\\s]+", R.string.err_city);
        awesomeValidation.addValidation(AddAppartment.this, R.id.zipCodeEditText, "^[1-9][0-9]{5}$", R.string.err_zipCode);


        //awesomeValidation.addValidation(AddAppartment.this, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
        //awesomeValidation.addValidation(activity, R.id.edt_email, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);



        //spinner
        statespinn = findViewById(R.id.stateEditText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.States, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespinn.setAdapter(adapter);
        statespinn.setOnItemSelectedListener(this);

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
                if(awesomeValidation.validate()) {
                    final String propertyName = edtpropertyName.getText().toString().trim();
                    final String ownerName = edtownerName.getText().toString().trim();
                    final String address = edtaddress.getText().toString().trim();
                    final String city = edtcity.getText().toString().trim();
                    // final String state = text.getText().toString().trim();
                    final String zipcode = edtzipcode.getText().toString().trim();
                    final String description = edtdescription.getText().toString().trim();

                    if (!propertyName.isEmpty() && !ownerName.isEmpty() && !address.isEmpty() && !city.isEmpty() && !zipcode.isEmpty() && imageUri != null && !description.isEmpty() || description.isEmpty() && !textstate.equalsIgnoreCase("Select State")) {

                        mprogress.setMessage("Uploading.......");
                        mprogress.show();

                        StorageReference filepath = mStorage.child("image_Appartments").child(imageUri.getLastPathSegment());
                        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {

                                        String t = task.getResult().toString();
                                        DatabaseReference newPost = mReference.push();
                                        newPost.child("Id").setValue(newPost.getKey());
                                        newPost.child("PropertyName").setValue(propertyName);
                                        newPost.child("OwnerName").setValue(ownerName);
                                        newPost.child("Address").setValue(address);
                                        newPost.child("City").setValue(city);
                                        newPost.child("State").setValue(textstate);
                                        newPost.child("Zipcode").setValue(zipcode);
                                        newPost.child("Description").setValue(description);
                                        newPost.child("Image").setValue(task.getResult().toString());
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                                        mprogress.dismiss();

                                        Intent intent = new Intent(AddAppartment.this, Appartments.class);
                                        startActivity(intent);


                                    }
                                });

                            }
                        });

                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();

                }



            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         textstate = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
        //Log.e("text",text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}