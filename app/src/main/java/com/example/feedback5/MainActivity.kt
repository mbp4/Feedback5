package com.example.feedback5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var btnAlta: Button
    private lateinit var btnAcercaDe: Button
    private lateinit var btnTema: ToggleButton
    private lateinit var btnConfig: ImageButton
    private lateinit var novelasAdapter: NovelasAdapter
    private var listadoNovelasF: MutableList<Novela> = mutableListOf()
    private val db: FirebaseFirestore = Firebase.firestore
    //creamos todas las variables necesarias para hacer la activity funcional

    companion object {
        const val ACCION_VER = 1
        const val ACCION_BORRAR = 2
        const val ACCION_FAV = 3
        const val ACCION_XFAV = 4
    }
    //declaramos todas las variables necesarias para hacer la aplicación funcional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //se establece la vista de la actividad

        btnAlta = findViewById(R.id.btnAlta)
        btnAcercaDe = findViewById(R.id.btnAcercaDe)
        btnTema = findViewById(R.id.btnTema)
        btnConfig = findViewById(R.id.btnAjustes)

        //asociamos a los botones el identificador del boton del layout

        btnTema.setChecked(LoginActivity.modoOscuro)

        btnTema.setOnClickListener {
            if (btnTema.isChecked()) {
                activarModo(true)
            } else {
                activarModo(false)
            }
        }

        btnAlta.setOnClickListener {
            val intent = Intent(this, NuevaNovelaActivity::class.java)
            startActivity(intent)
        } //boton que nos lleva a la actividad de alta de novelas

        btnAcercaDe.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
            //boton que nos lleva a la actividad de acerca de
        }

        btnConfig.setOnClickListener {
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }


        if (savedInstanceState == null) {
            cambiar(ListadoNovelasFragmento())
        }



    }

    private fun activarModo(modoOscuro: Boolean) {
        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        delegate.applyDayNight()
        LoginActivity.modoOscuro = modoOscuro
        db.collection("dbUsuarios")
            .whereEqualTo("mail", LoginActivity.mail)
            .get()
            .addOnSuccessListener { documentos ->
                val id = documentos.documents[0].id
                db.collection("dbUsuarios")
                    .document(id)
                    .update("modo", modoOscuro)
            }
    }

    fun cambiar(fragmento: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmento, fragmento)
            .commit()
    }

}


