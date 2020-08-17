package com.example.landlordproperty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class FlatsRecyclerView extends FirebaseRecyclerAdapter<FlatsPostModel, FlatsRecyclerView.ViewHolder> {


private Context context;
    public FlatsRecyclerView(@NonNull FirebaseRecyclerOptions<FlatsPostModel> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull FlatsPostModel model) {
        holder.propertynametv.setText(("Property Name : "+model.getPropertyName()));
        holder.addresstv.setText(("Address : "+model.getAddress()));
        holder.flatnotv.setText(("Flat.No : "+model.getFlatNo()));

        try {

            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are You Want To Delete Sure?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("Flats")
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

        //update
        Button btndelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //flats
            propertynametv = itemView.findViewById(R.id.propertynameViewText);
            flatnotv = itemView.findViewById(R.id.flatnoViewText);
            addresstv = itemView.findViewById(R.id.addressViewText);

            //update
            btndelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
