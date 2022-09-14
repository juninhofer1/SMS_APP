package com.example.sms_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage

class SMBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            val smsMessages: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in smsMessages) {
                if(message.messageBody.contains("ATS Frete Conta Digital")) {
                    val it = Intent(BroadcastAction.KEY_SMS)
                    it.putExtra(BundleKey.KEY_CODE_SMS, message.messageBody.numberOfString())
                    context?.sendBroadcast(it)
                }
            }
        }
    }
}

class BroadcastAction {
    companion object {
        val KEY_SMS = "sms_code"
    }
}

class BundleKey {
    companion object {
        val KEY_CODE_SMS = "number_code"
    }
}

fun String.numberOfString(): String{
   return this.replace("[^0-9]".toRegex(), "")
}