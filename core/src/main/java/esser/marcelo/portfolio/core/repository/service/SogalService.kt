package esser.marcelo.portfolio.core.repository.service

import androidx.annotation.VisibleForTesting
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.DataBoundResource
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.wrapper.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

interface SogalServiceDelegate {
    fun getSchedules(busLine: BusLine, shouldCreateCall: Boolean = true): Flow<Resource<LineSchedules>>
    fun getLines(shouldCreateCall: Boolean = true): Flow<Resource<List<BusLine>>>
}

class SogalService(
    private val _mApi: ISogalAPI,
    private val dao: AppDao,
) : SogalServiceDelegate {
    private val search_lines_action = "buscaLinhas"

    override fun getSchedules(
        busLine: BusLine,
        shouldCreateCall: Boolean
    ): Flow<Resource<LineSchedules>> {
        return DataBoundResource(
            shouldCreateCall = { shouldCreateCall },
            loadFromDatabase = {
                dao.getLineSchedule(busLine.id)
            },
            createCall = {
                if (busLine.way != null) {
                    _mApi.postSogalSchedules(busLine.way!!.code, busLine.code)
                } else {
                    Resource.error(message = "Line way must not be null", null)
                }
            },
            saveCallResult = { scheduleResponse ->
                scheduleResponse.lineId = busLine.id
                dao.insertSchedule(scheduleResponse)
            }
        ).build()

    }

    override fun getLines(shouldCreateCall: Boolean): Flow<Resource<List<BusLine>>> {
        return DataBoundResource(
            loadFromDatabase = { dao.getLines() },
            shouldCreateCall = { shouldCreateCall },
            createCall = { _mApi.postSogalLines(search_lines_action) },
            saveCallResult = { lines ->
                dao.insertLines(lines)
            }
        ).build()
    }
}