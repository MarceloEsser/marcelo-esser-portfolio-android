package esser.marcelo.portfolio.schedules

import androidx.lifecycle.ViewModel
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SchedulesViewModel(
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    // TODO: Implement the ViewModel
}