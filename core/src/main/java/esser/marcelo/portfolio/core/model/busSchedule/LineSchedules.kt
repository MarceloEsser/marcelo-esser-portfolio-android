package esser.marcelo.portfolio.core.model.busSchedule

import com.google.gson.annotations.SerializedName

class LineSchedules(
    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    var workingDays: List<Workingday>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    var saturdays: List<Saturday>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    var sundays: List<Sunday>? = null,
)