package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Entity
data class Workingday(

    @PrimaryKey(autoGenerate = true)
    var workingdayId: Long? = null,

    @ColumnInfo(name = "lineId")
    var workindayKey: Long? = null,
):BaseSchedule()