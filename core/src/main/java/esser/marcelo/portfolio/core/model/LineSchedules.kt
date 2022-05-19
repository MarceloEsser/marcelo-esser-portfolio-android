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
    var id: Long,

    @ColumnInfo(name = "line_id")
    var lineId: Long,

    @ColumnInfo(name = "line_way")
    var lineWayCode: String,

    @SerializedName(value = "horariosBCUteis")
    @ColumnInfo(name = "schedule_working_days_bc")
    @TypeConverters(Converters::class)
    var workingDaysBC: List<Schedule>? = null,

    @SerializedName(value = "horariosBCSabado")
    @ColumnInfo(name = "schedule_saturdays_bc")
    @TypeConverters(Converters::class)
    var saturdaysBC: List<Schedule>? = null,

    @SerializedName(value = "horariosBCDomingo")
    @ColumnInfo(name = "schedule_sundays_bc")
    @TypeConverters(Converters::class)
    var sundaysBC: List<Schedule>? = null,

    //Working days schedules
    @SerializedName(value = "horariosCBUteis")
    @ColumnInfo(name = "schedule_working_days_cb")
    @TypeConverters(Converters::class)
    var workingDaysCB: List<Schedule>? = null,

    @SerializedName(value = "horariosCBSabado")
    @ColumnInfo(name = "schedule_saturdays_cb")
    @TypeConverters(Converters::class)
    var saturdaysCB: List<Schedule>? = null,

    @SerializedName(value = "horariosCBDomingo")
    @ColumnInfo(name = "schedule_sundays_cb")
    @TypeConverters(Converters::class)
    var sundaysCB: List<Schedule>? = null,
) {
    fun replaceSchedules(newData: LineSchedules?) {
        if(newData == null) return
        if (newData.lineWayCode == LineWay.bcCode) {
            workingDaysBC = newData.workingDaysBC
            saturdaysBC = newData.saturdaysBC
            sundaysBC = newData.sundaysBC
            return
        }

        workingDaysCB = newData.workingDaysCB
        saturdaysCB = newData.saturdaysCB
        sundaysCB = newData.sundaysCB
    }

    val workingDays: List<Schedule>?
        get() {
            return if (lineWayCode == LineWay.bcCode) workingDaysBC
            else workingDaysCB
        }

    val saturdays: List<Schedule>?
        get() {
            return if (lineWayCode == LineWay.bcCode) saturdaysBC
            else saturdaysCB
        }

    val sundays: List<Schedule>?
        get() {
            return if (lineWayCode == LineWay.bcCode) sundaysBC
            else sundaysCB
        }

}