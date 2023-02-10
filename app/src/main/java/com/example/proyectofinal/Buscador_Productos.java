package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.proyectofinal.Buscador.Comerciantes;
import com.example.proyectofinal.Buscador.Productos;
import com.example.proyectofinal.adapter.AdapterProducto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buscador_Productos extends AppCompatActivity {

    DatabaseReference mDataBase;
    ArrayList<Productos> list;
    RecyclerView pr;
    SearchView searchView;
    AdapterProducto adapter;

    LinearLayoutManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_productos);

        String ID = getIntent().getStringExtra("ID");
        String Usuario = getIntent().getStringExtra("Usuario");
        mDataBase = FirebaseDatabase.getInstance().getReference().child("Comercios").child(ID).child("Productos_Registrados");
        //
        pr =findViewById(R.id.CM);
        searchView = findViewById(R.id.searchC);
        lm = new LinearLayoutManager(this);
        pr.setLayoutManager(lm);
        list = new ArrayList<>();
        adapter = new AdapterProducto(list);
        pr.setAdapter(adapter);

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 :snapshot.getChildren()){
                        Productos pro = snapshot1.getValue(Productos.class);
                        list.add(pro);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });
    }

    private void buscar(String s) {
        ArrayList<Productos>mlista = new ArrayList<>();

        for(Productos p:list){
            if(p.getEmpresa().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
            else if(p.getNombre().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
            else if(p.getPresentacion().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
            else if(p.getTipo().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
        }
        AdapterProducto adapterProducto = new AdapterProducto(mlista);
        pr.setAdapter(adapterProducto);

    }
}