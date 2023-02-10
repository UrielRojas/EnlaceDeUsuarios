package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FotoArchivo extends AppCompatActivity {

    TextView NombrePro,NombreEmpresa,NombrePresentacion,NombreTipo;
    ImageView Fotico;
    DatabaseReference mDataBase;
    Button Comerciantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_archivo);
        Bitmap Foto = getIntent().getParcelableExtra("Foto");


        String Objeto = getIntent().getStringExtra("Objeto");


        NombrePro = findViewById(R.id.NombrePro);
        NombreEmpresa = findViewById(R.id.NombreEmpresa);
        NombrePresentacion = findViewById(R.id.NombrePresentacion);
        NombreTipo = findViewById(R.id.NombreTipo);
        Comerciantes = findViewById(R.id.ProvedoresProductos);
        Fotico = findViewById(R.id.ImagenChida);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        Fotico.setImageBitmap(Foto);


        mDataBase.child("Productos").child(Objeto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Este Perfil si tiene perfil de comerciante
                if(snapshot.exists()){

                    String Nombre = snapshot.child("Nombre").getValue().toString();
                    String Empresa = snapshot.child("Empresa").getValue().toString();
                    String Presentacion = snapshot.child("Presentacion").getValue().toString();
                    String Tipo = snapshot.child("Tipo").getValue().toString();
                    NombrePro.setText("Nombre :"+Nombre);
                    NombreEmpresa.setText("Empresa: "+Empresa);
                    NombrePresentacion.setText("Presentacion: "+Presentacion);
                    NombreTipo.setText("Tipo de Producto: "+Tipo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Comerciantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FotoArchivo.this, Listacomercios.class);
                intent.putExtra("Objeto", Objeto);
                startActivity(intent);
            }
        });




    }






}