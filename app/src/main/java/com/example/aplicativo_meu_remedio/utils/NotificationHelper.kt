package com.example.aplicativo_meu_remedio.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.aplicativo_meu_remedio.R

object NotificationHelper {

    private const val CHANNEL_ID = "medicine_reminder_channel"
    private const val CHANNEL_NAME = "Lembretes de Remédios"
    private const val CHANNEL_DESCRIPTION = "Notificações para lembrar os horários dos remédios"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showMedicineNotification(
        context: Context,
        medicineName: String,
        medicineTime: String
    ) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hora do remédio 💊")
            .setContentText("Está na hora de tomar $medicineName às $medicineTime.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(
            medicineName.hashCode(),
            notification
        )
    }
}