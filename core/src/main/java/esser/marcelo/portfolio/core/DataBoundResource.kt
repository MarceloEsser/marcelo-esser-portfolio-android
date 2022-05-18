package esser.marcelo.portfolio.core

import esser.marcelo.portfolio.core.wrapper.Resource
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
    private val shouldFetch: () -> Boolean = { false },
    private val createCall: suspend () -> Resource<ResultType>,
    private val saveCallResult: (suspend (item: ResultType) -> Unit),
    private val loadFromDatabase: (suspend () -> ResultType?),
) {

    fun build(): Flow<Resource<ResultType>> {
        return flow {
            emit(Resource.loading())
            fetchFromDatabase()
            fetchFromNetwork()
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromDatabase() {
        val value = loadFromDatabase.invoke()
        value?.let { databaseValue ->
            emit(Resource.success(databaseValue))
        }
    }

    private suspend fun FlowCollector<Resource<ResultType>>.fetchFromNetwork() {
        val result = createCall.invoke()

        if (result.status == Status.Success) {
            result.data?.let {
                saveCallResult.invoke(it)
            }

            if (shouldFetch.invoke()) {
                fetchFromDatabase()
                return
            }

            emit(Resource.success(result.data))
            return
        }

        if (result.status == Status.Error) {
            emit(Resource.error(result.message))
            return
        }

        emit(Resource.success(null))
    }

}