package com.example.seminar_1.core.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.seminar_1.data.local.TligDatabase

class DailyMessageWorker(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val database = TligDatabase.getDatabase(context)
        val randomMessage = database.messagesDao().getRandomMessage()

        if (randomMessage != null) {
            NotificationHelper.showNotification(
                context,
                "Poselství pro dnešní den",
                randomMessage.title
            )
        }

        return Result.success()
    }
}
