package esser.marcelo.portfolio.core.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

open class Schedule(
    @SerializedName("hora")
    @ColumnInfo(name = "hour")
    var hour: String = "",

    @SerializedName("apd")
    @ColumnInfo(name= "accessible")
    var accessible: String = "N"
) {
    fun isAccessible(): Boolean {
        return accessible == "S"
    }
}
