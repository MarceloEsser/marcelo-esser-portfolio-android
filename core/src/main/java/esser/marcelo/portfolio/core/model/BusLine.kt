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

    @ColumnInfo(name = "way")
    var way: String?,
) : Serializable {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "code" to code,
            "way" to lineWay?.code,
            "id" to id
        )
    }

    @Ignore
    var lineWay: LineWay? = null
        set(value) {
            this.way = value?.code ?: ""
            field = value
        }
}
