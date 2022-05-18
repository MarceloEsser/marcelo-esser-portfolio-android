package esser.marcelo.portfolio.core.repository.service

import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface ISogalService {
    fun getSchedules(busLine: BusLine, shouldCreateCall: Boolean = true): Flow<Resource<LineSchedules>>
    fun getLines(shouldCreateCall: Boolean = true): Flow<Resource<List<BusLine>>>
}