package esser.marcelo.portfolio.core.repository.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

@Dao
interface AppDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertLines(lines: List<BusLine>)

    @Query("SELECT * FROM BusLine")
    suspend fun getLines(): List<BusLine>?

    @Insert(onConflict = REPLACE)
    suspend fun insertSchedule(scheduleResponse: LineSchedules)

    @Update
    suspend fun updateSchedule(scheduleResponse: LineSchedules)

    @Query("SELECT * FROM LineSchedules WHERE line_id in (:lineId)")
    suspend fun getLineSchedule(lineId: Long): LineSchedules?
}