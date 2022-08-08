package esser.marcelo.portfolio.core.model.schedules.bc

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
data class SundayBc(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sunday_bc_id")
    var id: Long? = null,

    @ColumnInfo(name = "lineId")
    var sundayKey: Long? = null,
) : BaseSchedule()