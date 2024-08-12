package com.example.securityhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

public class AdaptadorCasas extends RecyclerView.Adapter<AdaptadorCasas.CasaViewHolder> {
    List<Casas> casas;
    Context context;

    public AdaptadorCasas(List<Casas> casas){this.casas=casas;}

    @NonNull
    @Override
    public CasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcasa,parent,false);
        return new CasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CasaViewHolder holder, int position) {
        holder.bind(casas.get(position));
    }
    @Override
    public int getItemCount() {
        return casas.size();
    }

    public class CasaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView nomCasa, etdescription;
        String nameCasa;
        int Id;
        Button btneliminar;
        Button btnedit;
        public CasaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomCasa= itemView.findViewById(R.id.tvcasa);
            etdescription= itemView.findViewById(R.id.etDescripcion);
            btneliminar= itemView.findViewById(R.id.btnEliminar);
            btnedit= itemView.findViewById(R.id.btnEditar);
            btnedit.setVisibility(View.GONE);
            btneliminar.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        public void bind(Casas c) {
            nomCasa.setText(c.getNomCasa());
            etdescription.setText(c.getDescription());
            this.nameCasa= c.getNomCasa();
            this.Id = c.getId();
            btneliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    AlertDialog.Builder confirmacion = new AlertDialog.Builder(itemView.getContext());
                    confirmacion.setMessage("¿Estas seguro que quieres eliminar "+c.getNomCasa()+" ?");
                    confirmacion.setTitle("Confirmacion eliminar");
                    confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url="http://54.163.22.51/api/v1/groups"+"?id="+Id;

                            JsonObjectRequest eliminar = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(itemView.getContext(), nameCasa+" Eliminada", Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(itemView.getContext(), GroupStudentsActivity.class );
                                    view.getContext().startActivity(intent);
                                    ((Activity)itemView.getContext()).finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            SingletonRequest.getInstance(context).addToRequestQue(eliminar);
                        }
                    });
                    confirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = confirmacion.create();
                    dialog.show();
                }
            });


            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), EditarActivity.class);
                    intent.putExtra("name", c.getNomCasa());
                    intent.putExtra("description", c.getDescription());
                    intent.putExtra("id",String.valueOf(c.getId()));
                    view.getContext().startActivity(intent);
                    //((Activity)itemView.getContext()).finish();
                }
            });
        }
        @Override
        public void onClick(View view) {
            Intent vista = new Intent(view.getContext(), MateriasActivity.class);
            vista.putExtra("nombre", this.nameCasa);
            vista.putExtra("id",String.valueOf(this.Id));
            view.getContext().startActivity(vista);


        }
    }
}
