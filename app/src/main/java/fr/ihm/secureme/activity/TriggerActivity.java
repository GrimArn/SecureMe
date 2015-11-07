package fr.ihm.secureme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

import fr.ihm.secureme.R;

/**
 * Created by pierrebonhoure on 04/11/2015.
 */
public class TriggerActivity extends AppCompatActivity {
    private Mode mode;

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String extra_mode = intent.getStringExtra("EXTRA_mode");

            if (extra_mode.equals("mvt")) {
                mode = Mode.MVT;
                setContentView(R.layout.activity_mvt);
                getSupportActionBar().setTitle(getString(R.string.mouvement));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Switch activator= (Switch) findViewById(R.id.activate);
            } else if (extra_mode.equals("dist")) {
                mode = Mode.DIST;
                setContentView(R.layout.activity_dist);
                getSupportActionBar().setTitle(getString(R.string.distance));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Switch activator= (Switch) findViewById(R.id.activate);
            } else if (extra_mode.equals("cable")) {
                mode = Mode.CABLE;
                setContentView(R.layout.activity_cable);
                getSupportActionBar().setTitle(getString(R.string.cable));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Switch activator= (Switch) findViewById(R.id.activate);
            } else if (extra_mode.equals("sim")) {
                mode = Mode.SIM;
                setContentView(R.layout.activity_sim);
                getSupportActionBar().setTitle(getString(R.string.sim));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Switch activator= (Switch) findViewById(R.id.activate);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                if(sp.getBoolean("mode_sim",false)){
                    activator.setChecked(true);
                }
                activator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){//activation
                            editor.putBoolean("mode_sim", true);
                            TelephonyManager telephoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            String simSerialNumber = telephoneMgr.getSimSerialNumber();
                            editor.putString("SIM_ID", simSerialNumber);
                            editor.commit();
                        }else{ //désactivation
                            editor.putBoolean("mode_sim", false);
                            editor.commit();
                        }
                    }
                });
            }

        }
        else{
            System.err.println("activity called from nowhere");
        }
    }
}

enum Mode{
    MVT,
    DIST,
    CABLE,
    SIM
}

