package com.example.securityhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{
    Button btnregistro,btnLogin;
    EditText edEmail, edPassword;
    TextView  token;
    String name,email,created;
    public static String SaveToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=findViewById(R.id.btnLogin);
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        btnregistro= findViewById(R.id.btnRegistrarse);
        token= findViewById(R.id.txtToken);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });
        btnregistro.setVisibility(View.GONE);
        //AutoLogin();
    }

    public void Sesion(View view) {
        String emailI = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        Login(emailI, password);
    }

    private void Login(String emailI, String password)
    {
        String url = "http://54.163.22.51/api/v1/auth";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", emailI);
            jsonBody.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (emailI.length() == 0) {
            Toast.makeText(MainActivity.this, "Ingresa tu correo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(MainActivity.this, "Ingresa tu Contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Iniciando sesión....", Toast.LENGTH_SHORT).show();

        JsonObjectRequest login = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Has iniciado sesión exitosamente", Toast.LENGTH_SHORT).show();

                edEmail.setText("");
                edPassword.setText("");

                try {
                    SaveToken = response.getString("remember_token");
                    //Toast.makeText(MainActivity.this, SaveToken, Toast.LENGTH_SHORT).show();
                    System.out.println(SaveToken);

                    SharedPreferences Token = getSharedPreferences("token", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = Token.edit();
                    editor.putString("key", SaveToken);
                    editor.putString("email", emailI);
                    editor.putString("password", password);
                    editor.putBoolean("flagLogin", true);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), GroupStudentsActivity.class);
                    intent.putExtra("user_id", response.getString("user_id"));
                    intent.putExtra("rol_id", response.getString("rol"));
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private String GetParameter(String param_name) {
        SharedPreferences TokenPrefs = getSharedPreferences("token", Context.MODE_PRIVATE);
        return String.valueOf(TokenPrefs.getString(param_name, null));
    }
    private Boolean GetParameterBool(String param_name) {
        SharedPreferences TokenPrefs = getSharedPreferences("token", Context.MODE_PRIVATE);
        return TokenPrefs.getBoolean(param_name, false);
    }

    private void AutoLogin()
    {
         Boolean isLogin= GetParameterBool("flagLogin");
        String email = GetParameter("email");
        String password = GetParameter("password");
        if(isLogin)
        {
            Login(email, password);
        }
        else
        {
            Toast.makeText(this, "Inicia Sesion", Toast.LENGTH_SHORT).show();
        }
    }
}