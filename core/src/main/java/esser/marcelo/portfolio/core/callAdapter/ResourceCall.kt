package esser.marcelo.portfolio.core.callAdapter

import esser.marcelo.portfolio.core.wrapper.ApiResult
import esser.marcelo.portfolio.core.wrapper.Resource
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 07/05/22
 */

class ResourceCall<R>(
    private val call: Call<R>
) : Call<Resource<R>> {

    override fun enqueue(callback: Callback<Resource<R>>) {
        return call.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(
                    this@ResourceCall,
                    Response.success(ApiResult.create(response))
                )
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(
                    this@ResourceCall,
                    Response.success(ApiResult.create(t))
                )
            }

        })
    }

    override fun request(): Request {
        return call.request()
    }

    override fun clone(): Call<Resource<R>> {
        return ResourceCall(call)
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }


    override fun timeout(): Timeout {
        return call.timeout()
    }

    override fun execute(): Response<Resource<R>> {
        throw UnsupportedOperationException("Unsupported execute")
    }

}