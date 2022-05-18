package esser.marcelo.portfolio.core.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.core.repository.service.ISogalService

class SchedulesWorker(
    private val service: ISogalService,
    appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        var result = Result.retry()
        val map = inputData.keyValueMap
        val line = BusLine(
            name = map["name"].toString(),
            code = map["code"].toString(),
            id = -1,
        )

        line.way = LineWay.availableWays().find {
            it.code == map["way"]
        }

        service.getSchedules(busLine = line).collect {
            print("working")
            if (it.status == Status.Success) {
                result = Result.success()
            }
            if (it.status == Status.Error) {
                result = Result.failure()
            }
        }

        return result
    }
}