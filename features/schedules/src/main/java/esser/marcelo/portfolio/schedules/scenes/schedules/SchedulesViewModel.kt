package esser.marcelo.portfolio.schedules.scenes.schedules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.core.sogal.service.ISogalService
import esser.marcelo.portfolio.schedules.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class SchedulesViewModel(
    private val service: esser.marcelo.core.sogal.service.ISogalService,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var line: BusLine? = null

    private val _schedule = MutableLiveData<LineSchedules>()
    val schedule: LiveData<LineSchedules> by lazy {
        loadSchedules()
        return@lazy _schedule
    }

    lateinit var listMap: Map<Int, List<BaseSchedule>?>

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
                    _status.postValue(resource.status)

                    if (resource.status == Status.Success) {
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
                    if (resource.status == Status.Error) {
                        _error.postValue(resource.message ?: "")
                    }
                }
            }
        }

    }
}