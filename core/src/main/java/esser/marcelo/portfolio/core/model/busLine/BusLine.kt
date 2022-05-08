package esser.marcelo.portfolio.core.model.busLine

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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

@Entity
class BusLine(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
) : BaseLine() {
    @SerializedName("itinerarios")
    @Expose
    @Ignore
    var itineraries: List<Itinerary>? = null
}
