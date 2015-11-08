package fr.ihm.secureme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by pierrebonhoure on 08/11/2015.
 */
public class CableUnpluggedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if(sp.getBoolean("mode_cable",false)) {
            Intent intent1 = new Intent();
            intent1.setClassName("fr.ihm.secureme", "fr.ihm.secureme.activity.AlarmActivity");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
