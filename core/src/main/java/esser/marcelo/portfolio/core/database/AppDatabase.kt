package esser.marcelo.portfolio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import esser.marcelo.portfolio.core.model.busLine.BusLine
import esser.marcelo.portfolio.core.model.busSchedule.BaseSchedule
import esser.marcelo.portfolio.core.model.busSchedule.Saturday
import esser.marcelo.portfolio.core.model.busSchedule.Sunday
import esser.marcelo.portfolio.core.model.busSchedule.Workingday

@Database(
    entities = [BusLine::class, Workingday::class, Saturday::class, Sunday::class, BaseSchedule::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun getAppDao(): AppDao
}