package esser.marcelo.portfolio.core.sogalServices

import esser.marcelo.portfolio.core.model.LinesDTO
import esser.marcelo.portfolio.core.model.SogalResponse
import esser.marcelo.portfolio.core.wrapper.ApiResult
import kotlinx.coroutines.Deferred
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
    fun getSogalSchedulesAsync(
        @Field("action") lineWay: String,
        @Field("linha") lineCode: String
    ): Deferred<ApiResult<SogalResponse>>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalListAsync(
        @Field("action") action: String
    ): Deferred<ApiResult<List<LinesDTO>>>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalItinerariesAsync(
        @Field("action") action: String,
        @Field("linha") linha: String
    ): Deferred<ApiResult<LinesDTO>>
}