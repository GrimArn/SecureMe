package fr.ihm.secureme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.widget.TextView;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.KeyPadDialogCallBack;
import fr.ihm.secureme.dialog.ConfirmCodeKeyPadDialog;
import fr.ihm.secureme.dialog.CreateCodeKeyPadDialog;
import fr.ihm.secureme.model.ContactListSingleton;

public class SplashScreenActivity extends Activity implements KeyPadDialogCallBack {
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 3000;
    private String mFirstCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView appName = (TextView) findViewById(R.id.appname);
        appName.setTypeface(myTypeface);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String code = sharedPreferences.getString(getString(R.string.key_pref_app_code), getString(R.string.default_non_value_code));
        if (code.equals(getString(R.string.default_non_value_code))){
            AlertDialog addCodeDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.welcome))
                    .setMessage(getString(R.string.enter_new_code_message))
                    .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CreateCodeKeyPadDialog keyPadDialog = new CreateCodeKeyPadDialog(SplashScreenActivity.this,
                                    SplashScreenActivity.this);
                            keyPadDialog.show();
                            dialog.dismiss();
                        }
                    }).setCancelable(false)

                    .create();
            addCodeDialog.show();
        }
        else {
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

    @Override
    public void createCodeCallback(String code) {
        mFirstCode = code;
        ConfirmCodeKeyPadDialog confirmCodeKeyPadDialog = new ConfirmCodeKeyPadDialog(this, this);
        confirmCodeKeyPadDialog.show();
    }

    @Override
    public void confirmCodeCallback(String code) {
        if (mFirstCode.equals(code)) {
            saveCode();
            AlertDialog errorDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.code_changed_title))
                    .setIcon(R.drawable.ic_check_circle_black_24dp)
                    .setMessage(getString(R.string.code_changed_message))
                    .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ContactListSingleton.getInstance().loadContactList(SplashScreenActivity.this);
                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }).setCancelable(false)

                    .create();
            errorDialog.show();
        }
        else {
            AlertDialog errorDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error_title))
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setMessage(getString(R.string.error_not_same_code_message))
                    .setPositiveButton(getString(R.string.try_again_message), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CreateCodeKeyPadDialog keyPadDialog = new CreateCodeKeyPadDialog(SplashScreenActivity.this, SplashScreenActivity.this);
                            keyPadDialog.show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            errorDialog.show();
        }
    }

    private void saveCode() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(getString(R.string.key_pref_app_code), mFirstCode);
        editor.apply();
    }

    @Override
    public void checkCodeCallback(String code) {

    }
}
