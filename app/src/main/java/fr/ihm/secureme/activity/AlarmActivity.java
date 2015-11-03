package fr.ihm.secureme.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.ihm.secureme.R;
import fr.ihm.secureme.manager.SoundAlarmManager;

import java.util.Timer;
import java.util.TimerTask;


public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout mActivityBackground;
    Vibrator mVibrator;
    SoundAlarmManager mSoundAlarmManager;
    Timer mTimerChangingColor;
    private TextView mCodeField;

    protected Button mButtonZero;
    protected Button mButtonOne;
    protected Button mButtonTwo;
    protected Button mButtonThree;
    protected Button mButtonFour;
    protected Button mButtonFive;
    protected Button mButtonSix;
    protected Button mButtonSeven;
    protected Button mButtonEight;
    protected Button mButtonNine;

    private State mState;
    enum State {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        UNLOCKED,
        CODEWRONG
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_alarm);

        PowerManager.WakeLock screenLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();
        screenLock.release();

        init();
    }

    private void init() {
        mState = State.RED;
        initVibrate();
        initSound();
        initViews();
        initLight();
        initButtons();
        initTimer();
    }

    private void initSound() {
        mSoundAlarmManager = new SoundAlarmManager(this);
        mSoundAlarmManager.startAlarm();
        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
    }

    private void initVibrate(){
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 5000};
        mVibrator.vibrate(pattern, 0);
    }

    private void initTimer() {
        mTimerChangingColor = new Timer();
        tickTimer();
    }

    private void tickTimer() {
        mTimerChangingColor.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerHandler();
                    }
                });
            }
        }, 50);
    }

    private void timerHandler() {
        switch (mState) {
            case RED:
                mState = State.BLUE;
                lightRed();
                tickTimer();
                break;
            case BLUE:
                mState = State.GREEN;
                lightBlue();
                tickTimer();
                break;
            case GREEN:
                mState = State.YELLOW;
                lightGreen();
                tickTimer();
                break;
            case YELLOW:
                mState = State.RED;
                lightYellow();
                tickTimer();
                break;
            case UNLOCKED:
                mVibrator.cancel();
                lightGreen();
                screenUnlocking();
                break;
            case CODEWRONG:
                lightRed();
                keepScreenLocked();
                break;
        }
    }

    private void initViews() {
        mActivityBackground = (RelativeLayout) findViewById(R.id.mainlayout);
        mCodeField = (TextView) findViewById(R.id.code_tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initButtons() {
        mButtonZero = (Button) findViewById(R.id.button0);
        mButtonZero.setOnClickListener(this);
        mButtonOne = (Button) findViewById(R.id.button1);
        mButtonOne.setOnClickListener(this);
        mButtonTwo = (Button) findViewById(R.id.button2);
        mButtonTwo.setOnClickListener(this);
        mButtonThree = (Button) findViewById(R.id.button3);
        mButtonThree.setOnClickListener(this);
        mButtonFour = (Button) findViewById(R.id.button4);
        mButtonFour.setOnClickListener(this);
        mButtonFive = (Button) findViewById(R.id.button5);
        mButtonFive.setOnClickListener(this);
        mButtonSix = (Button) findViewById(R.id.button6);
        mButtonSix.setOnClickListener(this);
        mButtonSeven = (Button) findViewById(R.id.button7);
        mButtonSeven.setOnClickListener(this);
        mButtonEight = (Button) findViewById(R.id.button8);
        mButtonEight.setOnClickListener(this);
        mButtonNine = (Button) findViewById(R.id.button9);
        mButtonNine.setOnClickListener(this);
    }

    private void initLight() {
        lightRed();
    }

    private void lightRed() {
        mActivityBackground.setBackgroundColor(getResources().getColor(R.color.red));
    }
    private void lightBlue() {
        mActivityBackground.setBackgroundColor(getResources().getColor(R.color.blue));
    }

    private void lightGreen() {
        mActivityBackground.setBackgroundColor(getResources().getColor(R.color.green));
    }
    private void lightYellow() {
        mActivityBackground.setBackgroundColor(getResources().getColor(R.color.yellow));
    }

    private void screenUnlocking() {
        mSoundAlarmManager.stopAlarm();
        mTimerChangingColor.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

            }
        }, 1000);
    }
    private void keepScreenLocked() {
        mTimerChangingColor.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tickTimer();
                        mState = State.RED;
                    }
                });

            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        Button btnClicked = (Button) v;
        onKeyPressed(btnClicked.getText().toString());
    }

    @Override
    public void onBackPressed() {
    }

    private void onKeyPressed(String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String code = sharedPreferences.getString("pref_app_code", "NULL");
        Log.e("TAG", code);

        switch (mState) {
            case RED:case GREEN:case YELLOW:case BLUE:
                if (mCodeField.length() < 4) {
                    mCodeField.setText(mCodeField.getText() + key);
                }
                if (mCodeField.length() == 4) {
                    if (code.equals(mCodeField.getText().toString())) {
                        mState = State.UNLOCKED;
                    }
                    else{
                        mCodeField.setText("");
                        mState = State.CODEWRONG;
                    }

                }
                break;
            case UNLOCKED: case CODEWRONG:
                mCodeField.setText("");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
