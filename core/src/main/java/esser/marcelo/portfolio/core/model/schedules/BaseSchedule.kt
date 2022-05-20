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

open class BaseSchedule(
    @SerializedName("hora")
    @ColumnInfo(name = "hour")
    var hour: String = "",

    @SerializedName("apd")
    @ColumnInfo(name = "accessible")
    var accessible: String = "N"
) {
    fun isAccessible(): Boolean {
        return accessible == "S"
    }
}
