package com.example.securityhome;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAlumnos extends RecyclerView.Adapter<AdapterAlumnos.AdminViewHolder> {
    List<administrar> Administrar;

    public AdapterAlumnos(List<administrar> Administrar){this.Administrar=Administrar;}

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdmin,parent,false);
        return new AdminViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        holder.bind(Administrar.get(position));
    }
    @Override
    public int getItemCount() {
        return Administrar.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvnombre, tvdesc;
        String nomfun, Descfun;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnombre= itemView.findViewById(R.id.tvcasa);
            tvdesc= itemView.findViewById(R.id.tvdesc);
            itemView.setOnClickListener(this);
        }
        public void bind(administrar a) {
            tvnombre.setText(a.getNombre());
            tvdesc.setText(a.getDescripcion());
            this.nomfun= a.getNombre();
            this.Descfun= a.getDescripcion();
        }
        @Override
        public void onClick(View view) {

            switch (this.nomfun)
            {
                case "Mostrar Alumnos":
                    Intent vista = new Intent(view.getContext(), PartialsActivity.class);
                    vista.putExtra("nombre", this.nomfun.toString());
                    vista.putExtra("descripcion", this.Descfun.toString());
                    view.getContext().startActivity(vista);
                    break;

                case "Agregar Grupo":
                    Intent addgroups = new Intent(view.getContext(), AddGroupActivity.class);
                    addgroups.putExtra("nombre", this.nomfun.toString());
                    addgroups.putExtra("descripcion", this.Descfun.toString());
                    view.getContext().startActivity(addgroups);
                    break;


            }



        }
    }
}