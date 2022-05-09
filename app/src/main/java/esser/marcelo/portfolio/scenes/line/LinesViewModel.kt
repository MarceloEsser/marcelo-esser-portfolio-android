package esser.marcelo.portfolio.scenes.line

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class LinesViewModel(
    private val service: SogalServiceDelegate,
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

    var line: BusLine? = null

    private fun loadLines() {
        //TODO: implement status - Loading
        viewModelScope.launch(dispatcher) {
            service.getLines().collect { resource ->
                when (resource.requestStatus) {
                    Status.success -> {
                        if (resource.data == null) {
                            _error.postValue(resource.message ?: "")
                        }
                        resource.data?.let { data ->
                            _lines.postValue(data)
                            _allLines.postValue(data)
                        }
                    }
                    Status.error -> _error.postValue(resource.message ?: "")
                    else -> {
                        _error.postValue(resource.message ?: "")
                    }
                }

            }
        }
    }

    fun filterBy(text: String) {
        if (_allLines.value.isNullOrEmpty()) return

        val linesToFilter = _allLines.value
        val filter: List<BusLine>? = linesToFilter?.filter {
            it.name.lowercase().contains(text.lowercase())
                    || it.code.lowercase().contains(text)
        }
        _lines.value = filter ?: listOf()
    }
}