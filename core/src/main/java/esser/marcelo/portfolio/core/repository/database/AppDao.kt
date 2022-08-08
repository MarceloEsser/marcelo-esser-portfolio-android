package esser.marcelo.portfolio.core.repository.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import esser.marcelo.portfolio.core.model.schedules.cb.SaturdayCb
import esser.marcelo.portfolio.core.model.schedules.cb.SundayCb
import esser.marcelo.portfolio.core.model.schedules.cb.WorkingDayCb
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

    @Transaction
    @Query("SELECT * FROM BusLine WHERE name in (:name) and code in (:code) and way in (:way)")
    suspend fun getLineBy(name: String, code: String, way: String): LineSchedules?

    @Insert(onConflict = REPLACE)
    suspend fun insertWorkingDays(schedules: List<WorkingDayCb>)

    @Insert(onConflict = REPLACE)
    suspend fun insertSaturdays(schedules: List<SaturdayCb>)

    @Insert(onConflict = REPLACE)
    suspend fun insertSundays(schedules: List<SundayCb>)

    @Update(onConflict = REPLACE)
    fun updateLine(busLine: BusLine)
}