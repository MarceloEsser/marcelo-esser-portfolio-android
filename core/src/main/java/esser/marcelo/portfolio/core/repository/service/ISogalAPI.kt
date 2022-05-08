package esser.marcelo.portfolio.core.repository.service

import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.model.busSchedule.SchedulesResponse
import esser.marcelo.portfolio.core.wrapper.ApiResult
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
    suspend fun postSogalSchedulesAsync(
        @Field("action") lineWay: String,
        @Field("linha") lineCode: String
    ): Resource<SchedulesResponse>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    suspend fun postSogalLines(
        @Field("action") action: String
    ): Resource<List<BusLine>>

//    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
//    @FormUrlEncoded
//    fun getSogalItinerariesAsync(
//        @Field("action") action: String,
//        @Field("linha") linha: String
//    ): Deferred<ApiResult<BusLine>>
}