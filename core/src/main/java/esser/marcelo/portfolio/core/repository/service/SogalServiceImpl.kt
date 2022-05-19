package esser.marcelo.portfolio.core.repository.service

import android.content.Context
import androidx.work.Data
import esser.marcelo.portfolio.core.DataBoundResource
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.workManager.SchedulesWorker
import esser.marcelo.portfolio.core.wrapper.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

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
        var localLineSchedules: LineSchedules? = null

        return DataBoundResource(
            shouldFetch = { true },
            loadFromDatabase = {
                localLineSchedules = dao.getLineSchedule(busLine.id)
                print("locallinelength ${localLineSchedules?.id}")
                return@DataBoundResource localLineSchedules
            },
            createCall = {
                if (busLine.way != null) {
                    val data = Data.Builder()
                    data.putAll(busLine.toMap())
                    val tag =
                        "${SchedulesWorker::class.java.name}_${busLine.code}_${busLine.way?.code}"
//                    canEnqueueOneTimeWorker(SchedulesWorker::class, data.build(), tag = tag)

                    _mApi.postSogalSchedules(busLine.way!!.code, busLine.code)
                } else {
                    Resource.error(message = "Line way must not be null", null)
                }
            },
            saveCallResult = { lineSchedulesResult ->
                //TODO: isn't inserting new schedules
                lineSchedulesResult.lineWayCode = busLine.way?.code ?: ""

                localLineSchedules?.let {
                    lineSchedulesResult.id = it.id
                    it.replaceSchedules(lineSchedulesResult)
                    try {
                        dao.updateSchedule(it)
                    } catch (e: Exception) {
                        throw e
                    }
                }

                if (localLineSchedules == null)
                    try {
                        dao.insertSchedule(lineSchedulesResult)
                    } catch (e: Exception) {
                        throw e
                    }

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