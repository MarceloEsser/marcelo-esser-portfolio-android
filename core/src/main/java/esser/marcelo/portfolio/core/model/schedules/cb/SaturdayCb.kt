package esser.marcelo.portfolio.core.model.schedules.cb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import esser.marcelo.busoclock.model.schedules.BaseSchedule

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Entity
data class SaturdayCb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saturday_cb_id")
    var id: Long? = null,

    @ColumnInfo(name = "lineId")
    var saturdayKey: Long? = null,
) : BaseSchedule()