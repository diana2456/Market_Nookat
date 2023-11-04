package nook.test.market_nookat.ui.fragment.home

import android.content.Context

class ClickStateManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("click_state", Context.MODE_PRIVATE)

    fun isClicked(id: Int): Boolean {
        // Читаем состояние нажатия из SharedPreferences
        return sharedPreferences.getBoolean("clicked_$id", false)
    }

    fun setClicked(id: Int, isClicked: Boolean) {
        // Сохраняем состояние нажатия в SharedPreferences
        sharedPreferences.edit().putBoolean("clicked_$id", isClicked).apply()
    }
}
