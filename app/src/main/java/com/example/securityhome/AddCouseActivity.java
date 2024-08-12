package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Optional;

public class AddCouseActivity extends AppCompatActivity {

    EditText tv_course_name, tv_course_description;
    Spinner spnr_teachers;
    Button btn_addcourse;
    ArrayList<String> teacherslistname = new ArrayList<>();
    ArrayList<TeachersModel> teacherslist = new ArrayList<>();
    String teacherSelected;
    TextView tvtitleActivity;
    String namegroup = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_couse);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Agregar curso");
        }

        Intent intent = getIntent();
        String group_id = intent.getStringExtra("id_group");
        String subject_id = intent.getStringExtra("id_subject");
        String course_name = intent.getStringExtra("course_name");
        String id_course = intent.getStringExtra("id_course");
        String teacher_name = intent.getStringExtra("teacher_name");
        String Name = intent.getStringExtra("name");
        String notes = intent.getStringExtra("notes");
        namegroup =  intent.getStringExtra("nombre");
        String Action = intent.getStringExtra("Action") != null ? intent.getStringExtra("Action") : "";


        tv_course_name = findViewById(R.id.etCourseName);
        tvtitleActivity = findViewById(R.id.textViewTitle);
        tv_course_description = findViewById(R.id.etCourseDescription);
        spnr_teachers = findViewById(R.id.spinnerCategory);
        btn_addcourse = findViewById(R.id.btnSaveCourse);

        if (Action.equals("Editar")) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Editar curso");
            }
            btn_addcourse.setText("Guardar");
            tvtitleActivity.setText("Editar Curso");
            tv_course_name.setText(Name);
            tv_course_description.setText(notes);
        }

        btn_addcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursename = tv_course_name.getText().toString();
                String courseDescription = tv_course_description.getText().toString();
                String btntitle = btn_addcourse.getText().toString();

                if (btntitle.equals("Agregar")) {
                    AddCourse(group_id);
                } else if (btntitle.equals("Guardar")) {
                    UpdateCourse(coursename, courseDescription, 20, Integer.parseInt(group_id), Integer.parseInt(id_course));
                }
            }
        });

        teacherslistname.add(0, "Selecciona un profesor");

        spnr_teachers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teacherSelected = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        GetTeachers();
    }

    public void AddCourse(String idgroup) {
        String url = "http://54.163.22.51/api/v1/courses";
        JSONObject jsonBody = new JSONObject();
        String course_name = tv_course_name.getText().toString();
        String course_description = tv_course_description.getText().toString();
        int teacher_id = 0;

        if (!teacherSelected.contains("Selecciona")) {
            Optional<TeachersModel> teachersearched = teacherslist.stream()
                    .filter(teacher -> teacher.getName().equals(teacherSelected))
                    .findFirst();

            if (teachersearched.isPresent()) {
                teacher_id = teachersearched.get().getId();
            } else {
                Toast.makeText(this, "Profesor no encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            jsonBody.put("name", course_name);
            jsonBody.put("notes", course_description);
            jsonBody.put("id_teacher", teacher_id);
            jsonBody.put("group_id", idgroup);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (course_name.isEmpty()) {
            Toast.makeText(this, "Agrega un nombre de curso", Toast.LENGTH_SHORT).show();
            return;
        }
        if (course_description.isEmpty()) {
            Toast.makeText(this, "Agrega una descripción al curso", Toast.LENGTH_SHORT).show();
            return;
        }
        if (teacher_id == 0) {
            Toast.makeText(this, "Selecciona un profesor", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest login = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(AddCouseActivity.this, course_name + " Agregado", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), MateriasActivity.class);
//                intent.putExtra("id", idgroup);
//                intent.putExtra("nombre", namegroup);
//                startActivity(intent);
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

    private void GetTeachers() {
        String url = "http://54.163.22.51/api/v1/teachers";
        JsonArrayRequest mosCasas = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);

                        String name = objeto.getString("name");
                        String email = objeto.getString("email");
                        int id = objeto.getInt("id");
                        teacherslist.add(new TeachersModel(id, name, email));
                        teacherslistname.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCouseActivity.this,
                        android.R.layout.simple_spinner_item, teacherslistname);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnr_teachers.setAdapter(adapter);
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

    private void UpdateCourse(String name, String notes, int idteacher, int groupid, int id) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name);
            jsonBody.put("notes", notes);
            jsonBody.put("id_teacher", idteacher);
            jsonBody.put("group_id", groupid);
            jsonBody.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://54.163.22.51/api/v1/courses";
        JsonObjectRequest mosCasas = new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(AddCouseActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
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
