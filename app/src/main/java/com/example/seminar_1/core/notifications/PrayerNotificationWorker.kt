package com.example.seminar_1.core.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PrayerNotificationWorker(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        NotificationHelper.showNotification(
            context,
            "Tři denní modlitby",
            "Je čas na modlitbu: Zasvěcení, sv. Michael a sv. Bernard."
        )

        return Result.success()
    }
}
