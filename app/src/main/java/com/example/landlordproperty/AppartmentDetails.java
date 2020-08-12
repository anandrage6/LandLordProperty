package com.example.landlordproperty;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AppartmentDetails extends AppCompatActivity {
 TextView propertynametv,ownernametv, descriptiontv , totalAddresstv;
 ImageView imagetv;
 DatabaseReference reference;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appartment_details);


        //Appartment full Details


        propertynametv = findViewById(R.id.propertynameTextViewDetails);
        ownernametv = findViewById(R.id.ownernameTextViewDetails);
        totalAddresstv = findViewById(R.id. addressTextViewDetails);
        descriptiontv = findViewById(R.id.descriptionTextViewDetails);
        imagetv = findViewById(R.id.imageViewDetails);
        reference = FirebaseDatabase.getInstance().getReference().child("Appartments");

        String details = getIntent().getStringExtra("details");
        reference.child(details).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    String PropertyName = snapshot.child("PropertyName").getValue().toString();
                    String OwnerName = snapshot.child("OwnerName").getValue().toString();
                    String Description = snapshot.child("Description").getValue().toString();
                    String Address = snapshot.child("Address").getValue().toString();
                    String City = snapshot.child("City").getValue().toString();
                    String State = snapshot.child("State").getValue().toString();
                    String ZipCode = snapshot.child("Zipcode").getValue().toString();
                    String Image = snapshot.child("Image").getValue().toString();
                    String TotalAddress = Address + ", " + City + " " + State + "-" + ZipCode;

                    //set values into text view
                    Picasso.get().load(Image).into(imagetv);
                    propertynametv.setText("PropertyName : " + PropertyName);
                    ownernametv.setText("Owner Name : " + OwnerName);
                    descriptiontv.setText("Description : " + Description);

                    totalAddresstv.setText("Address :" + TotalAddress);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("Error",e.getMessage());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}