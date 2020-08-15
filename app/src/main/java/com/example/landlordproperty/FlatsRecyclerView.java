package com.example.landlordproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FlatsRecyclerView extends FirebaseRecyclerAdapter<FlatsPostModel, FlatsRecyclerView.ViewHolder> {


private Context context;
    public FlatsRecyclerView(@NonNull FirebaseRecyclerOptions<FlatsPostModel> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FlatsPostModel model) {
        holder.propertynametv.setText(("Property Name : "+model.getPropertyName()));
        holder.addresstv.setText(("Address : "+model.getAddress()));
        holder.flatnotv.setText(("Flat.No : "+model.getFlatNo()));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_add_flats_recycler_view, parent, false);
        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView propertynametv,addresstv,flatnotv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //flats
            propertynametv = itemView.findViewById(R.id.propertynameViewText);
            flatnotv = itemView.findViewById(R.id.flatnoViewText);
            addresstv = itemView.findViewById(R.id.addressViewText);
        }
    }
}
