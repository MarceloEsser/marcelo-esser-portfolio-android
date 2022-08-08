package esser.marcelo.portfolio.core.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import esser.marcelo.portfolio.core.model.schedules.cb.SaturdayCb
import esser.marcelo.portfolio.core.model.schedules.cb.SundayCb
import esser.marcelo.portfolio.core.model.schedules.cb.WorkingDayCb
import esser.marcelo.portfolio.core.helper.Converters
import esser.marcelo.portfolio.core.model.BusLine

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

@Database(
    entities = [BusLine::class, WorkingDayCb::class, SaturdayCb::class, SundayCb::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun getAppDao(): AppDao
}