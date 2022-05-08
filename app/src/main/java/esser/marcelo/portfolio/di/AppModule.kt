package esser.marcelo.portfolio.di

import esser.marcelo.portfolio.MainActivity
import esser.marcelo.portfolio.core.di.coreModule
import esser.marcelo.portfolio.line.LinesFragment
import esser.marcelo.portfolio.line.LinesViewModel
import esser.marcelo.portfolio.schedules.SchedulesFragment
import esser.marcelo.portfolio.schedules.SchedulesViewModel
import esser.marcelo.portfolio.schedules.di.schedulesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

val mainActivityScope = module {
    scope<MainActivity> {
        fragment { LinesFragment() }
        fragment { SchedulesFragment() }
    }
}
val viewModelModule = module {
    viewModel {
        LinesViewModel(
            get(),
            Dispatchers.IO
        )
    }
}

val appModule = listOf(
    coreModule,
    viewModelModule,
    mainActivityScope,
    schedulesViewModel
)