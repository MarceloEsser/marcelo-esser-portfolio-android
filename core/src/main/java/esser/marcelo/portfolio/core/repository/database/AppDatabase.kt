package esser.marcelo.portfolio.core.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import esser.marcelo.portfolio.core.helper.Converters
import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.model.busSchedule.*

@Database(
    entities = [BusLine::class, LineSchedules::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun getAppDao(): AppDao
}