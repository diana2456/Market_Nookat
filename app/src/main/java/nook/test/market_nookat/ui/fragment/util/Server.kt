package nook.test.market_nookat.ui.fragment.util

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import nook.test.market_nookat.ui.activity.AdsDataActivity


class Server : IntentService(""){

   private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
         notification = Notification(applicationContext)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
        val title = intent?.getStringExtra("title")
        val message = intent?.getStringExtra("message")

        if (title != null && message != null) {
            notification.showNotification(title, message)
            val activityIntent = Intent(this, AdsDataActivity::class.java)
            activityIntent.putExtra("key", title)
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(activityIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
}