package esser.marcelo.portfolio.core.model.busLine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

open class BaseLine() {

    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    var name: String = ""

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    var code: String = ""
    var way: String = ""

    constructor(name: String, code: String, way: String) : this() {
        this.name = name
        this.code = code
        this.way = way
    }
}