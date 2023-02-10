package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.*;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText CorreoLogin,Contrasena;
    Button Ingreso;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDataBase,ContrasenaModificada,ComercioUser;
    Dialog dialog;
    String Tipo_Perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null; //AFIRMAMOS QUE EL TITULO NO ES NULO
        actionBar.setTitle("Login");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CorreoLogin = findViewById(R.id.CorreoIngreso);
        Contrasena = findViewById(R.id.ContrasenaIngreso);
        Ingreso = findViewById(R.id.Ingreso);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Login.this); //Iniciamos el progresdialog
        dialog = new Dialog(Login.this); //Iniciamos el dialogo

        //Al presionar el boton realizara la opcion de ingreso
        Ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debemos convertir a STR el correo y la contrasena
                String correo = CorreoLogin.getText().toString();
                String password = Contrasena.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches())
                {
                    CorreoLogin.setError("Correo no valido");
                    CorreoLogin.setFocusable(true);
                }else if (password.length()<6) {
                   Contrasena.setError("La contraseña debe ser igual o mayor a 6 caracteres");
                   Contrasena.setFocusable(true);
                }else {
                    //ejecutamos la funcion login
                    LoginUsuario(correo,password);
                }
            }
        });

    }



    //Metodo de inicio de session
    private void LoginUsuario(String correo ,String pass)
    {
        progressDialog.setTitle("Ingresando");
        progressDialog.setMessage("Espere un momento..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se inicio la session de manera correcta entonces...
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            VerficarPerfil(user.getUid());
                            mDataBase = FirebaseDatabase.getInstance().getReference();
                            CambiosContrasena(pass,user.getUid());
                            //Accedemos a la base de datos
                            mDataBase.child("USUARIOS_STOREAGE").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                       String usuarios = snapshot.child("usuarios").getValue().toString();
                                       Intent intent = new Intent(Login.this, Inicio.class);
                                       intent.putExtra("Usuario",usuarios);
                                       intent.putExtra("ID",user.getUid());
                                       intent.putExtra("Contrasena",pass);
                                       intent.putExtra("Correo",correo);
                                       intent.putExtra("TipoUsuario",Tipo_Perfil);
                                       startActivity(intent);
                                       //startActivity(new Intent(Login.this,Inicio.class));
                                       assert user != null;
                                       // Se afirma que no es nulo el usuario ,se obtiene el correo electronico
                                       Toast.makeText(Login.this, "Bienvenid@ "+usuarios, Toast.LENGTH_SHORT).show();
                                       finish();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Algo Ocurrio mal :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                //nos muestra el mensaje
                No_Inicio();
                //Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void CambiosContrasena(String password,String id){
        ContrasenaModificada = FirebaseDatabase.getInstance().getReference("USUARIOS_STOREAGE");
        Map<String,Object> Contrasena = new HashMap<>();
        Contrasena.put("pass",password);
        ContrasenaModificada.child(id).updateChildren(Contrasena);

    }
    private void EncontarReferencia (String dato){
        //Iniciamos FireBaseDatabase

    }

    //Dialogo personalisado
    private void No_Inicio(){
        Button ok_no;
        dialog.setContentView(R.layout.no_session);//Conexion con el layaut de no session
        ok_no = dialog.findViewById(R.id.Aceptar);

        //Al presionar el boton se cierra el cuadro
        ok_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);//Se puede presionar Fuera de la animacion
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void VerficarPerfil(String id){
        ComercioUser = FirebaseDatabase.getInstance().getReference();
        //Accedemos a la base de datos
        ComercioUser.child("Comercios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Este Perfil si tiene perfil de comerciante
                if(snapshot.exists()){
                    Tipo_Perfil = "Comerciante";
                }else{
                    Tipo_Perfil = "Usuario";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}