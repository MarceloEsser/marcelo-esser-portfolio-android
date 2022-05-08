package esser.marcelo.portfolio.scenes.line

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.Status
import esser.marcelo.portfolio.core.model.busLine.BaseLine
import esser.marcelo.portfolio.core.repository.service.SogalServiceDelegate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    var line: BaseLine? = null

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()

    private fun loadLines() {
        viewModelScope.launch(dispatcher) {
            service.getLines().collect { resource ->
                resource.data?.let { data ->
                    when (resource.requestStatus) {
                        Status.success -> {
                            _lines.postValue(data)
                            _allLines.postValue(data)
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