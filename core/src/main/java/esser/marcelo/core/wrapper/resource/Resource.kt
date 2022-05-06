package esser.marcelo.core.wrapper.resource

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 08/05/22
 */

data class Resource<T>(
    val requestStatus: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {

        fun <T> success(data: T?): Resource<T> = Resource(Status.success, data, null)

        fun <T> error(message: String?, data: T? = null): Resource<T> =
            Resource(Status.error, data, message)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.loading, data, null)

    }
}