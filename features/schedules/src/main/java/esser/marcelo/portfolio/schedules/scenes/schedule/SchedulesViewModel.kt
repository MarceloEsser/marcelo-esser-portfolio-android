package esser.marcelo.portfolio.schedules.scenes.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.Schedule
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import esser.marcelo.portfolio.schedules.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

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

    lateinit var listMap: Map<Int, List<Schedule>?>

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private fun loadSchedules() {
        if (line == null) {
            _error.postValue("you need a line to get the schedules")
            return
        }

        line?.let {
            viewModelScope.launch(dispatcher) {
                service.getSchedules(it).collect { resource ->
                    _status.postValue(resource.requestStatus)

                    if (resource.requestStatus == Status.success) {
                        if (resource.data == null)
                            _error.postValue(resource.message ?: "")

                        resource.data?.let { data ->
                            listMap = mapOf(
                                R.id.action_workingdays to data.workingDays,
                                R.id.action_saturday to data.saturdays,
                                R.id.action_sunday to data.sundays,
                            )
                            _schedule.postValue(data)
                        }
                    }
                    if (resource.requestStatus == Status.error) {
                        _error.postValue(resource.message ?: "")
                    }
                }
            }
        }

    }
}