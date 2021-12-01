package com.example.workmanagersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.example.workmanagersample.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        init()


    }


    private fun init(){

        binding.apply {

            startWorkBtn.setOnClickListener {
                //val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
                val constraints = Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val data = workDataOf("WORK_MESSAGE" to "Work Completed!")
                val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build()
/*                val data = Data.Builder()
                    .putString("WORK_MESSAGE", "WORK Completed")
                    .build()*/

                val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                    5,
                    TimeUnit.MINUTES,
                    1, TimeUnit.MINUTES
                ).build()
                workManager.enqueue(workRequest)
            }
            workStatusBtn.setOnClickListener {
                val toast = Toast.makeText(
                    this@MainActivity,
                    "The work status is: ${WorkStatusSingleton.workMessage} ",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }

            resetStatusBtn.setOnClickListener {
                WorkStatusSingleton.workComplete = false
            }
            workOnUtThreadBtn.setOnClickListener {
                Thread.sleep(10000)
                WorkStatusSingleton.workComplete = true
            }

        }


    }
}