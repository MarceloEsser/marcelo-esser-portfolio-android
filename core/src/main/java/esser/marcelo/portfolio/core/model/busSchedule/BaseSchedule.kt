package esser.marcelo.portfolio.core.model.busSchedule

import androidx.room.ColumnInfo
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
    @ColumnInfo(name= "apd")
    var apd: String = "N"
) {
    fun isApd(): Boolean {
        return apd == "S"
    }
}
