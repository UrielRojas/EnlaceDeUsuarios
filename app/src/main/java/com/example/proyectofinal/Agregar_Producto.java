package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Agregar_Producto extends AppCompatActivity {


    ImageButton Gansito, Dulcigomas, Coffemate, sabritas, whiskas,Capsun;
    DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        String ID = getIntent().getStringExtra("ID");
        String Usuario = getIntent().getStringExtra("Usuario");


        Gansito = findViewById(R.id.Gansito);
        Dulcigomas = findViewById(R.id.Dulcigomas);
        Coffemate = findViewById(R.id.Coffemate);
        sabritas = findViewById(R.id.Sabritas);
        whiskas = findViewById(R.id.Whiskas);
        Capsun = findViewById(R.id.Capsun);

        Gansito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Gansito").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Gansito");
                            DatosProducto.put("Empresa", "Marinela");
                            DatosProducto.put("Tipo", "Dulces y Chocolates");
                            DatosProducto.put("Presentacion", "24 gr");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Gansito").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Dulcigomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Dulcigomas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Dulcigomas");
                            DatosProducto.put("Empresa", "Ricolino");
                            DatosProducto.put("Tipo", "Dulces y Chocolates");
                            DatosProducto.put("Presentacion", "63 gr");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Dulcigomas").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Coffemate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Coffemate").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Coffemate");
                            DatosProducto.put("Empresa", "Nestle");
                            DatosProducto.put("Tipo", "Cafes y Suplementos");
                            DatosProducto.put("Presentacion", "600 gr");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Coffemate").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        sabritas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Sabritas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Sabritas");
                            DatosProducto.put("Empresa", "Sabritas");
                            DatosProducto.put("Tipo", "Botanas");
                            DatosProducto.put("Presentacion", "42 gr");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Sabritas").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        whiskas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Whiskas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Whiskas");
                            DatosProducto.put("Empresa", "Whiskas");
                            DatosProducto.put("Tipo", "Animales");
                            DatosProducto.put("Presentacion", "85 gr");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Whiskas").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Capsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).child("Productos_Registrados").child("Capsun").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if (snapshot.exists()) {
                            Toast.makeText(Agregar_Producto.this, "Producto ya agregado ", Toast.LENGTH_SHORT).show();
                        } else {
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Agregar_Producto.this, RegistroComerciantes.class);
                            HashMap<Object, String> DatosProducto = new HashMap<>();
                            DatosProducto.put("Nombre", "Capsun");
                            DatosProducto.put("Empresa", "Heinz");
                            DatosProducto.put("Tipo", "Suplementos");
                            DatosProducto.put("Presentacion", "14 oz");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("Comercios");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(ID).child("Productos_Registrados").child("Capsun").setValue(DatosProducto);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario", Usuario);
                            Toast.makeText(Agregar_Producto.this, "Producto Agregado Correctamente ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}