package esser.marcelo.portfolio.core.sogalServices

import esser.marcelo.portfolio.core.model.LinesDTO
import esser.marcelo.portfolio.core.model.SogalResponse
import esser.marcelo.portfolio.core.wrapper.resource.Resource
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
    suspend fun getSchedules(lineWay: String, lineCode: String): Flow<Resource<SogalResponse?>>
    suspend fun getLines(): Flow<Resource<List<LinesDTO>?>>
    suspend fun getSogalItineraries(lineCode: String): Flow<Resource<LinesDTO?>>
}

class SogalService(
    private val _mApi: ISogalAPI
) : SogalServiceDelegate {

    private val SEARCH_LINES: String = "buscaLinhas"
    private val SEARCH_ITINERARIES: String = "buscaItinerarios"

    override suspend fun getSchedules(
        lineWay: String,
        lineCode: String
    ): Flow<Resource<SogalResponse?>> {
        return flow {
//            NetworkBoundResource(
//                collector = this,
//                call = _mApi.getSogalSchedulesAsync(lineWay, lineCode),
//                processResponse = { it }
//            ).build()
        }
    }

    override suspend fun getLines(): Flow<Resource<List<LinesDTO>?>> {
        return flow {
//            NetworkBoundResource(
//                collector = this,
//                processResponse = { it },
//                call = _mApi.getSogalListAsync(SEARCH_LINES)
//            ).build()
        }
    }

    override suspend fun getSogalItineraries(lineCode: String): Flow<Resource<LinesDTO?>> {
        return flow {
//            NetworkBoundResource(
//                collector = this,
//                processResponse = { it },
//                call = _mApi.getSogalItinerariesAsync(SEARCH_ITINERARIES, lineCode)
//            ).build()
        }
    }
}