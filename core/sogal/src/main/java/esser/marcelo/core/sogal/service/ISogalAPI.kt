package esser.marcelo.core.sogal.service

import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules
import esser.marcelo.portfolio.core.wrapper.Resource
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

interface ISogalAPI {
    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    suspend fun postSogalSchedules(
        @Field("action") lineWay: String,
        @Field("linha") lineCode: String
    ): Resource<LineSchedules>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    suspend fun postSogalLines(
        @Field("action") action: String
    ): Resource<List<BusLine>>
}