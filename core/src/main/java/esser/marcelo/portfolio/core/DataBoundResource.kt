package esser.marcelo.portfolio.core

import esser.marcelo.portfolio.core.wrapper.ApiEmptyResult
import esser.marcelo.portfolio.core.wrapper.ApiFailureResult
import esser.marcelo.portfolio.core.wrapper.ApiResult
import esser.marcelo.portfolio.core.wrapper.ApiSuccessResult
import esser.marcelo.portfolio.core.wrapper.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

open class DataBoundResource<ResultType>(
    private val shouldFetchFromNetwork: () -> Boolean,
    private val fetchFromNetwork: suspend () -> ApiResult<ResultType>,
    private val saveCallResult: (suspend (item: ResultType) -> Unit),
    private val fetchFromDataBase: (suspend () -> ResultType?),
) {

    fun build(): Flow<Resource<ResultType>> {
        return flow {
            emit(Resource.loading())

            fetchFromDatabase()
            //TODO: Review this rule
            if (shouldFetchFromNetwork.invoke())
                fetchFromNetwork()
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromDatabase() {
        val value = fetchFromDataBase.invoke()
        value?.let { databaseValue ->
            emit(Resource.success(databaseValue))
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromNetwork() {
        return when (val result = fetchFromNetwork.invoke()) {
            is ApiSuccessResult -> {
                result.body?.let {
                    saveCallResult.invoke(it)
                }
                emit(Resource.success(result.body))
            }
            is ApiEmptyResult -> {
                emit(Resource.success(null))
            }
            is ApiFailureResult -> {
                emit(Resource.error(result.message))
            }
        }
    }

}