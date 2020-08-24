package com.example.landlordproperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddPayment extends AppCompatActivity {
    Spinner tenantNameSpin, tenantProprtyNameSpin, tenantFlatNoSpin;
    TextView tenantNametv;
    EditText amountEt, noteEt, nameEt, upiIdEt;
    Button send;
    List<String> alltenants;
    ArrayAdapter<String> arrayTenants;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ProgressDialog mprogress;


    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        //set title on toolbar
        this.setTitle("Enter Your Payments Details");

        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi_id);


        //spinner part
        try {

            tenantNameSpin = findViewById(R.id.tenantSpinnerName);
            tenantNametv = findViewById(R.id.tenantSpinnerNametv);
            //spinnerflats = findViewById(R.id.spinnerflat);


            alltenants = new ArrayList<String>();
            //allflats = new ArrayList<String>();

            mReference = FirebaseDatabase.getInstance().getReference();
            mReference.child("Tenants").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot app : snapshot.getChildren()) {
                        String name = app.child("FullName").getValue(String.class);
                        //String flats = app.child("PropertyName").getValue(String.class);
                        alltenants.add(name);

                    }
                    arrayTenants = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, alltenants);

                    tenantNameSpin.setAdapter(arrayTenants);
                    //String value = String.valueOf(spinnerproperty);
                    //
                    //propertytv.setText(spinnerproperty.getSelectedItem().toString());
                    // Log.e("spinnerproperty", String.valueOf(propertytv));
                    tenantNameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            tenantNametv.setText(tenantNameSpin.getItemAtPosition(i).toString());
                            //String result = arrayproperty.getItem(i);


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



        // UPI Payment part
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();
                payUsingUpi(amount, upiId, name, note);
            }
        });
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")

                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(AddPayment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(AddPayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(AddPayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                //String transactionId = approvalRefNo;


                //insertpart
                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference().child("Payments");

                //dialog progress
                mprogress = new ProgressDialog(this);

                final String tenantFullName = tenantNametv.getText().toString().trim();
                //final String tenantPropertyName = nameedt.getText().toString().trim();
                //final String tenantFlatNo = nameedt.getText().toString().trim();
                final String tenantAmount = amountEt.getText().toString().trim();
                final String upiId = upiIdEt.getText().toString().trim();
                final String name = nameEt.getText().toString().trim();
                final String note = noteEt.getText().toString().trim();
                if( !tenantFullName.isEmpty() && !tenantAmount.isEmpty() && !upiId.isEmpty() && !name.isEmpty() && (!note.isEmpty() || note.isEmpty())){
                    mprogress.setMessage("Uploading.......");
                    mprogress.show();

                    DatabaseReference payments = mReference.push();
                    payments.child("TenantName").setValue(tenantFullName);
                    payments.child("TenantAmount").setValue(tenantAmount);
                    payments.child("UPIID").setValue(upiId);
                    payments.child("Name").setValue(name);
                    payments.child("Note").setValue(note);
                    payments.child("TransactionId").setValue(approvalRefNo);
                    Toast.makeText(getApplicationContext(),"Successfully Saved", Toast.LENGTH_LONG).show();

                    mprogress.dismiss();


                }else{
                    Toast.makeText(getApplicationContext(),"Error Ocurred", Toast.LENGTH_LONG).show();
                }
              //
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(AddPayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddPayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddPayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
            && netInfo.isConnectedOrConnecting()
            && netInfo.isAvailable()){
                return true;
            }
        }
        return false;

    }
}




