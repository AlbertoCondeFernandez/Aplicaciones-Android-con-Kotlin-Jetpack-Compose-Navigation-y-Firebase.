package com.example.myapplication.utilidades

//el canal página 18 t3
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

//REGSITRA EL CANAL de notificaciones
//REQUIS APPI NO USAR @RequiresApi

//se ejecuta cuando arranca la app
class NotificationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_concesionario",          // ID interno del canal
                "Avisos de Coches",             // nombre visible para el usuario
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Mensaje sobre mis coches"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // Aquí registramos el canal en Android
            manager.createNotificationChannel(canal)
        }
    }
}
