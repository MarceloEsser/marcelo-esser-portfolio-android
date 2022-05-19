package esser.marcelo.portfolio.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
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
    var id: Long,

    @SerializedName("nome_master")
    @Expose
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("linhas_master")
    @Expose
    @ColumnInfo(name = "code")
    val code: String,
) : Serializable {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "code" to code,
            "way" to way?.code,
            "id" to id
        )
    }

    fun fromMap(map: Map<String, Any?>): BusLine {
        val line = BusLine(
            name = map["name"].toString(),
            code = map["code"].toString(),
            id = map["id"].toString().toLong(),
        )
        line.way = LineWay.availableWays().find {
            it.code == code
        }
        return line
    }

    @Ignore
    var way: LineWay? = null
}
