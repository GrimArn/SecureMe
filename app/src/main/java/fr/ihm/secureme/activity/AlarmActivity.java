package fr.ihm.secureme.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.ihm.secureme.R;

import java.util.Timer;
import java.util.TimerTask;


public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rl;
    Timer t1;
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

    private boolean mCodeFound = false;
    private String mCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_alarm);
        rl = (RelativeLayout) findViewById(R.id.mainlayout);
        t1 = new Timer();

        mCodeField = (TextView) findViewById(R.id.code_tv);
        initLight();
        initButtons();

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
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl.setBackgroundColor(getResources().getColor(R.color.red));
                        lightBlue();
                    }
                });

            }
        }, 50);

    }
    private void lightBlue() {
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl.setBackgroundColor(getResources().getColor(R.color.blue));
                        lightGreen();
                    }
                });

            }
        }, 50);

    }

    private void lightGreen() {
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl.setBackgroundColor(getResources().getColor(R.color.green));
                        if (mCodeFound) {
                            screenUnlocking();
                        } else {
                            lightYellow();
                        }

                    }
                });

            }
        }, 50);

    }
    private void screenUnlocking() {
        t1.schedule(new TimerTask() {
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

    private void lightYellow() {
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rl.setBackgroundColor(getResources().getColor(R.color.yellow));
                        lightRed();
                    }
                });


            }
        }, 50);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button btnClicked = (Button) v;
        onKeyPressed(btnClicked.getText().toString());
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void onKeyPressed(String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String code = sharedPreferences.getString("pref_app_code", "NULL");
        Log.e("TAG", code);
        if (mCodeField.length() < 4) {
            mCodeField.setText(mCodeField.getText() + key);
        }
        if (mCodeField.length() == 4) {
            if (code.equals(mCodeField.getText().toString()))
                mCodeFound = true;
            else
                mCodeField.setText("");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
