package esser.marcelo.portfolio.core.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import esser.marcelo.portfolio.core.model.BusLine
import esser.marcelo.portfolio.core.model.LineSchedules

@Dao
interface AppDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertLines(lines: List<BusLine>)

    @Query("SELECT * FROM BusLine")
    suspend fun getLines(): List<BusLine>

    @Insert(onConflict = REPLACE)
    suspend fun insertSchedule(scheduleResponse: LineSchedules)

    @Query("SELECT * FROM LineSchedules WHERE line_id in (:lineId)")
    suspend fun getLineSchedule(lineId: Long): LineSchedules
}