package com.example.landlordproperty;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class AddTenantDetails extends AppCompatActivity implements TenantInterface {
    List<FlatsPostModel> allAppart;

    List<String> allAppartments;
    ArrayAdapter<String> arrayproperty;

    List<String> allflats;

    ArrayAdapter<String> arrayflats;
    TenantInterface tenantInterface;
    Spinner spinnerproperty, spinnerflats;


    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
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
        Intent intent = new Intent(AddTenantDetails.this, AddTenant.class);
        startActivity(intent);

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

        /*

        //spinner part
        try {

            spinnerproperty = findViewById(R.id.spinnerproperty);
            spinnerflats = findViewById(R.id.spinnerflat);


            allAppartments = new ArrayList<String>();
            allflats = new ArrayList<String>();

            mReference = FirebaseDatabase.getInstance().getReference();
            mReference.child("Flats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot app : snapshot.getChildren()) {
                        String name = app.child("PropertyName").getValue(String.class);
                        allAppartments.add(name);

                    }
                    arrayproperty = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, allAppartments);

                    spinnerproperty.setAdapter(arrayproperty);
                    //String value = String.valueOf(spinnerproperty);
                    //
                    //propertytv.setText(spinnerproperty.getSelectedItem().toString());
                    // Log.e("spinnerproperty", String.valueOf(propertytv));
                    spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            // propertytv.setText(spinnerproperty.getItemAtPosition(i).toString());
                            String result = arrayproperty.getItem(i);
                            mReference.equalTo(result).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot flats : snapshot.getChildren()) {
                                        String flat = flats.child("FlatNo").getValue(String.class);
                                        allflats.add(flat);
                                        //Log.e("allflats", allflats.toString());

                                    }
                                    arrayflats = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, allflats);
                                    spinnerflats.setAdapter(arrayflats);

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
*/

        //insertpart
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Tenants");

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
                // final  String propertyname = propertytv.getText().toString().trim();
                //final  String flatno = flatstv.getText().toString().trim();

                if (!fullname.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !rent.isEmpty() && !securitydeposit.isEmpty() || securitydeposit.isEmpty() && !startdate.isEmpty() && !enddate.isEmpty()) {
                    mprogress.setMessage("Uploading.......");
                    mprogress.show();
                    DatabaseReference tenants = mReference.push();
                    tenants.child("FullName").setValue(fullname);
                    tenants.child("Email").setValue(email);
                    tenants.child("Phone").setValue(phone);
                    tenants.child("RentAmount").setValue(rent);
                    tenants.child("SecurityDeposit").setValue(securitydeposit);
                    tenants.child("StartDate").setValue(startdate);
                    tenants.child("EndDate").setValue(enddate);

                    Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_LONG).show();
                    mprogress.dismiss();

                    //Intent intent = new Intent(AddTenantDetails.this, AddTenant.class);
                    //startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

                }

            }
        });



        //another Spinner


        //Spinner Part
        propertytv = findViewById(R.id.spinnerPropertyNametv);
        flatstv = findViewById(R.id.spinnerFlatnotv);
        spinnerproperty = findViewById(R.id.spinnerproperty);
        spinnerflats = findViewById(R.id.spinnerflat);
        spinnerproperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    final FlatsPostModel appartments = allAppart.get(i);
                    propertytv.setText(appartments.getPropertyName());

                //flatstv.setText(appartments.getFlatNo());

             /* spinnerflats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                       FlatsPostModel flatsPostModel = allAppart.get(i);
                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> adapterView) {

                   }
               });
              */
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mReference = FirebaseDatabase.getInstance().getReference("Flats");
        //interface
        tenantInterface = (TenantInterface) this;

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FlatsPostModel> appartmentslist = new ArrayList<>();
                for (DataSnapshot Appartmentssnapshot : snapshot.getChildren()) {
                    appartmentslist.add(Appartmentssnapshot.getValue(FlatsPostModel.class));
                }
                tenantInterface.onFireBaseLoadSuccess(appartmentslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ifirebaseloaddone.onFireBaseLoadFailed(databaseError.getmessage());

            }
        });


        //
    }
    //

    @Override
    public void onFireBaseLoadSuccess(List<FlatsPostModel> flats) {
        allAppart = flats;
        List<String> propertynameselect = new ArrayList<>();
        List<String> Dupicate = new ArrayList<>();
        List<String> flatno = new ArrayList<>();
        //looping all appartments
        for (FlatsPostModel appart : flats) {
            Dupicate.add(appart.getPropertyName());
            flatno.add(appart.getFlatNo());
            for (String apparement : Dupicate) {
                if (!propertynameselect.contains(apparement)) {
                    propertynameselect.add(apparement);



                    //create Adapter and set for spinner
                    arrayproperty = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertynameselect);
                    arrayproperty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerproperty.setAdapter(arrayproperty);
                    arrayflats = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, flatno);
                    spinnerflats.setAdapter(arrayflats);


                }
            }
        }
    }


            @Override
            public void onFireBaseLoadFailed (String message){

            }

        }



