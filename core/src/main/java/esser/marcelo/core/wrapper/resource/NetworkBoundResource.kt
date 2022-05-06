package esser.marcelo.core.wrapper.resource

import esser.marcelo.core.wrapper.ApiEmptyResult
import esser.marcelo.core.wrapper.ApiFailureResult
import esser.marcelo.core.wrapper.ApiResult
import esser.marcelo.core.wrapper.ApiSuccessResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 08/05/22
 */

open class NetworkBoundResource<ResultType>(
    private val fetchFromDataBase: (suspend () -> ResultType?)? = null,
    private val saveCallResult: (suspend (item: ResultType) -> Unit)? = null,
    private val fetchFromNetwork: suspend () -> ApiResult<ResultType>
) {

    fun build(): Flow<Resource<ResultType>> {
        return flow {
            emit(Resource.loading())

            fetchFromDatabase()
            fetchFromNetwork()
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromDatabase() {
        val value = fetchFromDataBase?.invoke()
        emit(Resource.success(value))
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromNetwork() {
        return when (val result = fetchFromNetwork.invoke()) {
            is ApiSuccessResult -> {
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