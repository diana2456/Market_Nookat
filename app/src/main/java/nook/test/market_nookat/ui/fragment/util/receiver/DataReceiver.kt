package nook.test.market_nookat.ui.fragment.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import nook.test.market_nookat.ui.fragment.util.Pref

class DataReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getBooleanExtra("MESSAGE",true)
        if(message != null){
            context?.let { Pref(it).setBoardingFire(false) }
            Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
        }
    }
}