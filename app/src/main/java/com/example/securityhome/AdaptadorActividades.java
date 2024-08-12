package com.example.securityhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdaptadorActividades extends RecyclerView.Adapter<AdaptadorActividades.ActividadesViewHolder> {

    List<ActividadesModel> Activities;

    public AdaptadorActividades(List<ActividadesModel> activities){this.Activities = activities;}


    @NonNull
    @Override
    public AdaptadorActividades.ActividadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad,parent,false);
        return new ActividadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorActividades.ActividadesViewHolder holder, int position) {
        holder.bind(Activities.get(position));
    }

    @Override
    public int getItemCount() {
        return Activities.size();
    }

    public class ActividadesViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvdescription;
        Button btn_completed;
        CheckBox chkCompleted;

        public ActividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvActivityName);
            tvdescription = itemView.findViewById(R.id.tvActivityDescription);
            chkCompleted =itemView.findViewById(R.id.chkCompleted);
            btn_completed =itemView.findViewById(R.id.btnMarkCompleted);
        }
        public void bind(ActividadesModel a)
        {
            tvname.setText(a.getName_activity());
            tvdescription.setText(a.getDescription());
            if(Boolean.valueOf(a.getReady()))
            {
                chkCompleted.setChecked(true);
                chkCompleted.setEnabled(false);
                btn_completed.setVisibility(View.GONE);
            }
            btn_completed.setEnabled(false);
            chkCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                    btn_completed.setEnabled(ischecked);
                }
            });

            btn_completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String url = "http://54.163.22.51/api/v1/activities";

                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("id", a.getId());
                        jsonBody.put("name", a.getName_activity());
                        jsonBody.put("description", a.getDescription());
                        jsonBody.put("ready", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest checked = new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(itemView.getContext(), "Success", Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(itemView.getContext(), GroupStudentsActivity.class);
//                            view.getContext().startActivity(intent);
//                            ((Activity)itemView.getContext()).finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                                Toast.makeText(itemView.getContext(), "Credenciales incorrectas. Por favor, intenta de nuevo.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(itemView.getContext(), "Error de red. Por favor, verifica tu conexión.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    SingletonRequest.getInstance(itemView.getContext()).addToRequestQue(checked);
                }
            });
        }
    }
}
