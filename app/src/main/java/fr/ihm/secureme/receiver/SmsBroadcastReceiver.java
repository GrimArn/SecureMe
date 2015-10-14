package fr.ihm.secureme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by nonau on 06/10/15.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver{

    final SmsManager smsManager = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        Log.v("TAG", "coucou");
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; ++i) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    if (message.equals("BANANA")) {
                        Toast toast = Toast.makeText(context, "Message reçu de : " + senderNum + " message : " + message, Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent1 = new Intent();
                        intent1.setClassName("fr.ihm.secureme", "fr.ihm.secureme.activity.AlarmActivity");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }


                }
            }
        } catch (Exception e) {
            Log.e("Sms", "Ex" + e);
        }
    }
}
