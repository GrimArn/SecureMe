package fr.ihm.secureme.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import fr.ihm.secureme.R;
import fr.ihm.secureme.model.ContactListSingleton;

public class SplashScreenActivity extends ActionBarActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView appName = (TextView) findViewById(R.id.appname);
        appName.setTypeface(myTypeface);

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    ContactListSingleton.getInstance().loadContactList(SplashScreenActivity.this);
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
}
