package esser.marcelo.portfolio.core.callAdapter

import esser.marcelo.portfolio.core.wrapper.ApiResult
import kotlinx.coroutines.Deferred
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

class CallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) is Deferred<*>) {
            val parameterUpperBound = getParameterUpperBound(0, returnType as ParameterizedType)

            val rawType = getRawType(parameterUpperBound)

            if (rawType != ApiResult::class.java)
                throw IllegalArgumentException("The type must be a ApiResult")

            if (parameterUpperBound !is ParameterizedType)
                throw IllegalArgumentException("resource must be parameterized")


            val bodyType = getParameterUpperBound(0, parameterUpperBound)

            return MyCallAdapter<Any>(bodyType)
        }
        throw IllegalArgumentException("return type must be Deferred")
    }
}