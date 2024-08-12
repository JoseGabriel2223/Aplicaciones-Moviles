package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Datos_Sensores extends AppCompatActivity {
    //aqui declaramos variables globales
    TextView nameSensor, tvSensor, Ct, Up, prueba;
    String nomsen, namecasona;
    Intent intent;
    Button btnHisto, btnDeleSensor;
    ConstraintLayout constraint;
    Handler intervalo = new Handler();
    int tiempo= 15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_sensores);

        //aqui es donde casteamos todos los elementos
        nameSensor= findViewById(R.id.tvNomSen);
        tvSensor=findViewById(R.id.tvValor);
        Ct= findViewById(R.id.tvCreated);
        Up= findViewById(R.id.tvUpdate);
        btnHisto=findViewById(R.id.btnHistorico);
        constraint=(ConstraintLayout) findViewById(R.id.cons);
        prueba=findViewById(R.id.prueCasona);
        btnDeleSensor=findViewById(R.id.btnDelete);

        //Aqui extraemos los datos del adaptador
        Intent vistasen= getIntent();
        nomsen= vistasen.getStringExtra("nombresen");
        namecasona=vistasen.getStringExtra("nombreC");
        prueba.setText(namecasona);
        intent= getIntent();
        nameSensor.setText(nomsen);
        //clases a parte
        EliminarSensor();
        historico();
        switch (nomsen){
            case "Temperatura1":
                MostrarTemperatura();
                intervalo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MostrarTemperatura();
                        intervalo.postDelayed(this, tiempo);
                    }
                }, tiempo);
                break;

            case"Humedad":
                MostrarHumedad();

                intervalo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MostrarHumedad();
                        intervalo.postDelayed(this, tiempo);
                    }
                }, tiempo);
                break;

            case "gas":
                MostrarGas();
                intervalo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MostrarGas();
                        intervalo.postDelayed(this, tiempo);
                    }
                }, tiempo);
                break;

            case "Ultrasonico1":
                MostrarUltrasonico();
                intervalo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MostrarUltrasonico();
                        intervalo.postDelayed(this, tiempo);
                    }
                }, tiempo);
                break;

            case "FLAME":
                MostrarFlama();
                intervalo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MostrarFlama();
                        intervalo.postDelayed(this, tiempo);
                    }
                }, tiempo);
                break;

            default:
                break;
        }
    }
    private void historico() {
        btnHisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historicoS= new Intent(Datos_Sensores.this, Historico.class);
                historicoS.putExtra("nombreS", nomsen);
                startActivity(historicoS);
                finish();
            }
        });
    }
    private void MostrarTemperatura() {
        String url="http://54.219.172.193/api/auth/temperatura/MostrarTemperatura";

        JsonObjectRequest sensor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int i= response.getInt("value");
                    tvSensor.setText(i+"C°");
                   Ct.setText("Actualizacion: "+response.getString("created_at"));
                   Up.setText("Ultima Actulizacion: "+response.getString("updated_at"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(sensor);
    }
    private void MostrarHumedad() {

        String url="http://54.219.172.193/api/auth/humedad/MostrarHumedad";
        JsonObjectRequest sensor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int i= response.getInt("value");
                    tvSensor.setText(i+"%");
                    Up.setText(response.getString("updated_at"));
                    Ct.setText(response.getString("created_at"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(sensor);
    }
    private void MostrarGas() {
        String url="http://54.219.172.193/api/auth/gas/MostrarGas";
        JsonObjectRequest sensor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int i= response.getInt("value");
                    tvSensor.setText(i+" ppm");
                    Up.setText(response.getString("updated_at"));
                    Ct.setText(response.getString("created_at"));

                    if (i>150){
                        constraint.setBackgroundColor(Color.RED);
                        AlertDialog.Builder confirmacion = new AlertDialog.Builder(Datos_Sensores.this);
                        confirmacion.setMessage("Se ha detectado una posible fuga de gas");
                        confirmacion.setTitle("ADVERTENCIA!!");
                        confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = confirmacion.create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(sensor);



    }
    private void MostrarUltrasonico() {

        String url="http://54.219.172.193/api/auth/distancia/MostrarDistancia";

        JsonObjectRequest sensor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    tvSensor.setText(response.getString("value")+"cm");
                    Ct.setText(response.getString("updated_at"));
                    Up.setText(response.getString("created_at"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(sensor);
    }
    private void MostrarFlama() {

        String url="http://54.219.172.193/api/auth/flama/MostrarFlama";

        JsonObjectRequest sensor= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int val= response.getInt("value");
                    tvSensor.setText(val+"");
                    Ct.setText(response.getString("updated_at"));
                    Up.setText(response.getString("created_at"));

                    if (val==1){
                        constraint.setBackgroundColor(Color.RED);
                        AlertDialog.Builder confirmacion = new AlertDialog.Builder(Datos_Sensores.this);
                        confirmacion.setMessage("Se ha detectado fuego en la casa");
                        confirmacion.setTitle("ADVERTENCIA!!");
                        confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = confirmacion.create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(sensor);

    }

    private void EliminarSensor() {
     btnDeleSensor.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {
             AlertDialog.Builder confirmacion = new AlertDialog.Builder(Datos_Sensores.this);
             confirmacion.setMessage("Estas seguro que quieres eliminar "+nomsen+" ?");
             confirmacion.setTitle("Confirmacion eliminar");
             confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                     String url="http://54.219.172.193/api/auth/sensor/CambiarSensor/"+namecasona+"/"+nomsen;

                     JsonObjectRequest DeleteSensor= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                         @Override
                         public void onResponse(JSONObject response) {
                             Intent sensorcasa= new Intent(Datos_Sensores.this, GroupStudentsActivity.class);
                             startActivity(sensorcasa);
                             finish();
                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                         }
                     });
                     SingletonRequest.getInstance(Datos_Sensores.this).addToRequestQue(DeleteSensor);

                 }
             });
             confirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.cancel();
                 }

             });
             AlertDialog dialog = confirmacion.create();
             dialog.show();

         }
     });
    }
}