package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActividadesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ActividadesModel> Activities = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReloadActivity();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Intent intent = getIntent();
        String partial_id = intent.getStringExtra("partial_id");
        String partial_name = intent.getStringExtra("partial_name");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Actividades "+partial_name);
        }
        recyclerView = findViewById(R.id.recyclerActivities);
        GetActivitiesBySubject(partial_id);
    }

    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void GetActivitiesBySubject(String partial_id)
    {
        String url= "http://54.163.22.51/api/v1/partials/"+partial_id+"/activities";

        JsonArrayRequest versensores= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int x=0; x<response.length(); x++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(x).toString());
                        String id= objeto.getString("id");
                        String partial_id = objeto.getString("partial_id");
                        String name = objeto.getString("name");
                        String description = objeto.getString("description");
                        String ready = objeto.getString("ready");
                        String grade = objeto.getString("grade");

                        Activities.add(new ActividadesModel(id, partial_id, name, description,ready,grade));

                        recyclerView.setLayoutManager(new LinearLayoutManager(ActividadesActivity.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorActividades sensores = new AdaptadorActividades(Activities);
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