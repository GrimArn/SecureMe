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
    private Switch activator;
    private SharedPreferences.Editor editor;

    enum Mode{
        MVT,
        DIST,
        CABLE,
        SIM
    }


    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String extra_mode = intent.getStringExtra("EXTRA_mode");
            switch (extra_mode){
                case "mvt":
                    mode = Mode.MVT;
                    break;
                case "dist":
                    mode = Mode.DIST;
                    break;
                case "cable":
                    mode = Mode.CABLE;
                    break;
                case "sim":
                    mode = Mode.SIM;
                    break;
                default :
                    try {
                        Log.e("Not existing mode ",extra_mode);
                        throw new NotExistingModeException(" (mode wanted : "+extra_mode+")");
                    } catch (NotExistingModeException e) {
                        return;
                    }
            }
            initView();
            initListener();

        }
        else{
            System.err.println("activity called from nowhere");
        }
    }

    private void initListener() {
        switch(mode){
            case MVT :
                activator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){//activation
                            editor.putBoolean("mode_mvt", true);
                            editor.commit();
                        }else{ //désactivation
                            editor.putBoolean("mode_mvt", false);
                            editor.commit();
                        }
                    }
                });
                break;
            case DIST:
                activator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){//activation
                            editor.putBoolean("mode_dist", true);
                            editor.commit();
                        }else{ //désactivation
                            editor.putBoolean("mode_dist", false);
                            editor.commit();
                        }
                    }
                });
                break;
            case CABLE:
                activator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){//activation
                            editor.putBoolean("mode_cable", true);
                            editor.commit();
                        }else{ //désactivation
                            editor.putBoolean("mode_cable", false);
                            editor.commit();
                        }
                    }
                });
                break;
            case SIM:
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
                break;
        }
    }

    private void initView() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        switch(mode){
            case MVT :
                setContentView(R.layout.activity_mvt);
                getSupportActionBar().setTitle(getString(R.string.mouvement));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activator= (Switch) findViewById(R.id.activate);
                if(sp.getBoolean("mode_mvt",false)){
                    activator.setChecked(true);
                }
                break;
            case DIST:
                setContentView(R.layout.activity_dist);
                getSupportActionBar().setTitle(getString(R.string.distance));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activator= (Switch) findViewById(R.id.activate);
                if(sp.getBoolean("mode_dist",false)){
                    activator.setChecked(true);
                }
                break;
            case CABLE:
                setContentView(R.layout.activity_cable);
                getSupportActionBar().setTitle(getString(R.string.cable));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activator= (Switch) findViewById(R.id.activate);
                if(sp.getBoolean("mode_cable",false)){
                    activator.setChecked(true);
                }
                break;
            case SIM:
                setContentView(R.layout.activity_sim);
                getSupportActionBar().setTitle(getString(R.string.sim));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activator= (Switch) findViewById(R.id.activate);
                if(sp.getBoolean("mode_sim",false)){
                    activator.setChecked(true);
                }
                break;
        }
    }

    private class NotExistingModeException extends Exception {
        NotExistingModeException(String mode){
            super(mode);
        }
    }
}


