package esser.marcelo.portfolio.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.model.schedules.Saturday
import esser.marcelo.portfolio.core.model.schedules.Sunday
import esser.marcelo.portfolio.core.model.schedules.Workingday

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/08/22
 */

class SogalResponse(
    @SerializedName("nomeLinha")
    var lineName: String? = null,

    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    var workingDays: List<Workingday>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    var saturdays: List<Saturday>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    var sundays: List<Sunday>? = null,

    @SerializedName("hora")
    var hour: String? = null,

    @SerializedName("abrev")
    var abrev: String? = null,

    @SerializedName("apd")
    var apd: String? = null,

    @SerializedName("nome_master")
    @Expose
    var name: String? = null,

    @SerializedName("linhas_master")
    @Expose
    var lineCode: String? = null
)
