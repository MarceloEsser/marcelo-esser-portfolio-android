package esser.marcelo.portfolio.core.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import esser.marcelo.portfolio.core.model.Schedule
import java.lang.reflect.Type

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 10/05/22
 */

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Schedule> {
        val listType: Type = object : TypeToken<List<Schedule>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromArrayList(list: List<Schedule>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}