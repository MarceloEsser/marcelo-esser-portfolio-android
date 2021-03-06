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
    val id: Long,

    @SerializedName("nome_master")
    @Expose
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("linhas_master")
    @Expose
    @ColumnInfo(name = "code")
    val code: String,
) : Serializable {
    @Ignore
    var way: LineWay? = null
}
