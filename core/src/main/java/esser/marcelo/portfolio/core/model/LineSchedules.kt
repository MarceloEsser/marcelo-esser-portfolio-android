package esser.marcelo.portfolio.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.helper.Converters

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

@Entity
class LineSchedules(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "line_id")
    var lineId: Long,

    @SerializedName(value = "horariosBCUteis", alternate = ["horariosCBUteis"])
    @ColumnInfo(name = "schedule_working_days")
    @TypeConverters(Converters::class)
    var workingDays: List<Schedule>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = ["horariosCBSabado"])
    @ColumnInfo(name = "schedule_saturdays")
    @TypeConverters(Converters::class)
    var saturdays: List<Schedule>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    @ColumnInfo(name = "schedule_sundays")
    @TypeConverters(Converters::class)
    var sundays: List<Schedule>? = null,
)