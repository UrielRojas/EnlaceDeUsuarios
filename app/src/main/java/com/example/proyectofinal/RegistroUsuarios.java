package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistroUsuarios extends AppCompatActivity {

    EditText Correo, Contrasena,Contrasena2, Nombres, Apellidos, NombreUsuario;
    Button REGISTRARUSUARIO;

    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null; //AFIRMAMOS QUE EL TITULO NO ES NULO
        actionBar.setTitle("RegistroUsuarios");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.CorreoIngreso);
        Contrasena = findViewById(R.id.Cont1);
        Contrasena2 = findViewById(R.id.Cont2);
        Nombres = findViewById(R.id.Nomb);
        Apellidos = findViewById(R.id.Apelli);
        NombreUsuario = findViewById(R.id.UserNom);
        REGISTRARUSUARIO= findViewById(R.id.ModificarDatos);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegistroUsuarios.this); //Iniciamos el progresdialog
        REGISTRARUSUARIO.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String correo = Correo.getText().toString();
                String pass = Contrasena.getText().toString();
                //validamos
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setFocusable(true);
                }else if (pass.length()<6){
                    Contrasena.setError("Contraseña debe ser mayor a 6");
                }else if (Contrasena.getText().toString().equals(Contrasena2.getText().toString())){
                    REGISTRAR(correo,pass);
                }else{
                    Contrasena.setError("Las contraseñas no coinciden");
                }

            }
        });

    }

    //Metodo que registra Usuarios
    private void REGISTRAR (String correo ,String pass){
        progressDialog.setTitle("Registrando");
        progressDialog.setMessage("Espere un momento..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Registro Existoso
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //Registro de datos
                            assert user != null; //El usuario no es nulo
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Contrasena.getText().toString();
                            String nombre = Nombres.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            String Usuario = NombreUsuario.getText().toString();
                            //Creamos un Hashmap para mandar datos a firebase
                            HashMap<Object,String> DatosUsuario = new HashMap<>();
                            DatosUsuario.put("uid",uid);
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("pass",pass);
                            DatosUsuario.put("nombre",nombre);
                            DatosUsuario.put("apellidos",apellidos);
                            DatosUsuario.put("usuarios",Usuario);
                            //Imagen vacia por el momento
                            DatosUsuario.put("imagen","");
                            //Iniciamos la instancia de base de datos de firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //Se crea la base de datos
                            DatabaseReference reference = database.getReference("USUARIOS_STOREAGE");
                            //Base de datos no Relacional = "USUARIOS_STOREAGE"
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(RegistroUsuarios.this,"Se registro el Dato",Toast.LENGTH_SHORT).show();
                            //Toast.makeText("Se registro el dato",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistroUsuarios.this,Login.class));
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistroUsuarios.this,"Algo Ocurrio mal",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegistroUsuarios.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}