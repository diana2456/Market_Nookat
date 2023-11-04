package nook.test.market_nookat.ui.fragment.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import nook.test.market_nookat.R
import nook.test.market_nookat.ui.activity.AdsDataActivity
import nook.test.market_nookat.ui.activity.MainActivity
class Notification(private val context: Context) {
        private val channelId = "channel_id"
        private val channelName = "channel_name"
        init {
            createNotificationChannel()
        }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT)
                channel.lightColor = Color.GREEN
                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }
        @SuppressLint("MissingPermission")
        fun showNotification( title: String, message: String) {
            val bundle = Bundle()
            bundle.putString("FIFI", title)
            val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.mobile_navigation) // Replace with your navigation graph
                .setDestination(R.id.adsSerFragment) // Replace with the destination ID of AdsNotifiFragment
                .setArguments(bundle).createPendingIntent()

                val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(context)) {
                notify(0, notificationBuilder.build())

            }
        }
}


