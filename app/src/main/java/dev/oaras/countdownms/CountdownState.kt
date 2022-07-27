package dev.oaras.countdownms

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.math.RoundingMode
import java.time.ZoneId
import java.util.*

class CountdownViewModel(context: Context) : ViewModel() {

    private val pref: SharedPreferences = context.getSharedPreferences("countdown", Context.MODE_PRIVATE)

    val year: Int
    val month: Int
    val day: Int

    init {
        val localDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        year = localDate.year
        month = localDate.monthValue
        day = localDate.dayOfMonth
    }

    private val calendar: Calendar = Calendar.getInstance()

    var data: CountdownData? by mutableStateOf(getSavedData())
        private set

    @JvmName("setDataInternal")
    fun setData(data: CountdownData) {
        this.data = data
        pref.edit {
            putString("string", data.string)
            putLong("start", data.start)
            putLong("end", data.end)
        }
    }

    private fun getSavedData(): CountdownData? {
        val string = pref.getString("string", null)
        val start = pref.getLong("start", 0)
        val end = pref.getLong("end", 0)
        if (string != null && start != 0L && end != 0L) {
            return CountdownData(string, start, end)
        }
        return null
    }

    val currentTimeMs = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(21L)
        }
    }

    fun setTarget(context: Context, year: Int, month: Int, dayOfMonth: Int) {
        //Get milliseconds from target date
        calendar.set(year, month, dayOfMonth, 0, 0, 0)
        val targetMs = calendar.timeInMillis

        val newData = CountdownData(
            "$dayOfMonth/$month/$year", System.currentTimeMillis(), targetMs
        )
        if (newData.max > 0) setData(newData)
        else showSnackbar(context, "Can't countdown to past date")
    }

    private fun showSnackbar(context: Context, s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}

data class CountdownData(
    val string: String,
    val start: Long,
    val end: Long,
) {
    val max = end - start

    fun progress(currentMs: Long): Float {
        if (currentMs >= end) return -1f
        return (currentMs - start).toBigDecimal()
            .divide(max.toBigDecimal(), 2, RoundingMode.HALF_UP).toFloat()
    }

}