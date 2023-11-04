package nook.test.market_nookat.ui.fragment.settings

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.Locale

class LocateHelper {

    @SuppressLint("ObsoleteSdkInt")
    fun setLocale(context: Context, language: String): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            updateResource(context,language)
        } else {
            updateResourceLegacy(context, language)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.N)
    fun updateResource(context: Context,language:String): Context{
        val locate = Locale(language)
        Locale.setDefault(locate)
        val configuration = context.resources.configuration
        configuration.setLocale(locate)
        configuration.setLayoutDirection(locate)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourceLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val  resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration,resources.displayMetrics)
        return context
    }
}