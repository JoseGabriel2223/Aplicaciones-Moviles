package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class AddGroupActivity extends AppCompatActivity {

    EditText edInsertar;
    EditText edDescription;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent vista= getIntent();
        String nombre= vista.getStringExtra("nombre");
        String Descripcion= vista.getStringExtra("descripcion");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_casa);
        edInsertar= findViewById(R.id.etGroupName);
        edDescription= findViewById(R.id.etGroupDescription);
        btnAgregar= findViewById(R.id.btnSaveGroup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Grupos");
        }

      btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= edInsertar.getText().toString();
                String description= edDescription.getText().toString();
                JSONObject jsonBody = new JSONObject();

                try {
                    jsonBody.put("name", name);
                    jsonBody.put("description", description);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url="http://54.163.22.51/api/v1/groups";
                JsonObjectRequest add= new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),name+" Agregada", Toast.LENGTH_SHORT).show();
                        Intent ag= new Intent(getApplicationContext(), GroupStudentsActivity.class);
                        startActivity(ag);
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(add);
            }
        });

    }
}