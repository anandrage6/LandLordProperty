package com.example.landlordproperty;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends FirebaseRecyclerAdapter <PostModel,RecyclerAdapter.ViewHolder>{

    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options) {
        super(options);
    }

    //getting and setting values in layoutcard

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PostModel model) {
        holder.tvpropertyname.setText(("Property Name : "+model.getPropertyName()));
        holder.tvownername.setText(("Owner Name : "+model.getOwnerName()));
        holder.tvdescription.setText(("Description : "+model.getDescription()));

        //To show image

        String imageUri = model.getImage();
        Picasso.get().load(imageUri).into(holder.Imageadd);


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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpropertyname = itemView.findViewById(R.id.propertynameTextView);
            tvownername = itemView.findViewById(R.id.ownernameTextView);
            tvdescription = itemView.findViewById(R.id.descriptionTextView);
            Imageadd = itemView.findViewById(R.id.imageButtonAdd);

        }
    }
}
