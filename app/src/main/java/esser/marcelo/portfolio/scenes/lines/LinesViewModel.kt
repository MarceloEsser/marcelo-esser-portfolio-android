package esser.marcelo.portfolio.scenes.lines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.repository.service.ISogalService
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class LinesViewModel(
    private val service: ISogalService,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _allLines = MutableLiveData<List<BusLine>>()
    private val _lines = MutableLiveData<List<BusLine>>()
    val lines: LiveData<List<BusLine>> by lazy {
        loadLines()
        return@lazy _lines
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    var line: BusLine? = null

    private fun loadLines() {
        viewModelScope.launch(dispatcher) {
            service.getLines().collect { resource ->
                _status.postValue(resource.status)

                if (resource.status == Status.Success) {
                    if (resource.data == null) {
                        _error.postValue(resource.message ?: "")
                    }
                    resource.data?.let { data ->
                        _lines.postValue(data)
                        _allLines.postValue(data)
                    }
                }
                if (resource.status == Status.Error) {
                    _error.postValue(resource.message ?: "")
                }

            }
        }
    }

    fun filterBy(text: String) {
        if (_allLines.value.isNullOrEmpty()) return

        val linesToFilter = _allLines.value
        val filter: List<BusLine>? = linesToFilter?.filter {
            it.name.lowercase(Locale.getDefault()).contains(text.lowercase())
                    || it.code.lowercase(Locale.getDefault()).contains(text)
        }
        _lines.value = filter ?: listOf()
    }
}