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
        fun availableWays(): List<LineWay> {
            return listOf(
                LineWay("Centro Bairro - CB", "buscaHorarioLinhaCB"),
                LineWay("Bairro Centro - BC", "buscaHorarioLinhaBC"),
            )
        }
    }
}