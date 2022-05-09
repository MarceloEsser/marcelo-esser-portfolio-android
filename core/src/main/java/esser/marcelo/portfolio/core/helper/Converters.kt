package esser.marcelo.portfolio.core.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import esser.marcelo.portfolio.core.model.busSchedule.BaseSchedule
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<BaseSchedule> {
        val listType: Type = object : TypeToken<List<BaseSchedule>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromArrayList(list: List<BaseSchedule>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}