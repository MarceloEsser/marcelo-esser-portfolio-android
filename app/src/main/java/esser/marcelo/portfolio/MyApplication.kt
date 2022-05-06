package esser.marcelo.portfolio

import android.app.Application
import esser.marcelo.portfolio.di.appModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}