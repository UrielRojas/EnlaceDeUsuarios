package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inicio extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageButton CerrarSesion ,TomarFoto,Comercio,Modificar,Buscar;
    ImageView Fotico;
    final int CapturarImagen = 1;
    TextView Objeto , Titulo,Tipo_Perfil,Comer;
    BitmapDrawable drawable;
    String Fotico_img;
    Bitmap bitmap;
    DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null; //AFIRMAMOS QUE EL TITULO NO ES NULO
        actionBar.setTitle("Inicio");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String user = getIntent().getStringExtra("Usuario");
        String ID = getIntent().getStringExtra("ID");
        String Password = getIntent().getStringExtra("Contrasena");
        String Correo = getIntent().getStringExtra("Correo");
        String Perfil = getIntent().getStringExtra("TipoUsuario");
        CerrarSesion = findViewById(R.id.CerrarSesion);
        TomarFoto = findViewById(R.id.Fotografia);
        Fotico = findViewById(R.id.Fotico);
        Comercio = findViewById(R.id.comercio);
        Titulo = findViewById(R.id.Titulo);
        Modificar = findViewById(R.id.Datos);
        Tipo_Perfil = findViewById(R.id.Tipo_Perfil);
        Comer = findViewById(R.id.Comercio);
        Buscar = findViewById(R.id.Buscar);
        Tipo_Perfil.setText("Perfil de "+Perfil);
        Titulo.setText("Bienvenido "+user);
        titulocomercio(Perfil);


        //Objeto = findViewById(R.id.Objeto);
        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Metodo para cerrar sesion
                CerrarS();

            }
        });

        TomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CapturarImagen);
            }
        });

        Comercio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBase = FirebaseDatabase.getInstance().getReference();
                //Accedemos a la base de datos
                mDataBase.child("Comercios").child(ID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Este Perfil si tiene perfil de comerciante
                        if(snapshot.exists()){
                            Intent intent = new Intent(Inicio.this, Perfil_Comerciante.class);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario",user);
                            startActivity(intent);
                        }else{
                            //Metodo para crear un comercio
                            Intent intent = new Intent(Inicio.this, RegistroComerciantes.class);
                            intent.putExtra("ID", ID);
                            intent.putExtra("Usuario",user);
                            startActivity(intent);
                            //startActivity(new Intent(Inicio.this,RegistroComerciantes.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        });

        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Datos_Pedir_Contrasena.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Contrasena",Password);
                intent.putExtra("Correo",Correo);
                startActivity(intent);
            }
        });

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, buscar_productos.class);
                intent.putExtra("ID", ID);
                intent.putExtra("Contrasena",Password);
                intent.putExtra("Correo",Correo);
                startActivity(intent);
            }
        });

    }

    private void titulocomercio(String P){
        if (P.equals("Usuario")){
            Comer.setText("REGISTRAR COMERCIO");
        }else{
            Comer.setText("PERFIL DE COMERCIO");
        }
    }
    @Override
    protected void onStart() {
        //llamamos al metodo para iniciar la Actividad
        VerificacionSession();
        super.onStart();
    }

    //Metodo que verifica si el usuario ya incio session previamente

    private void VerificacionSession(){
        //Si el usuario inicio sesion no dirije a esta actividad
        if (firebaseUser != null){
            Toast.makeText(Inicio.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(Inicio.this,UsuariosRegistro.class));
            finish();
        }
    }

    private void CerrarS() {
        //Se cierra la sesion del usuario
        firebaseAuth.signOut();
        Toast.makeText(Inicio.this, "Se ha cerrado Sesíon", Toast.LENGTH_SHORT).show();
        //no dirije al menu principal
        startActivity(new Intent(Inicio.this,UsuariosRegistro.class));

    }


    protected void onActivityResult(int requestCode, int resultCode ,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode ==  CapturarImagen && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap1 = (Bitmap)extras.get("data");
            Fotico.setImageBitmap(bitmap1);
            Intent intent = new Intent(this, FotoArchivo.class);
            intent.putExtra("Foto", bitmap1);
            try{
                if (!Python.isStarted()) {
                    Python.start(new AndroidPlatform(this));
                }

                Python py = Python.getInstance();

                drawable = (BitmapDrawable)Fotico.getDrawable();
                bitmap = drawable.getBitmap();
                Fotico_img = (String) getStringFotico(bitmap);

                PyObject pyo = py.getModule("Deteccion");
                PyObject obj = pyo.callAttr("Deteccion_obj", Fotico_img);

                intent.putExtra("Objeto", obj.toString());
                //intent.putExtra("Objeto", "Gansito");
                startActivity(intent);

            }catch (Exception o){
                Objeto.setText("No se ejecuto");
            }
            //

        }
    }

    private Object getStringFotico(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private String GenerarNombre(){
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }

}