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

class LinesDTO(
    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<ItinerariesDTO>? = null
) : BaseLine()
