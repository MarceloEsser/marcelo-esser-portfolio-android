package esser.marcelo.portfolio.core.di

import androidx.room.Room
import androidx.work.WorkerFactory
import esser.marcelo.portfolio.core.repository.service.ISogalAPI
import esser.marcelo.portfolio.core.NetworkHandler
import esser.marcelo.portfolio.core.callAdapter.CallAdapterFactory
import esser.marcelo.portfolio.core.repository.database.AppDatabase
import esser.marcelo.portfolio.core.repository.service.SogalServiceImpl
import esser.marcelo.portfolio.core.repository.service.ISogalService
import esser.marcelo.portfolio.core.workManager.factory.SogalWorkeFactory
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
    factory {
        Room.databaseBuilder(get(), AppDatabase::class.java, "esser_portfolio").build()
    }

    single { get<AppDatabase>().getAppDao() }

    single { get<Retrofit>().create(ISogalAPI::class.java) }
    single<ISogalService> { SogalServiceImpl(get(), get(), get()) }
}

val workManager = module {
    factory<WorkerFactory> { SogalWorkeFactory(get()) }
}

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(NetworkHandler.gsonBuilder()))
    .addCallAdapterFactory(CallAdapterFactory())
    .client(NetworkHandler.httpClient())
    .baseUrl("http://sogal.com.br/")
    .build()