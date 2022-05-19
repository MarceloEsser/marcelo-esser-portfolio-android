package esser.marcelo.portfolio.core.repository.service

import android.content.Context
import androidx.work.Data
import esser.marcelo.portfolio.core.DataBoundResource
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.workManager.SchedulesWorker
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

    private val search_lines_action = "buscaLinhas"

    override fun getSchedules(
        busLine: BusLine,
    ): Flow<Resource<LineSchedules>> {
        var localLineSchedules: LineSchedules? = null

        return DataBoundResource(
            shouldFetch = { true },
            loadFromDatabase = {
                localLineSchedules = dao.getLineSchedule(busLine.id)

                return@DataBoundResource localLineSchedules
            },
            createCall = {
                if (busLine.way != null) {
                    val data = Data.Builder()
                    data.putAll(busLine.toMap())
                    val tag =
                        "${SchedulesWorker::class.java.name}_${busLine.code}_${busLine.way?.code}"
                    canEnqueueOneTimeWorker(SchedulesWorker::class, data.build(), tag = tag)

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
                    dao.updateSchedule(it)
                }

                if (localLineSchedules == null)
                    dao.insertSchedule(lineSchedulesResult)

            }
        ).build()

    }

    override fun getLines(): Flow<Resource<List<BusLine>>> {
        return DataBoundResource(
            shouldFetch = { true },
            loadFromDatabase = {
                dao.getLines()
            },
            createCall = {
                _mApi.postSogalLines(search_lines_action)
            },
            saveCallResult = { lines ->
                dao.insertLines(lines)
            }
        ).build()
    }
}