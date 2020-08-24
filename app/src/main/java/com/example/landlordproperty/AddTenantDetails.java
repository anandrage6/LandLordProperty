package com.example.landlordproperty;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class AddTenantDetails extends AppCompatActivity {
    List<FlatsPostModel> allAppart;

    List<String> allAppartments;
    ArrayAdapter<String> arrayproperty;

    List<String> allflats;
    ArrayAdapter<String> arrayflats;

    TenantInterface tenantInterface;
    List<FlatsPostModel> flatsall;
    Spinner spinnerproperty, spinnerflats;


    FirebaseDatabase mDatabaseTenants;
    DatabaseReference mReferenceTenants;
    ProgressDialog mprogress;
    //input part
    EditText nameedt, emailedt, phoneedt, rentedt, securitydepositedt, startdateedt, enddateedt;
    TextView propertytv, flatstv;
    ;
    Button btnsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant_details);
        //set title on toolbar
        this.setTitle("Enter Your Tenant Details");


        //input part
        nameedt = findViewById(R.id.tenantFullNameEditText);
        emailedt = findViewById(R.id.tenantEmailEditText);
        phoneedt = findViewById(R.id.tenantPhoneNumberEditText);
        rentedt = findViewById(R.id.rentEditText);
        securitydepositedt = findViewById(R.id.securityEditText);
        startdateedt = findViewById(R.id.startdateEditText);
        enddateedt = findViewById(R.id.enddateEditText);
        propertytv = findViewById(R.id.spinnerPropertyNametv);
        flatstv = findViewById(R.id.spinnerFlatnotv);
        btnsave = findViewById(R.id.tenantbtn_save);
        spinnerproperty = findViewById(R.id.spinnerproperty);
        spinnerflats = findViewById(R.id.spinnerflat);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //startDate Picker
        startdateedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenantDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        startdateedt.setText(date);


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //EndDate Picker
        enddateedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenantDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        enddateedt.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });





        //spinner part
        try {

            spinnerproperty = findViewById(R.id.spinnerproperty);
            spinnerflats = findViewById(R.id.spinnerflat);


            allAppartments = new ArrayList<String>();
            allflats = new ArrayList<String>();

            mReferenceTenants = FirebaseDatabase.getInstance().getReference();
            mReferenceTenants.child("Flats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot app : snapshot.getChildren()) {
                        String name = app.child("PropertyName").getValue(String.class);
                        //String flats = app.child("PropertyName").getValue(String.class);
                        allAppartments.add(name);

                    }
                    arrayproperty = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, allAppartments);

                    spinnerproperty.setAdapter(arrayproperty);

                    spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                             propertytv.setText(spinnerproperty.getItemAtPosition(i).toString());
                            String result = arrayproperty.getItem(i);

                            mReferenceTenants.child("Flats").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot app : snapshot.getChildren()) {
                                        String flatNo = app.child("FlatNo").getValue(String.class);
                                        //String flats = app.child("PropertyName").getValue(String.class);
                                        allflats.add(flatNo);

                                    }
                                    arrayflats = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, allflats);

                                    spinnerflats.setAdapter(arrayflats);
                                    spinnerflats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            flatstv.setText(spinnerflats.getItemAtPosition(i).toString());
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Excepion error", e.getMessage());
        }


        //insertpart
        mDatabaseTenants = FirebaseDatabase.getInstance();
        mReferenceTenants = mDatabaseTenants.getReference().child("Tenants");

        //dialog progress
        mprogress = new ProgressDialog(this);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = nameedt.getText().toString().trim();
                final String email = emailedt.getText().toString().trim();
                final String phone = phoneedt.getText().toString().trim();
                final String rent = rentedt.getText().toString().trim();
                final String securitydeposit = securitydepositedt.getText().toString().trim();
                final String startdate = startdateedt.getText().toString().trim();
                final String enddate = enddateedt.getText().toString().trim();
                final String propertyname = propertytv.getText().toString().trim();
                final String flatno = flatstv.getText().toString().trim();

                if (!fullname.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !rent.isEmpty() && !startdate.isEmpty() && !enddate.isEmpty() && !propertyname.isEmpty() && (!securitydeposit.isEmpty() || securitydeposit.isEmpty())) {
                    mprogress.setMessage("Uploading.......");
                    mprogress.show();
                    DatabaseReference tenants = mReferenceTenants.push();
                    tenants.child("FullName").setValue(fullname);
                    tenants.child("Email").setValue(email);
                    tenants.child("Phone").setValue(phone);
                    tenants.child("RentAmount").setValue(rent);
                    tenants.child("SecurityDeposit").setValue(securitydeposit);
                    tenants.child("StartDate").setValue(startdate);
                    tenants.child("EndDate").setValue(enddate);
                    tenants.child("PropertyName").setValue(propertyname);
                    // tenants.child("FlatNo").setValue(flatno);

                    Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_LONG).show();
                    mprogress.dismiss();

                    Intent intent = new Intent(AddTenantDetails.this, AddTenant.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

                }

            }
        });


        /*
        //another Spinner

        spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FlatsPostModel flats = flatsall.get(i);
                propertytv.setText(flats.getPropertyName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mReferenceTenants = FirebaseDatabase.getInstance().getReference("Flats");
        //interface
        tenantInterface = this;

        mReferenceTenants.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FlatsPostModel> appartmentslist = new ArrayList<>();
                for(DataSnapshot Appartmentssnapshot : snapshot.getChildren()){
                    appartmentslist.add(Appartmentssnapshot.getValue(FlatsPostModel.class));
                }
                tenantInterface.onSuccess(appartmentslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//
    }
//
    @Override
    public void onSuccess(List<FlatsPostModel> flats) {
        flatsall = flats;
        List<String> listFlats = new ArrayList<>();

        //looping all appartments
        for(FlatsPostModel flt : flats){
            listFlats.add(flt.getPropertyName());

            //create Adapter and set for spinner
            ArrayAdapter<String> flatsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listFlats);
            spinnerproperty.setAdapter(flatsAdapter);
        }
    }

    @Override
    public void onFailed(String message) {

    }*/
    }
}




