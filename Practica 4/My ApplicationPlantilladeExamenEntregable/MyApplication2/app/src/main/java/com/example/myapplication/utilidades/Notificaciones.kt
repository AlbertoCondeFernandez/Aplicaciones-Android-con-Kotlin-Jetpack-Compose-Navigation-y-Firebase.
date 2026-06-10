package com.example.myapplication.utilidades

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity
import kotlin.random.Random

///clase auxiliar para gestionar y mostrar notificaciones
//es el ecargadod e avisar al usuario
class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val channelId =
        "canal_concesionario" // ID único (como dirección) del canal de notificaciones

    fun showSimpleNotification(titulo: String, cuerpo: String) {
// Builder con contentIntent para la creación de la notificación

        //intet en este caso te dirá qué pantalla quiero abrir cuando el usuario pulse la notificación"
        //por ente todas las notificaciones de arriba te mandarán al Main activity
        val intent = Intent(context, MainActivity::class.java).apply {
// Flags de cómo abrir la Activity
// NEW_TASK: Abre nueva instancia si app cerrada CLEAR_TASK: Elimina todas las instancias anteriores va directo a MainActivity
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        // Creo un ID aleatorio para distinguir notificaciones.
        val notificationId = Random.nextInt() // Genera un número aleatorio

        // PendingIntent (lo que se ejecuta al tocar la notificación), EL  permiso especial para que Android ejecute ese Intent más tard
        val pendingIntent = PendingIntent.getActivity(
            context, // Quién (app) lo pide, el contexto , la app actual
            notificationId, // ID único para distinguir diferentes PendingIntents con acciones distintas
            intent, // Intent que lleva dentro, creado anteriormente
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
// UPDATE_CURRENT: Reemplaza si ya existe
// IMMUTABLE: No lo modifica (seguridad para Android 12 o superior)
        )

        //un builder donde monto la applicacion paso a paso
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(titulo) // Título
            .setContentText(cuerpo) // Texto / cuerpo de la notificación
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono del sistema
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Tipo de prioridad
            .setAutoCancel(true) // Que la notificación se descarte al tocarla
            .setContentIntent(pendingIntent) //abre  con click intentn pag t3-27
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(cuerpo)
                    .setBigContentTitle(titulo)
            )
            .build() // Crea objeto final

        //para ver la notificación
        notificationManager.notify(Random.nextInt(), notification)

    }
}