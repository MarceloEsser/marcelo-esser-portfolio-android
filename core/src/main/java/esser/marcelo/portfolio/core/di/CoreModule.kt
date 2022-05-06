package esser.marcelo.portfolio.core.di

import esser.marcelo.portfolio.core.sogalServices.ISogalAPI
import esser.marcelo.portfolio.core.NetworkHandler
import esser.marcelo.portfolio.core.callAdapter.CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

val coreModule = module {
    single { retrofit() }

    single { get<Retrofit>().create(ISogalAPI::class.java) }
}

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(NetworkHandler.gsonBuilder()))
    .addCallAdapterFactory(CallAdapterFactory())
    .client(NetworkHandler.httpClient())
    .baseUrl("http://sogal.com.br/")
    .build()