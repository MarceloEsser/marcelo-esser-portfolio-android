package esser.marcelo.portfolio.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import esser.marcelo.portfolio.core.model.busSchedule.SchedulesResponse

@Dao
interface AppDao {

    @Insert()
    suspend fun insertSchedules(schedulesResponse: SchedulesResponse);

    @Query("")
    suspend fun getSchedules(): SchedulesResponse
}