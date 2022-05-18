package esser.marcelo.portfolio.core.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import esser.marcelo.portfolio.core.repository.service.ISogalService
import esser.marcelo.portfolio.core.workManager.SchedulesWorker

class SogalWorkeFactory(private val service: ISogalService) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SchedulesWorker::class.java.name ->
                SchedulesWorker(service, appContext, workerParameters)
            //TODO: LinesWorker
            else -> return null
        }
    }
}