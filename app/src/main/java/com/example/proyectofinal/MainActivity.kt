package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinal.R
import android.content.Intent
import android.os.Handler
import com.example.proyectofinal.UsuariosRegistro

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Duracion de tiempo de carga de pantalla en Microsegundos
        val tiempo = 2000
        Handler().postDelayed({ //Se ejecuta la pantalla principal de la aplicacion
            val ola = Intent(this@MainActivity, UsuariosRegistro::class.java)
            startActivity(ola)
        }, tiempo.toLong())
    }
}