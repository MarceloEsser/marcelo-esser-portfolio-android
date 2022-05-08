package esser.marcelo.portfolio.core.repository.service

import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.DataBoundResource
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.model.busSchedule.LineSchedules
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
    suspend fun getSchedules(lineWay: String, lineCode: String): Flow<Resource<LineSchedules>>
    suspend fun getLines(): Flow<Resource<List<BusLine>>>
//    suspend fun getSogalItineraries(lineCode: String): Flow<Resource<BusLine?>>
}

class SogalService(
    private val _mApi: ISogalAPI,
    private val dao: AppDao,
) : SogalServiceDelegate {

    override suspend fun getSchedules(
        lineWay: String,
        lineCode: String
    ): Flow<Resource<LineSchedules>> {
        return DataBoundResource(
            fetchFromDataBase = { LineSchedules() },
            shouldFetchFromNetwork = { true },
            fetchFromNetwork = { _mApi.postSogalSchedulesAsync(lineWay, lineCode) },
            saveCallResult = { scheduleResponse ->
                //TODO: Insert each schedule (working day, saturday and sunday)
            }
        ).build()

    }

    override suspend fun getLines(): Flow<Resource<List<BusLine>>> {
        return DataBoundResource(
            fetchFromDataBase = { dao.getLines() },
            shouldFetchFromNetwork = { true },
            fetchFromNetwork = { _mApi.postSogalLines("buscaLinhas") },
            saveCallResult = { lines ->
                dao.insertLines(lines)
            }
        ).build()
    }
}