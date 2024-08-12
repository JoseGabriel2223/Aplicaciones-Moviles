package com.example.securityhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    EditText edName, edEmail, edpassword,edConfirmar;
    Button btnRegistro;
    String name, email, password, confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edName=findViewById(R.id.edNombre);
        edEmail=findViewById(R.id.edCorreo);
        edpassword=findViewById(R.id.edContraseña);
        edConfirmar=findViewById(R.id.edConfirmar);

    }
    public void Registrar(View view){
            String url="http://54.219.172.193/api/register";
            JSONObject jsonBody= new JSONObject();

        Toast.makeText(this, "Registrarse", Toast.LENGTH_SHORT).show();
        try {
            jsonBody.put("name", edName.getText().toString());
            jsonBody.put("email", edEmail.getText().toString());
            jsonBody.put("password", edpassword.getText().toString());
            jsonBody.put("password_confirmation",edConfirmar.getText().toString());

        }catch(Exception e){
        }

        JsonObjectRequest registro1= new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                edName.setText("");
                edEmail.setText("");
                edpassword.setText("");
                edConfirmar.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("vol", error.toString());
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(registro1);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}