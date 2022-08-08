package esser.marcelo.portfolio.core.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.model.schedules.cb.SaturdayCb
import esser.marcelo.portfolio.core.model.schedules.cb.SundayCb
import esser.marcelo.portfolio.core.model.schedules.cb.WorkingDayCb

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

data class LineSchedules(

    @Embedded var line: BusLine? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    @SerializedName(value = "horariosBCUteis", alternate = ["horariosCBUteis"])
    var workingDays: List<WorkingDayCb>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    @SerializedName(value = "horariosBCSabado", alternate = ["horariosCBSabado"])
    var saturdays: List<SaturdayCb>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    var sundays: List<SundayCb>? = null,
)