package esser.marcelo.portfolio.core.model

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class LineWay(
    val description: String,
    val code: String
) {
    companion object {
        final val bcCode = "buscaHorarioLinhaBC"
        final val cbCode = "buscaHorarioLinhaCB"

        fun availableWays(): List<LineWay> {
            return listOf(
                LineWay("Centro Bairro - CB", cbCode),
                LineWay("Bairro Centro - BC", bcCode),
            )
        }
    }
}