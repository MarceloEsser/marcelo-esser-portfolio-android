package esser.marcelo.portfolio.core.repository.service

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.*
import kotlin.reflect.KClass


sealed class BaseService(private val context: Context) {
    private val instance = WorkManager.getInstance(context)

    fun <WorkerType : ListenableWorker> canEnqueueOneTimeWorker(
        worker: KClass<WorkerType>,
        data: Data? = null,
        tag: String = worker.java.name
    ) {
        if (needToSchedule()) {
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

    private fun needToSchedule(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return true
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return true

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false
            else -> true
        }
    }

}
