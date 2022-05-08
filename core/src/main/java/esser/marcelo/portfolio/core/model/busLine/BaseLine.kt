package esser.marcelo.portfolio.core.model.busLine

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

open class BaseLine {

    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    @ColumnInfo(name = "name")
    var name: String = ""

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    @ColumnInfo(name = "code")
    var code: String = ""

    @Ignore
    var way: String = ""
}