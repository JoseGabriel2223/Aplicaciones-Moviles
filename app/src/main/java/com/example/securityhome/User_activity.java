package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class User_activity extends AppCompatActivity {
    TextView user, email, created;
    Intent intent;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user=findViewById(R.id.tvusuario);
        email=findViewById(R.id.tvemail);
        created=findViewById(R.id.tvInicio);
        logout=findViewById(R.id.btnLogout);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Nuevo Título");
        }

        intent= getIntent();
        String Usuario= intent.getStringExtra("user1");
        String email1= intent.getStringExtra("email1");
        String created1= intent.getStringExtra("created");

        user.setText(Usuario);
        email.setText("Email: "+email1);
        created.setText("Fecha Inicio de sesion: \n"+created1);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmacion = new AlertDialog.Builder(User_activity.this);
                confirmacion.setMessage("Estas seguro que quieres cerrar sesion ?");
                confirmacion.setTitle("Confirmacion Cerrar Sesion");
                confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Intent intent= new Intent(User_activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();*/
                        Toast.makeText(User_activity.this, "Haz cerrado sesion", Toast.LENGTH_SHORT).show();
                        finishAffinity();
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