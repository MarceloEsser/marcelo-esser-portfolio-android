package esser.marcelo.portfolio.core.repository.service

import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface ISogalService {
    fun getSchedules(busLine: BusLine): Flow<Resource<LineSchedules>>
    fun getLines(): Flow<Resource<List<BusLine>>>
}