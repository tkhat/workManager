package com.example.workmanagersample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context, params: WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {
        val messsage = inputData.getString("WORK_MESSAGE")
        Thread.sleep(10000)
        WorkStatusSingleton.workComplete = true
        messsage?.let {
            WorkStatusSingleton.workMessage = it
        }
        return Result.success()
    }

}