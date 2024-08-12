package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Historico extends AppCompatActivity {
    Intent historicoS;
    TextView tvhistSen,Valor,nomFeed;
    List<Datos> datos = new ArrayList<>();
    RecyclerView recycler;
    String val, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        historicoS= getIntent();
        String nomS= historicoS.getStringExtra("nombreS");
        tvhistSen= findViewById(R.id.tvhistSen);
        tvhistSen.setText(nomS+" Historico");
        recycler = (RecyclerView) findViewById(R.id.recycler);
        switch (nomS){
            case "Temperatura1":
                ReporteTemperatura();
                break;
            case "Humedad":
                ReporteHumedad();
                break;
            case "Ultrasonico1":
                ReporteDistancia();
                break;
            case"FLAME":
                ReporteFlama();
                break;
            case"gas":
                ReporteGas();
                break;
            default:
                break;
        }

    }
    //-------Aqui se mostrara el reporte de Temperatura----------------
    private void ReporteTemperatura() {

        String url="http://54.219.172.193/api/auth/temperatura/MostrarRegistro";

        JsonArrayRequest RTemp= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson= new Gson();
                Toast.makeText(getApplicationContext(), "Reporte de Actualizaciones de Temperatura", Toast.LENGTH_SHORT).show();

                for(int i =0; i<10; i++)
                {
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        val=objeto.getString("value")+"C°";
                        fecha= objeto.getString("updated_at");

                        datos.add(new Datos(fecha,val));
                        recycler.setLayoutManager(new LinearLayoutManager(Historico.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorDatos adacptadorDatos = new AdaptadorDatos(datos);
                        recycler.setAdapter(adacptadorDatos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(RTemp);
    }
    //-------Aqui se mostrara el reporte de Humedad----------------
    private void ReporteHumedad() {

        String url="http://54.219.172.193/api/auth/humedad/MostrarRegistro";

        JsonArrayRequest RTemp= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext(), "Reporte de Actualizaciones de Humedad", Toast.LENGTH_SHORT).show();
                for(int i =0; i<10; i++)
                {
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        val=objeto.getString("value");
                        fecha= objeto.getString("updated_at");

                        datos.add(new Datos(fecha,val));
                        recycler.setLayoutManager(new LinearLayoutManager(Historico.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorDatos adacptadorDatos = new AdaptadorDatos(datos);
                        recycler.setAdapter(adacptadorDatos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(RTemp);
    }
    //-------Aqui se mostrara el reporte de distancia----------------
    private void ReporteDistancia() {

        String url="http://54.219.172.193/api/auth/distancia/MostrarRegistro";

        JsonArrayRequest RTemp= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext(), "Reporte de Actualizaciones de Distancias", Toast.LENGTH_SHORT).show();
                for(int i =0; i<10; i++)
                {
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        val=objeto.getString("value");
                        fecha= objeto.getString("updated_at");

                        datos.add(new Datos(fecha,val));
                        recycler.setLayoutManager(new LinearLayoutManager(Historico.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorDatos adacptadorDatos = new AdaptadorDatos(datos);
                        recycler.setAdapter(adacptadorDatos);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(RTemp);
    }
    //-------Aqui se mostrara el reporte de movimiento----------------
    private void ReporteFlama() {
        String url="http://54.219.172.193/api/auth/flama/MostrarRegistro";

        JsonArrayRequest RTemp= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int n= 10;
                for(int i =0; i<5; i++)
                {
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());


                        if (objeto.getString("value") == "1"){
                            val=objeto.getString("value")+" Se detecto fuego ";

                        }else{
                            val=objeto.getString("value");
                        }
                        fecha= objeto.getString("updated_at");

                        datos.add(new Datos(fecha,val));
                        recycler.setLayoutManager(new LinearLayoutManager(Historico.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorDatos adacptadorDatos = new AdaptadorDatos(datos);
                        recycler.setAdapter(adacptadorDatos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(RTemp);
    }
    //-------Aqui se mostrara el reporte de gas----------------
    private void ReporteGas() {

        String url="http://54.219.172.193/api/auth/gas/MostrarRegistro";

        JsonArrayRequest RTemp= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0; i<5; i++)
                {
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        val=objeto.getString("value");
                        fecha= objeto.getString("updated_at");
                        datos.add(new Datos(fecha,val));
                        recycler.setLayoutManager(new LinearLayoutManager(Historico.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorDatos adacptadorDatos = new AdaptadorDatos(datos);
                        recycler.setAdapter(adacptadorDatos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(RTemp);
    }
}
