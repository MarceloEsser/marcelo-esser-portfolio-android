package esser.marcelo.portfolio.core.model.busLine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.model.Itinerary

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/08/22
 */

class BusLine(
    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<Itinerary>? = null
) : BaseLine()
