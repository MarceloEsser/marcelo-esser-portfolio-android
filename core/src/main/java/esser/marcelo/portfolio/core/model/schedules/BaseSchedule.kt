package esser.marcelo.portfolio.core.model.schedules

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
    var hour: String = "",

    @SerializedName("abrev")
    var abrev: String =  "",

    @SerializedName("apd")
    var apd: String = "N"
) {
    fun isApd(): Boolean {
        return apd == "S"
    }
}
