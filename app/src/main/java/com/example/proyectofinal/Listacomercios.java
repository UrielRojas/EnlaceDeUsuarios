package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.proyectofinal.Buscador.Comerciantes;
import com.example.proyectofinal.adapter.AdapterComerciantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Listacomercios extends AppCompatActivity {

    DatabaseReference mDataBase;
    ArrayList<Comerciantes> list;
    RecyclerView pr;
    SearchView searchView;
    AdapterComerciantes adapter;
    LinearLayoutManager lm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listacomercios);

        String ID = getIntent().getStringExtra("ID");
        String Usuario = getIntent().getStringExtra("Usuario");
        String Objeto = getIntent().getStringExtra("Objeto");
        String Ob1  = "";

        if (Objeto.equals("Coffe_mate")){
            Ob1 = "Coffemate";
        }
        else if(Objeto.equals("Sabritas_Naturales")){
            Ob1 = "Sabritas";
        }
        else {
            Ob1 = Objeto;
        }


        mDataBase = FirebaseDatabase.getInstance().getReference().child("Comercios");
        //
        pr =findViewById(R.id.ListaCM);
        searchView = findViewById(R.id.searchComercio);
        lm = new LinearLayoutManager(this);
        pr.setLayoutManager(lm);
        list = new ArrayList<>();
        adapter = new AdapterComerciantes(list);
        pr.setAdapter(adapter);

        String finalOb = Ob1;
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 :snapshot.getChildren()){
                        try {
                            //String ID =snapshot.child("ID").getValue().toString();
                            String Producto = snapshot1.child("Productos_Registrados").child(finalOb).child("Nombre").getValue().toString();
                            if (Producto.equals(finalOb) ){
                                Comerciantes pro = snapshot1.getValue(Comerciantes.class);
                                list.add(pro);
                            }
                        }
                        catch(Exception e) {
                            //  Block of code to handle errors
                        }
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
        ArrayList<Comerciantes>mlista = new ArrayList<>();

        for(Comerciantes p:list){
            if(p.getUsuario().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
            else if(p.getComercio().toLowerCase().contains(s.toLowerCase())){
                mlista.add(p);
            }
        }
        AdapterComerciantes adapterC = new AdapterComerciantes(mlista);
        pr.setAdapter(adapterC);

    }
}