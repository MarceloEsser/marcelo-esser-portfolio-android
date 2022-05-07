package esser.marcelo.portfolio.core.repository.service

import esser.marcelo.portfolio.core.repository.DataBoundResource
import esser.marcelo.portfolio.core.repository.database.AppDao
import esser.marcelo.portfolio.core.model.busSchedule.SchedulesResponse
import esser.marcelo.portfolio.core.repository.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

interface SogalServiceDelegate {
    suspend fun getSchedules(lineWay: String, lineCode: String): Flow<Resource<SchedulesResponse?>>
//    suspend fun getLines(): Flow<Resource<List<BusLine>?>>
//    suspend fun getSogalItineraries(lineCode: String): Flow<Resource<BusLine?>>
}

class SogalService(
    private val _mApi: ISogalAPI,
    private val dao: AppDao,
) : SogalServiceDelegate {

    override suspend fun getSchedules(
        lineWay: String,
        lineCode: String
    ): Flow<Resource<SchedulesResponse?>> {
        return flow {
            DataBoundResource(
                fetchFromDataBase = { dao.getSchedules() },
                shouldFetchFromNetwork = { true },
                fetchFromNetwork = { _mApi.getSogalSchedulesAsync(lineWay, lineCode) },
                saveCallResult = { scheduleResponse ->
                    //TODO: Insert each schedule (working day, saturday and sunday)
                    dao.insertSchedules(scheduleResponse)
                }
            ).build()
        }
    }
//
//    override suspend fun getLines(): Flow<Resource<List<BusLine>?>> {
//        return flow {
////            NetworkBoundResource(
////                collector = this,
////                processResponse = { it },
////                call = _mApi.getSogalListAsync(SEARCH_LINES)
////            ).build()
//        }
//    }
//
//    override suspend fun getSogalItineraries(lineCode: String): Flow<Resource<BusLine?>> {
//        return flow {
////            NetworkBoundResource(
////                collector = this,
////                processResponse = { it },
////                call = _mApi.getSogalItinerariesAsync(SEARCH_ITINERARIES, lineCode)
////            ).build()
//        }
//    }
}