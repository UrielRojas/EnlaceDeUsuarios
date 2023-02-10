package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Modificar_Datos extends AppCompatActivity {


    DatabaseReference databaseReference,databaseReference2;
    private ProgressDialog progressDialog;
    Button Modificar_Datos,RecuperarContraseña;
    EditText Nombre, Apellidos, NombreUsuario;
    FirebaseAuth auth;
    FirebaseUser User;
    ProgressDialog progress;
    DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);

        String Id = getIntent().getStringExtra("ID");
        String Correo = getIntent().getStringExtra("Correo");
        databaseReference = FirebaseDatabase.getInstance().getReference("USUARIOS_STOREAGE");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Comercios");
        Nombre = findViewById(R.id.Nomb);
        Apellidos = findViewById(R.id.Apelli);
        NombreUsuario = findViewById(R.id.UserNom);
        Modificar_Datos = findViewById(R.id.ModificarDatos);
        RecuperarContraseña = findViewById(R.id.Restablecer);
        auth = FirebaseAuth.getInstance();
        User = auth.getCurrentUser();
        progress = new ProgressDialog(Modificar_Datos.this);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        Modificar_Datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Modificamos los datos de Usuario
                Map<String,Object> DatosUsuario = new HashMap<>();
                Map<String,Object> DatosUsuarioComercio = new HashMap<>();
                DatosUsuario.put("nombre",Nombre.getText().toString());
                DatosUsuario.put("apellidos",Apellidos.getText().toString());
                DatosUsuario.put("usuarios",NombreUsuario.getText().toString());
                DatosUsuarioComercio.put("Usuario",NombreUsuario.getText().toString());
                databaseReference.child(Id).updateChildren(DatosUsuario);
                mDataBase.child("Comercios").child(Id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if(snapshot.exists()){
                            databaseReference2.child(Id).updateChildren(DatosUsuarioComercio);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                Intent intent = new Intent(Modificar_Datos.this, Inicio.class);
                intent.putExtra("Usuario",NombreUsuario.getText().toString());
                intent.putExtra("ID",Id);
                startActivity(intent);
                //startActivity(new Intent(Login.this,Inicio.class));
                // Se afirma que no es nulo el usuario ,se obtiene el correo electronico
                Toast.makeText(Modificar_Datos.this, "Datos modificados Correctamente ", Toast.LENGTH_SHORT).show();
            }
        });

        RecuperarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Espere un momento..");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                enviarCorreo(Correo);
            }
        });


    }

    private void enviarCorreo(String correo){
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Modificar_Datos.this, "Revise su correo para restaurar su contraseña ", Toast.LENGTH_SHORT).show();
                    CerrarS();
                }else{
                    Toast.makeText(Modificar_Datos.this, "Algo Ocurrio mal :(,revise sus datos ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Modificar_Datos.this, Inicio.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CerrarS() {
        //Se cierra la sesion del usuario
        auth.signOut();
        Toast.makeText(Modificar_Datos.this, "Se ha cerrado Sesíon", Toast.LENGTH_SHORT).show();
        //no dirije al menu principal
        startActivity(new Intent(Modificar_Datos.this,UsuariosRegistro.class));

    }
}