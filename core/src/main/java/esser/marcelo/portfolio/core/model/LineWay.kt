package esser.marcelo.portfolio.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity

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