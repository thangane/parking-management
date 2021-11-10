package app.com.parkingmanagement.util

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun getDate(dateString: String): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(Date(dateString.toLong()))
    }

    fun getCurrentDateString(): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(Date())
    }

    fun convertToDateString(date: Date): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date)
    }

    fun convertToDateTime(millis: String): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return formatter.format(Date(millis.toLong()))
    }

    fun getEpochMillis(dateString: String): Int {
        return Date(dateString).time.toInt()
    }
}