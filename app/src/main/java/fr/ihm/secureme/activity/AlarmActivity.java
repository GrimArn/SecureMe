package fr.ihm.secureme.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import fr.ihm.secureme.R;

import java.util.Timer;
import java.util.TimerTask;


public class AlarmActivity extends ActionBarActivity {

    RelativeLayout rl;
    Timer t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        rl = (RelativeLayout) findViewById(R.id.mainlayout);
        t1 = new Timer();

        initLight();
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
                        lightYellow();
                    }
                });

            }
        }, 50);

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
}
