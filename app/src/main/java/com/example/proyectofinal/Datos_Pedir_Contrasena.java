package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Datos_Pedir_Contrasena extends AppCompatActivity {


    EditText Contrasena;
    Button Cambios;
    TextView Portada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_pedir_contrasena);
        String pass = getIntent().getStringExtra("Contrasena");
        String Correo = getIntent().getStringExtra("Correo");
        String ID = getIntent().getStringExtra("ID");
        Contrasena = findViewById(R.id.ContrasenaIngreso);
        Cambios = findViewById(R.id.Aceptar_Contra);
        Portada =  findViewById(R.id.Caratula);

        Cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.equals(Contrasena.getText().toString())){
                    Intent intent = new Intent(Datos_Pedir_Contrasena.this, Modificar_Datos.class);
                    intent.putExtra("ID", ID);
                    intent.putExtra("Correo", Correo);
                    startActivity(intent);
                }else{
                    Contrasena.setError("La contraseña es incorrecta");
                }
            }
        });


    }
}