package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Agregar_Sensor extends AppCompatActivity {
    Button btnAddSensor;
    TextView tvDisponibles, tvP;
    String namecasona, nameSensor;
    EditText add;
    ListView lv1;

    String opciones[]= {"Temperatura1","Humendad","gas","flame","Ultrasonico1"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_sensor);
        Intent intent= getIntent();
        namecasona= intent.getStringExtra("nombrecasa");
        btnAddSensor= findViewById(R.id.btnAddSensor);
        //tvDisponibles= findViewById(R.id.tvDisponibles);
        tvP=findViewById(R.id.tvPrueba);
        add=findViewById(R.id.etAdd);
        lv1=findViewById(R.id.lv1);
        tvP.setText(namecasona);

        //tvDisponibles.setText("1.Temperatura1\n"+"2.Humendad\n"+"3.gas\n"+"4.FLAME\n"+"5.Ultrasonico1");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Agregar_Sensor.this, R.layout.list_view_opciones, opciones);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                add.setText(""+lv1.getItemAtPosition(i));

            }
        });


        btnAddSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomSensor = add.getText().toString();
                String url="http://54.219.172.193/api/auth/sensor/AñadirSensor/"+namecasona+"/"+nomSensor;

                JsonObjectRequest addS= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray a= response.getJSONArray("feeds");
                            for(int i=0; i<a.length(); i++){
                                JSONObject obj = new JSONObject(a.get(i).toString());
                                String name= obj.getString("name");
                            }
                            Intent addsen= new Intent(Agregar_Sensor.this, GroupStudentsActivity.class);
                            startActivity(addsen);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(addS);
            }
        });

    }
}