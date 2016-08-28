package andrewpolvoko.htp_android_courses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((intent.getExtras().get("pdus")) != null) {
            final Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            final SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }
            for (SmsMessage sms : messages) {
                Toast.makeText(context, "Sms from: " + sms.getOriginatingAddress() +
                                        "\nText: " + sms.getMessageBody(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
