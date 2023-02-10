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

public class PerfilComercioLista extends AppCompatActivity {

    TextView Vista,Usuario,ComercioPerfi;
    Button Productos,Inicio;
    DatabaseReference mDataBase;
    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ID = getIntent().getStringExtra("ID");
        setContentView(R.layout.activity_perfil_comercio_lista);


        Vista = findViewById(R.id.comer);
        Usuario = findViewById(R.id.UsuarioPerfi);
        ComercioPerfi = findViewById(R.id.ComercioPerfi);
        Productos = findViewById(R.id.Perficomer1);
        Inicio = findViewById(R.id.InicioApi);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        mDataBase.child("Comercios").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Este Perfil si tiene perfil de comerciante
                if(snapshot.exists()){
                    User = snapshot.child("Usuario").getValue().toString();
                    String Comercio = snapshot.child("Comercio").getValue().toString();
                    String Calle = snapshot.child("Calle").getValue().toString();
                    String Colonia = snapshot.child("Colonia").getValue().toString();
                    String Estado = snapshot.child("Estado").getValue().toString();
                    String Municipio = snapshot.child("Municipio").getValue().toString();
                    Vista.setText(Comercio);
                    Usuario.setText("Usuario: "+User);
                    ComercioPerfi.setText("Calle "+Calle+" Colonia "+ Colonia+" ,"+Municipio+" "+Estado);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilComercioLista.this, Buscador_Productos.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Usuario",User);
                startActivity(intent);
            }
        });
    }
}