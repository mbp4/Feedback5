package com.example.feedback5

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FavoritasWidget: AppWidgetProvider() {

    private val db: FirebaseFirestore = Firebase.firestore

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            actualizarWidget(context, appWidgetManager, appWidgetId)
        }
    }


    private fun actualizarWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_novelas_favoritas)

        views.removeAllViews(R.id.widgetFavo)

        db.collection("dbNovelas")
            .whereEqualTo("fav", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val titulo = document.getString("titulo") ?: "Sin título"
                    val autor = document.getString("autor") ?: "Sin autor"

                    val novelaView = RemoteViews(context.packageName, R.layout.widget_item)
                    novelaView.setTextViewText(R.id.txtTitulo, titulo)
                    novelaView.setTextViewText(R.id.txtAutor, autor)

                    views.addView(R.id.widgetFavo, novelaView)
                }

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
            .addOnFailureListener { exception ->
                Log.w("FavoritasWidget", "Error al obtener las favoritas", exception)
            }
    }


}