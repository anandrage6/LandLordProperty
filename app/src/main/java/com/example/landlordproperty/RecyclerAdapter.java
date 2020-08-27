package com.example.landlordproperty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class RecyclerAdapter extends FirebaseRecyclerAdapter <PostModel,RecyclerAdapter.ViewHolder>{

/*
    //Update Part
private EditText name = null;
private EditText owner = null;
private EditText description = null;
private Button btn = null;

*/
private Uri imageUri=null;
private static final int Gallery_Code=1;
StorageReference mStorage;
private  Context context;
    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options, Context context) {
        super(options);
        this.context=context;

    }

    //getting and setting values in layoutcard

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final PostModel model) {
        // To Show Appartment Details
        holder.tvpropertyname.setText(("Property Name : "+model.getPropertyName()));
        holder.tvownername.setText(("Owner Name : "+model.getOwnerName()));
        holder.tvdescription.setText(("Description : "+model.getDescription()));

        //To show image

        String imageUri = model.getImage();
        Picasso.get().load(imageUri).into(holder.Imageadd);


        //set onClickListner to Appartment full details

        try {


            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AppartmentDetails.class);
                    //Toast.makeText(view.getContext(), "Item clicked at " +position, Toast.LENGTH_SHORT).show();
                    i.putExtra("details", getRef(position).getKey());
                    context.startActivity(i);

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("LinearLayout Error",e.getMessage());
        }

        //Delete OnclickListner
        try {

            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are You Want To Delete Sure?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("Appartments")
                                            .child(getRef(position).getKey())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog dialog =builder.create();
                    dialog.setTitle("Delete");
                    dialog.show();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Delete Error",e.getMessage());
        }

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String cProperty = getItem(position).getPropertyName();
                final String cOwner = getItem(position).getOwnerName();
                final String cAddress = getItem(position).getAddress();
                final String cCity = getItem(position).getCity();
                final String cZipcode = getItem(position).getZipcode();
                final String cDescription = getItem(position).getDescription();
                final String cImage = getItem(position).getImage();
                final String cId = getItem(position).getId();



                Intent i = new Intent(view.getContext(),UpdateDataApartments.class);
                i.putExtra("PropertyName",cProperty);
                i.putExtra("OwnerName",cOwner);
                i.putExtra("Address",cAddress);
                i.putExtra("City",cCity);
                i.putExtra("Zipcode",cZipcode);
                i.putExtra("Description",cDescription);
                i.putExtra("Image",cImage);
                i.putExtra("Id", cId);
                view.getContext().startActivity(i);
            }
        });
        /*
        //update OnclickListner
       holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(0,0,0,0)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_apartment_content))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();




                View myView = dialog.getHolderView();


                try {
                   final EditText name = (EditText) myView.findViewById(R.id.updatePropertyNameEditText);
                  final EditText owner =  (EditText) myView.findViewById(R.id.updateOwnerNameEditText);
                   final EditText address = (EditText) myView.findViewById(R.id.updateAddressEditText);
                    final EditText city = (EditText) myView.findViewById(R.id.updateCityEditText);
                    //final Spinner state = (Spinner) myView.findViewById(R.id.updateStateEditText);

                    final EditText zipcode = (EditText) myView.findViewById(R.id.updateZipCodeEditText);
                    final EditText description = (EditText)  myView.findViewById(R.id.updateDescriptionEditText);

                    final ImageButton image =  myView.findViewById(R.id.updateImageButton);
                    //final EditText imagetext = myView.findViewById(R.id.updateImageButton);

                   Button btnSave = (Button) myView.findViewById(R.id.updateBtn);

                    String imageUrl = model.getImage();
                    Picasso.get().load(imageUrl).into(image);


                    name.setText(model.getPropertyName());
                    owner.setText(model.getOwnerName());
                    address.setText(model.getAddress());
                    city.setText(model.getCity());
                    //state
                    zipcode.setText(model.getZipcode());
                    description.setText(model.getDescription());
                    //image.setImageURI(Uri.parse(imageUrl));

                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            ((Activity) context).startActivityForResult(intent,Gallery_Code);
                        }
                    });



                    StorageReference filepath = mStorage.child("image_Appartments").child(getRef(position).getKey());





                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("PropertyName", name.getText().toString());
                            map.put("OwnerName", owner.getText().toString());
                            map.put("Address",address.getText().toString());
                            map.put("City", city.getText().toString());
                            //map.put("State",state.setAdapter(adapter));
                            map.put("Zipcode", zipcode.getText().toString());
                            map.put("Description", description.getText().toString());
                           //map.put("Image",);


                            FirebaseDatabase.getInstance().getReference().child("Appartments")
                                    .child(getRef(position).getKey())
                                    .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();


                                }
                            });
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("Update Error",e.getMessage());
                }

                dialog.show();



            }
        });


         */


    }




    //viewholder to pass data to appartments with extra activity_layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.design_row_for_recyclerview, parent, false);
            return new ViewHolder(view);

    }

    //based on id's finding values and viewing items

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tvpropertyname,tvownername,tvaddress,tvcity,tvstate,tvzipcode,tvdescription;
        ImageView Imageadd;
        //linear layout to get full details
        LinearLayout linearLayout;
        // update button
        Button btnupdate;
        //Delete Button
        Button btndelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpropertyname = itemView.findViewById(R.id.propertynameTextView);
            tvownername = itemView.findViewById(R.id.ownernameTextView);
            tvdescription = itemView.findViewById(R.id.descriptionTextView);
            Imageadd = itemView.findViewById(R.id.imageButtonAdd);
           //tvaddress = itemView.findViewById(R.id.addressTextView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            btnupdate = itemView.findViewById(R.id.btn_update);
            btndelete = itemView.findViewById(R.id.btn_delete);



        }

        }
    }




