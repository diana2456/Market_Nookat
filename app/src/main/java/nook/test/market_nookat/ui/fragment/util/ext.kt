package nook.test.market_nookat.ui.fragment.util

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import nook.test.market_nookat.ui.activity.MainActivity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: String){
    Glide.with(this).load(image).into(this)
}

fun ImageView.loadPhoto(image: Uri){
    Glide.with(this).load(image).into(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertDateFormat(locale: Locale = Locale.getDefault()): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
        val dateTime = OffsetDateTime.parse(this, inputFormatter)

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val timeString = dateTime.format(timeFormatter)

        val dayOfMonth = dateTime.dayOfMonth
        val month = dateTime.month.getDisplayName(TextStyle.SHORT, locale)

        "$timeString $dayOfMonth-$month."
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        "" // Обработка ошибки: возвращаем пустую строку или другое значение по умолчанию
    }
}