package fr.ihm.secureme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by pierrebonhoure on 07/11/2015.
 */
public class SimCardRemovedReceiver extends BroadcastReceiver {
    private int mSimState=-1;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = telephoneMgr.getSimSerialNumber();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if(sp.getBoolean("mode_sim",false)) {
            int simState = telephoneMgr.getSimState();
            if (simState == 1) {
               Intent intent1 = new Intent();
               intent1.setClassName("fr.ihm.secureme", "fr.ihm.secureme.activity.AlarmActivity");
               intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent1);
            }
            else{
                Log.e("GROS PROBLEME","CA VA PAS LA");
            }
        }
    }
}
