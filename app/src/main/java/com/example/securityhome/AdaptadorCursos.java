package com.example.securityhome;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorCursos extends RecyclerView.Adapter<AdaptadorCursos.AdminViewHolder> {

    private List<Partial_Model> alumnos;

    public AdaptadorCursos(List<Partial_Model> alumnos) {
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        holder.bind(alumnos.get(position));
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_name_student;
        private TextView tv_description_student;
        private Integer id_student;
        private String partial_name;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_student = itemView.findViewById(R.id.tvStudentName);
            tv_description_student = itemView.findViewById(R.id.etStudentDescription);
            itemView.setOnClickListener(this);
        }

        public void bind(Partial_Model a) {
            tv_name_student.setText(a.getName());
            tv_description_student.setText(a.getNotes());
            id_student = a.getId();
            partial_name = a.getName();
        }

        @Override
        public void onClick(View view) {
            Intent vista = new Intent(view.getContext(), ActividadesActivity.class);
            vista.putExtra("partial_id", String.valueOf(id_student));
            vista.putExtra("partial_name", partial_name);
            view.getContext().startActivity(vista);
        }
    }
}

