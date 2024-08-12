package com.example.securityhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupStudentsActivity extends AppCompatActivity {
    TextView name, description, user;
    Intent intent;
    RecyclerView recyclerCasas;
    String nombreCasa, user_id, description_group, rol_id;
    private SwipeRefreshLayout swipeRefreshLayout;
    int Id;
    List<Casas> cas = new ArrayList<>();
            FloatingActionButton flot;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_groups);

                flot= findViewById(R.id.fab);
                flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });

        flot.setVisibility(View.GONE);
        Intent vista= getIntent();
        user_id= vista.getStringExtra("user_id");
        rol_id= vista.getStringExtra("rol_id");

        //Toast.makeText(this, "rol: "+rol_id+" user id: "+user_id, Toast.LENGTH_SHORT).show();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Grupos");
        }
        recyclerCasas= findViewById(R.id.recyclerGroups);
        ShowGroups();
                swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);

                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ReloadActivity();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void ShowGroups() {
        String url="http://54.163.22.51/api/v1/students/"+user_id+"/groups";
        JsonArrayRequest mosCasas = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i= 0; i<response.length(); i++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        nombreCasa=objeto.getString("name");
                        description_group=objeto.getString("description");
                        Id=Integer.parseInt(objeto.getString("id"));
                        cas.add(new Casas(nombreCasa, description_group, Id, Integer.parseInt(rol_id), Integer.parseInt(user_id)));
                        recyclerCasas.setLayoutManager(new LinearLayoutManager(GroupStudentsActivity.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorCasas adaptar = new AdaptadorCasas(cas);
                        recyclerCasas.setAdapter(adaptar);
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
        SingletonRequest.getInstance(this).addToRequestQue(mosCasas);
    }

}