package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistroComerciantes extends AppCompatActivity {

    private ProgressDialog progressDialog;
    TextView comercio,Calle,Colonia,Municipio,Estado;
    Button Aceptar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comerciantes);
        comercio = findViewById(R.id.Comercio);
        Calle = findViewById(R.id.Calle);
        Colonia = findViewById(R.id.Colonia);
        Municipio = findViewById(R.id.Municipio);
        Estado = findViewById(R.id.Estado);
        Aceptar = findViewById(R.id.Acept);
        progressDialog = new ProgressDialog(RegistroComerciantes.this); //Iniciamos el progresdialog
        dialog = new Dialog(RegistroComerciantes.this);
        String ID = getIntent().getStringExtra("ID");
        String Usuario = getIntent().getStringExtra("Usuario");

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngresarComercio(Usuario,ID);
            }
        });


    }


    private void Listo(String ID , String Usuario){
        Button ok_no;
        dialog.setContentView(R.layout.aceptar);//Conexion con el layaut de no session
        ok_no = dialog.findViewById(R.id.Aceptar);

        //Al presionar el boton se cierra el cuadro
        ok_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(RegistroComerciantes.this, Inicio.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Usuario",Usuario);
                intent.putExtra("TipoUsuario","Comerciante");
                startActivity(intent);
            }
        });
        dialog.setCancelable(false);//Se puede presionar Fuera de la animacion
        dialog.show();
    }


    private void IngresarComercio(String Usuario,String ID){
        HashMap<Object,String> DatosComercio = new HashMap<>();
        DatosComercio.put("Usuario",Usuario);
        DatosComercio.put("Comercio",comercio.getText().toString());
        DatosComercio.put("Calle",Calle.getText().toString());
        DatosComercio.put("Colonia",Colonia.getText().toString());
        DatosComercio.put("Municipio",Municipio.getText().toString());
        DatosComercio.put("Estado",Estado.getText().toString());
        DatosComercio.put("ID",ID);
        //Iniciamos la instancia de base de datos de firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Se crea la base de datos
        DatabaseReference reference = database.getReference("Comercios");
        //Base de datos no Relacional = "USUARIOS_STOREAGE"
        reference.child(ID).setValue(DatosComercio);
        //startActivity(new Intent(RegistroComerciantes.this,Inicio.class));
        progressDialog.dismiss();
        Listo(ID,Usuario);

    }
}