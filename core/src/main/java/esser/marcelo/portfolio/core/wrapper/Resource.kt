package esser.marcelo.portfolio.core.wrapper

import esser.marcelo.portfolio.core.Status

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 06/05/22
 */

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {

        fun <T> success(data: T?): Resource<T> = Resource(Status.Success, data, null)

        fun <T> error(message: String?, data: T? = null): Resource<T> =
            Resource(Status.Error, data, message)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.Loading, data, null)

    }
}