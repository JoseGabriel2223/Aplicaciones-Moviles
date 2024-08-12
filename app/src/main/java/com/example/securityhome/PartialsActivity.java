package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
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

public class PartialsActivity extends AppCompatActivity {

    RecyclerView recyclerViewStudents;
    List<Partial_Model> students = new ArrayList<>();
    private int id;
    private String name;
    private String course_id;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partials);
        recyclerViewStudents= findViewById(R.id.recyclerPartials);


        Intent intent = getIntent();
        course_id = intent.getStringExtra("idcourse");
        String name_subject = intent.getStringExtra("name_subject");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Parciales "+name_subject);
        }

        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReloadActivity();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ShowPartials();
    }
    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void ShowPartials()
    {
        String url="http://54.163.22.51/api/v1/courses/"+course_id+"/partials";
        JsonArrayRequest mosCasas = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(PartialsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                for(int i= 0; i<response.length(); i++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        id= Integer.parseInt(objeto.getString("id"));
                        name=objeto.getString("name");
                        //String notes=objeto.getString("notes");

                        students.add(new Partial_Model(id, name, "Evaluacion del parcial"));
                        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(PartialsActivity.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorCursos adaptar = new AdaptadorCursos(students);
                        recyclerViewStudents.setAdapter(adaptar);
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