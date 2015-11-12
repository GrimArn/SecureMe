package fr.ihm.secureme.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by pierrebonhoure on 10/11/2015.
 */
public class MotionDetector extends Service implements SensorEventListener{
    // Start with some variables
    private SensorManager sensorMan;
    private Sensor accelerometer;
    private Handler handler;


    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private boolean started=false;
    private boolean detected=false;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int sensibilite;

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e("Service", "created");
        super.onCreate(); // if you override onCreate(), make sure to call super().
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sensibilite = (10-(sp.getInt("sensibilite_mouvement",5)))/2;
        editor = sp.edit();
        handler= new Handler();

        Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        Looper.prepare();
                        for (int i=sp.getInt("trig_time_mvt", 3); i>0 ; i=i-2 ){
                            if(sp.getBoolean("mode_mvt",false)) {
                                toastThis(i + " sec");
                                wait(2000);
                            }else{
                                stopSelf();
                            }
                        }
                        Log.d("Thread", "ended");
                        Log.d("Sensibility", sensibilite+"");
                        if(sp.getBoolean("mode_mvt",false)){
                            started = true;
                        }
                        else {
                            stopSelf();
                        }
                    }
                }
                catch(InterruptedException ex){
                }

            }
        };
        Log.d("Thread", "started");
        thread.start();

    }
    private void toastThis(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MotionDetector.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("toast","toasting");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!detected && started) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity = event.values.clone();
                // Shake detection
                float x = mGravity[0];
                float y = mGravity[1];
                float z = mGravity[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;
                // Make this higher or lower according to how much
                // motion you want to detect
                if (mAccel > sensibilite) {
                    Log.d("Motion detected", mAccel + "");
                    Intent i = new Intent();
                    i.setClassName("fr.ihm.secureme", "fr.ihm.secureme.activity.AlarmActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(i);
                    editor.putBoolean("mode_mvt",false);
                    editor.commit();
                    stopSelf();
                    detected = true;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }
}
