package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.CookieHandler;

public class Perfil_Comerciante extends AppCompatActivity {

    TextView ComercioN ,UsuarioN ,Direccion;
    DatabaseReference mDataBase;
    Button Producto,Lista,Aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_comerciante);
        String ID = getIntent().getStringExtra("ID");
        String Usuario = getIntent().getStringExtra("Usuario");
        ComercioN = findViewById(R.id.ComercioN);
        UsuarioN = findViewById(R.id.UsuarioN);
        Direccion = findViewById(R.id.DireccionN);
        Producto = findViewById(R.id.Productos);
        Lista = findViewById(R.id.Lista);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        Aceptar = findViewById(R.id.AceptarComercio);


        //Accedemos a la base de datos
        mDataBase.child("Comercios").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Este Perfil si tiene perfil de comerciante
                if(snapshot.exists()){
                    String User = snapshot.child("Usuario").getValue().toString();
                    String Comercio = snapshot.child("Comercio").getValue().toString();
                    String Calle = snapshot.child("Calle").getValue().toString();
                    String Colonia = snapshot.child("Colonia").getValue().toString();
                    String Estado = snapshot.child("Estado").getValue().toString();
                    String Municipio = snapshot.child("Municipio").getValue().toString();
                    UsuarioN.setText(User);
                    ComercioN.setText(Comercio);
                    Direccion.setText("Calle "+Calle+" Colonia"+ Colonia+" ,"+Municipio+" "+Estado);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        Producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_Comerciante.this, Agregar_Producto.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Usuario",Usuario);
                startActivity(intent);
            }
        });

        Lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_Comerciante.this, Buscador_Productos.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Usuario",Usuario);
                startActivity(intent);
            }
        });

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_Comerciante.this, Inicio.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Usuario",Usuario);
                startActivity(intent);
            }
        });

    }
}