package esser.marcelo.core.sogal.service

import android.content.Context
import androidx.work.Data
import esser.marcelo.portfolio.core.DataBoundResource
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.repository.service.BaseService
import esser.marcelo.portfolio.core.wrapper.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

class SogalServiceImpl(
    private val _mApi: ISogalAPI,
    private val dao: AppDao,
    context: Context
) : ISogalService, BaseService(context = context) {

    private val searchLinesAction = "buscaLinhas"

    override fun getSchedules(
        busLine: BusLine,
    ): Flow<Resource<LineSchedules>> {
        var localLineSchedules: LineSchedules?

        return DataBoundResource(
            shouldFetch = { true },
            loadFromDatabase = {
                localLineSchedules =
                    dao.getLineBy(busLine.name, busLine.code, busLine.way ?: "")
                return@DataBoundResource localLineSchedules
            },
            createCall = {
                val data = Data.Builder()
                data.putAll(busLine.toMap())
                val tag =
                    "${esser.marcelo.core.sogal.workManager.SchedulesWorker::class.java.name}_${busLine.code}_${busLine.way}"
                canEnqueueOneTimeWorker(esser.marcelo.core.sogal.workManager.SchedulesWorker::class, data.build(), tag = tag)

                _mApi.postSogalSchedules(busLine.way ?: "", busLine.code)
            },
            saveCallResult = { lineSchedulesResult ->
                lineSchedulesResult.line = busLine

                lineSchedulesResult.let { line ->
                    line.workingDays?.let { workingDays ->
                        workingDays.forEach { element -> element.workingDayKey = line.line?.id }
                        dao.insertWorkingDays(workingDays)
                    }
                    line.saturdays?.let { saturdays ->
                        saturdays.forEach { element -> element.saturdayKey = line.line?.id }
                        dao.insertSaturdays(saturdays)
                    }
                    line.sundays?.let { sundays ->
                        sundays.forEach { element -> element.sundayKey = line.line?.id }
                        dao.insertSundays(sundays)
                    }
                }

//                dao.insertSchedule(lineSchedulesResult)
            }
        ).build()
    }

    override fun getLines(): Flow<Resource<List<BusLine>>> {
        var localLines: List<BusLine>? = null
        return DataBoundResource(
            shouldFetch = { true },
            loadFromDatabase = {
                localLines = dao.getLines()
                return@DataBoundResource localLines
            },
            createCall = {
                _mApi.postSogalLines(searchLinesAction)
            },
            saveCallResult = { lines ->
                localLines?.let {
                    for (line in lines) {
                        line.id = it.find { element -> line.code == element.code }?.id ?: 0
                    }
                }
                dao.insertLines(lines)
            }
        ).build()
    }
}