package esser.marcelo.portfolio.core.model.busSchedule

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

data class Workingday(
    var workingdayId: Long? = null,
    var workindayKey: Long? = null,
) : BaseSchedule()