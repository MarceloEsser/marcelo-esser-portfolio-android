package esser.marcelo.portfolio.core.model.busLine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.portfolio.core.model.LineWay
import esser.marcelo.portfolio.core.model.busSchedule.LineSchedules
import java.io.Serializable

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
    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    @ColumnInfo(name = "name")
    var name: String,

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    @ColumnInfo(name = "code")
    var code: String,

) : Serializable {
    @Ignore
    var way: LineWay? = null
}
