package com.example.landlordproperty;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter extends FirebaseRecyclerAdapter <PostModel,RecyclerAdapter.ViewHolder>{


private EditText name = null;
private EditText owner = null;
private EditText description = null;
private Button btn = null;

private  Context context;
    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options, Context context) {
        super(options);
        this.context=context;

    }

    //getting and setting values in layoutcard

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final PostModel model) {
        holder.tvpropertyname.setText(("Property Name : "+model.getPropertyName()));
        holder.tvownername.setText(("Owner Name : "+model.getOwnerName()));
        holder.tvdescription.setText(("Description : "+model.getDescription()));

        //To show image

        String imageUri = model.getImage();
        Picasso.get().load(imageUri).into(holder.Imageadd);

        //set onClickListner to details

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
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Delete Error",e.getMessage());
        }

        //update OnclickListner
       holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.activity_content))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();



                View holderView = dialog.getHeaderView();

                try {
                    name = (EditText) holderView.findViewById(R.id.updateProperty);
                    owner =  (EditText) holderView.findViewById(R.id.updateowner);
                    description = (EditText)  holderView.findViewById(R.id.updatedescription);
                    btn = (Button) holderView.findViewById(R.id.btn_update);


                    name.setText(model.getPropertyName());
                    owner.setText(model.getOwnerName());
                    description.setText(model.getDescription());


                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("PropertyName", name.getText().toString());
                            map.put("OwnerName", owner.getText().toString());
                            map.put("Description", description.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Appartments")
                                    .child(getRef(position).getKey())
                                    .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
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

    }


    //viewholder to pass data to appartments with extra activity_layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
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
