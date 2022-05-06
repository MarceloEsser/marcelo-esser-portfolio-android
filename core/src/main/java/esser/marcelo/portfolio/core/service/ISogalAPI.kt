package esser.marcelo.portfolio.core.service

import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.model.busSchedule.SchedulesResponse
import esser.marcelo.portfolio.core.wrapper.ApiResult
import esser.marcelo.portfolio.core.wrapper.resource.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.Flow

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
    suspend fun getSogalSchedulesAsync(
        @Field("action") lineWay: String,
        @Field("linha") lineCode: String
    ): ApiResult<SchedulesResponse>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalListAsync(
        @Field("action") action: String
    ): Deferred<ApiResult<List<BusLine>>>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalItinerariesAsync(
        @Field("action") action: String,
        @Field("linha") linha: String
    ): Deferred<ApiResult<BusLine>>
}