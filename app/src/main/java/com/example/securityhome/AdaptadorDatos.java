package com.example.securityhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorDatos extends RecyclerView.Adapter<AdaptadorDatos.DatosViewHolder> {
    List<Datos> datos;

    public AdaptadorDatos(List<Datos>datos){this.datos=datos;}

    @NonNull
    @Override
    public DatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdatos,parent,false);
        return new DatosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatosViewHolder holder, int position) {

        holder.bind(datos.get(position));

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class DatosViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue, tvFecha;


        public DatosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha= itemView.findViewById(R.id.txtfecha);
            tvValue=itemView.findViewById(R.id.txtvalor);

        }
        public void bind(Datos datos) {
            tvValue.setText(datos.getValue());
            tvFecha.setText(datos.getFecha());
        }
    }
}
