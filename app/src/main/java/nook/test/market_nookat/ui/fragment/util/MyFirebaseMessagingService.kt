package nook.test.market_nookat.ui.fragment.util


import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val intent = Intent(this, Server::class.java)
        intent.putExtra("title", message.notification?.title)
        intent.putExtra("message", message.notification?.body)
        startService(intent)
    }
}

