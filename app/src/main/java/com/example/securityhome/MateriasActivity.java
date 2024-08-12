package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MateriasActivity extends AppCompatActivity {
    TextView  sensor;
    String namecasona, nameSensor, valorsen;
    String Id;
    Intent vista;
    List<Courses> senCasa = new ArrayList<>();
    List<Casas> nCasa = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton flot;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_casa);

        vista= getIntent();
        namecasona= vista.getStringExtra("nombre");
        Id= vista.getStringExtra("id");


        recyclerView= findViewById(R.id.recyclerSCasa);
        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReloadActivity();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        flot= findViewById(R.id.fab2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Materias "+ namecasona);
        }

        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddCouseActivity.class);
                intent.putExtra("nombrecasa",namecasona);
                intent.putExtra("id_group",String.valueOf(Id));
                startActivity(intent);
                finish();
            }
        });
        //sensor= findViewById(R.id.tvSensor);
        ShowSubjects();
    }

    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void ShowSubjects() {

        String url= "http://54.163.22.51/api/v1/groups/"+Id;

        JsonArrayRequest versensores= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int x=0; x<response.length(); x++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(x).toString());
                        nameSensor= objeto.getString("name");
                        String idcourse = objeto.getString("id");
                        String progress = objeto.getString("progress");
                        String notes = objeto.getString("notes");


                        JSONObject objectTeacher = new JSONObject(objeto.getString("teacher"));
                        String teachername = objectTeacher.getString("name");

                        //String valor= valorsen;
                        senCasa.add(new Courses(nameSensor, idcourse, namecasona, progress, teachername, Id, notes));
                        nCasa.add(new Casas(namecasona, "", 0, 0, 0));

                        recyclerView.setLayoutManager(new LinearLayoutManager(MateriasActivity.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorMaterias sensores = new AdaptadorMaterias(senCasa,nCasa);
                        recyclerView.setAdapter(sensores);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de red. Por favor, verifica tu conexión.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(versensores);
    }

}