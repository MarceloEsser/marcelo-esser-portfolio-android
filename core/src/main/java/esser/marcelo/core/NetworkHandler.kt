package esser.marcelo.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 08/05/22
 */


object NetworkHandler {

    private val logginInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    fun gsonBuilder(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .setLenient().create()
    }

    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    }
}