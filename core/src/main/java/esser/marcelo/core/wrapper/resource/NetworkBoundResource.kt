package esser.marcelo.core.wrapper.resource

import esser.marcelo.core.wrapper.ApiEmptyResult
import esser.marcelo.core.wrapper.ApiFailureResult
import esser.marcelo.core.wrapper.ApiResult
import esser.marcelo.core.wrapper.ApiSuccessResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.FlowCollector

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 08/05/22
 */

open class NetworkBoundResource<ResultType, RequestType>(
    private val collector: FlowCollector<Resource<ResultType>>,
    private val call: Deferred<ApiResult<RequestType>>,
    private val processResponse: (response: RequestType?) -> ResultType,
) {

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        collector.emit(Resource.loading())
        fetchFromNetwork()
        return this
    }

    private suspend fun fetchFromNetwork() {
        return when (val result = call.await()) {
            is ApiSuccessResult -> {
                val process = processResponse(result.body)
                collector.emit(Resource.success(process))
            }
            is ApiEmptyResult -> {
                collector.emit(Resource.success(null))
            }
            is ApiFailureResult -> {
                collector.emit(Resource.error(result.message))
            }
        }
    }

}