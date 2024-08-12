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

public class EditarActivity extends AppCompatActivity {

    Intent vista;
    String namegroup, descriptiongroup, id;
    EditText et_namegroup, et_descriptiongroup;
    Button btnSave, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        vista = getIntent();
        namegroup= vista.getStringExtra("name");
        descriptiongroup= vista.getStringExtra("description");
        id= vista.getStringExtra("id");

        et_descriptiongroup =(EditText) findViewById(R.id.etGroupDescription);
        et_namegroup = (EditText) findViewById(R.id.etGroupName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        et_namegroup.setText(namegroup);
        et_descriptiongroup.setText(descriptiongroup);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditGroup();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), GroupStudentsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void EditGroup()
    {
        String url = "http://54.163.22.51/api/v1/groups";
        JSONObject jsonBody = new JSONObject();
        String descriptionGroup = et_descriptiongroup.getText().toString();
        String nameGroup = et_namegroup.getText().toString();

        try {
            jsonBody.put("id", id);
            jsonBody.put("name", nameGroup);
            jsonBody.put("description", descriptionGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nameGroup.length() == 0) {
            Toast.makeText(EditarActivity.this, "Escribe un nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (descriptionGroup.length() == 0) {
            Toast.makeText(EditarActivity.this, "Escribe una descripcion", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Procesando....", Toast.LENGTH_SHORT).show();

        JsonObjectRequest login = new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Has editado "+nameGroup+" exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), GroupStudentsActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    Toast.makeText(getApplicationContext(), "Credenciales incorrectas. Por favor, intenta de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de red. Por favor, verifica tu conexión.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SingletonRequest.getInstance(this).addToRequestQue(login);
    }
}