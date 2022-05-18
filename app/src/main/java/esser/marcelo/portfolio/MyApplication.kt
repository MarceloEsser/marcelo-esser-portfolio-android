package esser.marcelo.portfolio

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import esser.marcelo.portfolio.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

class MyApplication : Application(), KoinComponent, Configuration.Provider {
    private val delegatingWorkerFactory = DelegatingWorkerFactory()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }

        setupWorkManagerFactory()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(delegatingWorkerFactory)
            .build()
    }

    private fun setupWorkManagerFactory() {
        getKoin().getAll<WorkerFactory>()
            .forEach {
                delegatingWorkerFactory.addFactory(it)
            }
    }

}