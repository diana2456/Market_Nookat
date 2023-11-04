package nook.test.market_nookat.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import nook.test.market_nookat.R
import nook.test.market_nookat.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }
}