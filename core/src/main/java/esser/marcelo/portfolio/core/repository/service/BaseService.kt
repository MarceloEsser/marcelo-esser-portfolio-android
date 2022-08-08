package esser.marcelo.portfolio.core.repository.service

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.*
import androidx.work.ExistingWorkPolicy
import kotlin.reflect.KClass


sealed class BaseService(private val context: Context) {
    private val instance = WorkManager.getInstance(context)

    fun <WorkerType : ListenableWorker> canEnqueueOneTimeWorker(
        worker: KClass<WorkerType>,
        data: Data? = null,
        tag: String = worker.java.name
    ) {
        if (!isNetworkAvailable()) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = OneTimeWorkRequest.Builder(worker.java)
                .addTag(tag)
                .setConstraints(constraints)

            data?.let {
                work.setInputData(it)
            }

            instance.enqueueUniqueWork(tag, ExistingWorkPolicy.KEEP, work.build())
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> true
        }
    }

}
