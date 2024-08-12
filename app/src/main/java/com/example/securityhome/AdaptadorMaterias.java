package com.example.securityhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

public class AdaptadorMaterias extends RecyclerView.Adapter<AdaptadorMaterias.SensoresViewHolder> {

    List<Courses> sensores;
    List <Casas> casas;

    public AdaptadorMaterias(List<Courses> sensores, List <Casas> casas){
        this.sensores=sensores;
        this.casas= casas;
    }

    @NonNull
    @Override
    public SensoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsensores,parent,false);
        return new SensoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMaterias.SensoresViewHolder holder, int position) {
        holder.bind(sensores.get(position));
        //holder.bind2(casas.get(position));
    }
    @Override
    public int getItemCount() {
        return sensores.size();
    }

    public class SensoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvnameSensor, tvteachername;
        String nomSensor, namecasa, idcourse;
        Button btndelete, btnEdit;

        ProgressBar progress;
        public SensoresViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnameSensor= itemView.findViewById(R.id.tvSubjectName);
            progress = itemView.findViewById(R.id.progressBar);
            btndelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            tvteachername=itemView.findViewById(R.id.tvTeacherName);
            itemView.setOnClickListener(this);
        }

        public void bind(Courses s) {
            tvnameSensor.setText(s.getNombre());
            tvteachername.setText("Profesor: "+ s.getTeacherName());
            this.nomSensor= s.getNombre();
            this.idcourse = s.getId_course();

            String test = s.getProgress();

            Float progresscourse = Float.parseFloat(s.getProgress());
            progress.setProgress(Math.round(progresscourse));

            if(progresscourse < 100)
            {
                btndelete.setVisibility(View.GONE);
            }
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder confirmacion = new AlertDialog.Builder(itemView.getContext());
                    confirmacion.setMessage("¿Estas seguro que quieres eliminar "+s.getNombre()+"?");
                    confirmacion.setTitle("Confirmacion eliminar");

                    confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url="http://54.163.22.51/api/v1/courses?id="+s.getId_course();

                            JsonObjectRequest eliminar = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //Toast.makeText(itemView.getContext(), nameCasa+" Eliminada", Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(itemView.getContext(), MateriasActivity.class );
                                    String co_name = s.getCours_name();

                                    intent.putExtra("id", s.getId_group());
                                    intent.putExtra("nombre", co_name);
                                    view.getContext().startActivity(intent);
                                    ((Activity)itemView.getContext()).finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            SingletonRequest.getInstance(view.getContext()).addToRequestQue(eliminar);
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
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editActivity = new Intent(view.getContext(), AddCouseActivity.class);
                    editActivity.putExtra("id_group", s.getId_group());
                    editActivity.putExtra("name", s.getNombre());
                    editActivity.putExtra("course_name", s.getCours_name());
                    editActivity.putExtra("teacher_name", s.getTeacherName());
                    editActivity.putExtra("id_course", s.getId_course());
                    editActivity.putExtra("notes", s.getNotes());
                    editActivity.putExtra("id_subject", s.getSubject_id());
                    editActivity.putExtra("Action", "Editar");
                    editActivity.putExtra("nombre", "");
                    view.getContext().startActivity(editActivity);
                }
            });
        }
        @Override
        public void onClick(View view) {
            Intent vistasen = new Intent(view.getContext(), PartialsActivity.class);
            vistasen.putExtra("idcourse", this.idcourse);
            vistasen.putExtra("name_subject", this.nomSensor);
            view.getContext().startActivity(vistasen);
        }
    }
}