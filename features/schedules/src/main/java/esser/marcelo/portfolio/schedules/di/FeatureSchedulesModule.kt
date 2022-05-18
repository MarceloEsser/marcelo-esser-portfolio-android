package esser.marcelo.portfolio.schedules.di

import esser.marcelo.portfolio.schedules.scenes.schedules.SchedulesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

val schedulesViewModel = module {
    viewModel {
        SchedulesViewModel(
            get(),
            Dispatchers.IO
        )
    }
}