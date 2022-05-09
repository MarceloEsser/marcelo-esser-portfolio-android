package esser.marcelo.portfolio.schedules.scenes.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.model.busSchedule.LineSchedules
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SchedulesViewModel(
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var line: BusLine? = null

    private val _schedule = MutableLiveData<LineSchedules>()
    val schedule: LiveData<LineSchedules> by lazy {
        loadSchedules()
        return@lazy _schedule
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private fun loadSchedules() {
        if (line == null) {
            _error.postValue("linha não pode estar nula")
            return
        }
        line?.let {
            viewModelScope.launch(dispatcher) {
                service.getSchedules(it).collect { resource ->
                    resource.data?.let { data ->
                        when (resource.requestStatus) {
                            Status.success -> {
                                _schedule.postValue(data)
                            }
                            Status.error -> _error.postValue(resource.message ?: "")
                            else -> {
                                _error.postValue(resource.message ?: "")
                            }
                        }
                    }
                }
            }
        }

    }

}