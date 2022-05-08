package esser.marcelo.portfolio.schedules.di

import esser.marcelo.portfolio.schedules.SchedulesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val schedulesViewModel = module {
    viewModel {
        SchedulesViewModel(
            get(),
            Dispatchers.IO
        )
    }
}