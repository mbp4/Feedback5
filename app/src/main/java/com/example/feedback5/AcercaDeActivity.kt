package com.example.feedback5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class AcercaDeActivity: AppCompatActivity(){
    private var handler: Handler ?= Handler(Looper.getMainLooper()) //handler accede al hilo principal
    private var colorRunnable: Runnable? = null

    private lateinit var btnAceptar: Button //creamos un boton que asociaremos al boton de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de) //asociamos el correspondiente layout

        btnAceptar = findViewById(R.id.aceptar)

        btnAceptar.setOnClickListener {
            finish()
            //cuando se pulse el boton termianmos la aplicacion y volvemos a la actividad inicial
        }

        /*
        Thread(object : Runnable {
            override fun run() {
                var i = -1
                while (true){
                    val color = if (i == -1){
                        android.graphics.Color.RED
                    } else{
                        android.graphics.Color.GREEN
                    }
                    i *= -1
                    btnAceptar.setBackgroundColor(color) //esto es una referencia que apunta al AcercaDeActivity y produce la fuga de memoria
                    Thread.sleep(100)
                }
            }
        }).start()

         */

        var i = -1
        colorRunnable = object : Runnable {
            override fun run() {
                val color = if (i == -1){
                    android.graphics.Color.RED
                } else{
                    android.graphics.Color.GREEN
                }
                i *= -1
                btnAceptar.setBackgroundColor(color)
                handler?.postDelayed(this, 100)
            }
        }
        handler?.post(colorRunnable!!)


    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(colorRunnable!!) //mata al hilo, terminando el parpadeo
        handler = null
        colorRunnable = null
    }
}