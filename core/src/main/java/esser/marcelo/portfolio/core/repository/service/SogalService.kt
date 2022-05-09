package esser.marcelo.portfolio.core.repository.service

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
    suspend fun getSchedules(busLine: BusLine): Flow<Resource<LineSchedules>>
    suspend fun getLines(): Flow<Resource<List<BusLine>>>
}

class SogalService(
    private val _mApi: ISogalAPI,
    private val dao: AppDao,
) : SogalServiceDelegate {
    private val search_lines_action = "buscaLinhas"
    override suspend fun getSchedules(
        busLine: BusLine
    ): Flow<Resource<LineSchedules>> {
        return DataBoundResource(
            fetchFromDataBase = { dao.getLineSchedule(busLine.id) },
            shouldFetch = { true },
            fetchFromNetwork = {
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

    override suspend fun getLines(): Flow<Resource<List<BusLine>>> {
        return DataBoundResource(
            fetchFromDataBase = { dao.getLines() },
            shouldFetch = { true },
            fetchFromNetwork = { _mApi.postSogalLines(search_lines_action) },
            saveCallResult = { lines ->
                dao.insertLines(lines)
            }
        ).build()
    }
}