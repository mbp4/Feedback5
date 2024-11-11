package com.example.feedback5

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feedback5.MainActivity.Companion.ACCION_BORRAR
import com.example.feedback5.MainActivity.Companion.ACCION_FAV
import com.example.feedback5.MainActivity.Companion.ACCION_VER
import com.example.feedback5.MainActivity.Companion.ACCION_XFAV
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ListadoNovelasFragmento:Fragment() {
    private lateinit var recyclerNovelas: RecyclerView
    private lateinit var novelasAdapter: NovelasAdapter
    private var listadoNovelasF: MutableList<Novela> = mutableListOf()
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerNovelas = view.findViewById(R.id.recyclerNovelas)
        recyclerNovelas.layoutManager = LinearLayoutManager(context)

        cargarNovelas()
    }

    private fun cargarNovelas() {
        db.collection("dbNovelas")
            .get()
            .addOnSuccessListener { documentos ->
                listadoNovelasF.clear()
                for (documento in documentos) {
                    val novela = documento.toObject(Novela::class.java)
                    listadoNovelasF.add(novela)
                }
                prepararRecyclerView()
            }
            .addOnFailureListener({ exception ->
                Log.w(TAG, "Error al obtener las novelas de la base de datos", exception)
            })
    }

    private fun verNovela(novela: Novela) {

        val fragment = VerNovelaFragment().apply {
            arguments = Bundle().apply {
                putString("Titulo", novela.titulo)
                putString("Autor", novela.autor)
                putInt("Año", novela.año)
                putString("Sinopsis", novela.sinopsis)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmento, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun prepararRecyclerView() {
        //configuramos el recycler para que sea una lista vertical
        novelasAdapter = NovelasAdapter(listadoNovelasF) { novela, accion ->
            if (accion == ACCION_VER) {
                verNovela(novela)
            } else if (accion == ACCION_BORRAR) {
                borrarNovela(novela)
            } else if (accion == ACCION_FAV) {
                añadirFavorita(novela)
            } else if (accion == ACCION_XFAV){
                xFav(novela)
            }
            //hacemos que el metodo identifique si el usuario quiere borrar, ver la novela o añadir o borrar de favoritos y se ejecuta la accion elegida

        }
        recyclerNovelas.adapter = novelasAdapter //asignamos el recycler a la vista
        novelasAdapter.notifyDataSetChanged() //notificamos al adaptador que los datos han cambiado
    }

    private fun xFav(novela: Novela) {
        db.collection("dbNovelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.update("fav", false)
                }
                mostrarNovelas()
                //Toast.makeText(this, "Novela eliminada de favoritos", Toast.LENGTH_SHORT).show()
            }
    }
    //metodo para borrar de favoritos la novela


    private fun borrarNovela(novela: Novela) {
        db.collection("dbNovelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.delete()
                }
                mostrarNovelas()
                //Toast.makeText(this, "Novela eliminada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                //Toast.makeText(this, "Error al borrar la novela", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Error al borrar la novela de la base de datos", e)
            }
    }
    //con este metodo buscamos la novela que el usuario ha elegido y la borramos de la base de datos dandole a la vez un mensaje para que sepa que se ha eliminado correctamente

    private fun añadirFavorita(novela: Novela) {
        db.collection("dbNovelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.update("fav", true)
                }
                mostrarNovelas()
                //Toast.makeText(this, "Novela añadida a favoritos", Toast.LENGTH_SHORT).show()
            }
    }
    //metodo para cambiar el atributo de la novela y añadirlo a favoritos

    private fun mostrarNovelas() {
        db.collection("dbNovelas")
            .get()
            .addOnSuccessListener { documentos ->
                listadoNovelasF.clear()
                for (documento in documentos) {
                    val novela = documento.toObject(Novela::class.java)
                    listadoNovelasF.add(novela)
                }
                prepararRecyclerView()
            }
            .addOnFailureListener({ exception ->
                //Toast.makeText(this, "Error al obtener las novelas", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Error al obtener las novelas de la base de datos", exception)
            }) //mandamos un error a la logcat y al usuario en el caso de que no se pueda obtener la lista de novelas de la base de datos
        //creamos un metodo que hace que muestre la lista de novelas de la base de datos y las añada a una lista de novelas para uqe se muestren en la principal
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.cambiar(this)
    }

    override fun onResume() {
        super.onResume()
        mostrarNovelas()
    }

}