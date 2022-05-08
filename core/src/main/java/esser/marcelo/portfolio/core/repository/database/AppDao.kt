package esser.marcelo.portfolio.core.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import esser.marcelo.portfolio.core.model.busLine.BusLine

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLines(lines: List<BusLine>)

    @Query("SELECT * FROM BusLine")
    suspend fun getLines(): List<BusLine>
}