package com.example.feedback5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class VerNovelaFragment : Fragment() {
    private lateinit var txt1: TextView
    private lateinit var txt2: TextView
    private lateinit var txt3: TextView
    private lateinit var txt4: TextView
    private lateinit var btnVolver: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt1 = view.findViewById(R.id.txt1)
        txt2 = view.findViewById(R.id.txt2)
        txt3 = view.findViewById(R.id.txt3)
        txt4 = view.findViewById(R.id.txt4)
        btnVolver = view.findViewById(R.id.btnVolver)

        arguments?.let {
            txt1.text = it.getString("Titulo")
            txt2.text = it.getString("Autor")
            txt3.text = it.getInt("Año").toString()
            txt4.text = it.getString("Sinopsis")
        }

        btnVolver.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as? MainActivity)?.cambiar(ListadoNovelasFragmento())
        }

    }
}
