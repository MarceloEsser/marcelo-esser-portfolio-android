package esser.marcelo.portfolio.core.repository.wrapper

import org.json.JSONObject
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

sealed class ApiResult<T> {
    companion object {

        fun <T> create(throwable: Throwable): ApiFailureResult<T> {
            return ApiFailureResult(throwable.message)
        }

        fun <T> create(response: Response<T>): ApiResult<T> {
            if (response.isSuccessful) {

                if (responseIsNotEmpty(response)) {
                    return ApiSuccessResult(response.body())
                }

                return ApiEmptyResult()

            }

            val errorBody: JSONObject = createJsonObject(response)

            val errorMessage: String = getErrorMessageFrom(errorBody)

            return ApiFailureResult(errorMessage)

        }

        private fun getErrorMessageFrom(errorBody: JSONObject): String {
            var errorMessage = "unknown error"
            if (errorBody.has("error")) {
                errorMessage = errorBody["error"] as String
            }
            return errorMessage
        }

        private fun <T> createJsonObject(response: Response<T>) =
            JSONObject(response.errorBody()?.charStream()?.readText() ?: "{}")

        private fun <T> responseIsNotEmpty(response: Response<T>) =
            response.body() != null && response.code() != 204
    }
}

class ApiEmptyResult<T> : ApiResult<T>()
data class ApiFailureResult<T>(val message: String?) : ApiResult<T>()
data class ApiSuccessResult<T>(val body: T?) : ApiResult<T>()