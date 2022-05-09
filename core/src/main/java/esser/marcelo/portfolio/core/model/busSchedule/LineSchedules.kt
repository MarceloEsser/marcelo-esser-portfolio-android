package esser.marcelo.portfolio.core.model.busSchedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.helper.Converters

@Entity
class LineSchedules(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "line_id")
    var lineId: Long,

    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    @ColumnInfo(name = "schedule_working_days")
    @TypeConverters(Converters::class)
    var workingDays: List<BaseSchedule>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    @ColumnInfo(name = "schedule_saturdays")
    @TypeConverters(Converters::class)
    var saturdays: List<BaseSchedule>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    @ColumnInfo(name = "schedule_sundays")
    @TypeConverters(Converters::class)
    var sundays: List<BaseSchedule>? = null,
)