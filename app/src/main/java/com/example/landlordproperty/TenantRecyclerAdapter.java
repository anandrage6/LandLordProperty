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

public class TenantRecyclerAdapter extends FirebaseRecyclerAdapter<TenantPostModel, TenantRecyclerAdapter.ViewHolder> {

    private Context context;

    public TenantRecyclerAdapter(@NonNull FirebaseRecyclerOptions<TenantPostModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull TenantPostModel model) {
        holder.fullName.setText(("FullName : "+model.getFullName()));
       holder.propertyName.setText(("Property Name : "+model.getPropertyName()));
       holder.flatNo.setText(("Flat.No : "+model.getFlatNo()));
       holder.phoneNumber.setText(("PhoneNumber : "+model.getPhone()));

        try {

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are You Want To Delete Sure?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("Tenants")
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
                .inflate(R.layout.add_tenants_design_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName, propertyName, flatNo, phoneNumber;
        Button btnUpdate,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.tenantNameTextView);
            propertyName = itemView.findViewById(R.id.tenantProprtyNameTextView);
            flatNo = itemView.findViewById(R.id.tenantFlatNoTextView);
            phoneNumber = itemView.findViewById(R.id.tenantPhoneNmuberTextView);
            btnDelete = itemView.findViewById(R.id.tenantBtn_delete);


        }
    }
}
