package esser.marcelo.portfolio.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/08/22
 */
class ItinerariesDTO(

    val itineraryId: Long,

    @SerializedName("cidade")
    @Expose
    var city: String? = null,

    @SerializedName("logradouro")
    @Expose
    var street: String? = null,

    var itineraryKey: Long? = null,

)
