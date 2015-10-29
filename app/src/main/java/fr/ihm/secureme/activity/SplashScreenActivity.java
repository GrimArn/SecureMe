package fr.ihm.secureme.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.KeyPadDialogCallBack;
import fr.ihm.secureme.dialog.ConfirmCodeKeyPadDialog;
import fr.ihm.secureme.dialog.CreateCodeKeyPadDialog;
import fr.ihm.secureme.model.ContactListSingleton;
import fr.ihm.secureme.preferences.KeyPadPreference;

public class SplashScreenActivity extends ActionBarActivity/* implements KeyPadDialogCallBack*/{
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private String mFirstCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView appName = (TextView) findViewById(R.id.appname);
        appName.setTypeface(myTypeface);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String code = sharedPreferences.getString("pref_app_code", "NULL");
        /*if (code.equals("NULL")){
            AlertDialog addCodeDialog = new AlertDialog.Builder(this)
                    .setTitle("Bienvenue !")
                    .setMessage("Pour utiliser l'application, vous devez rentrer un code de sécurité")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        }*/
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

    /*@Override
    public void createCodeCallback(String code) {
        mFirstCode = code;
        ConfirmCodeKeyPadDialog confirmCodeKeyPadDialog = new ConfirmCodeKeyPadDialog(this, this);
        confirmCodeKeyPadDialog.show();
    }

    @Override
    public void confirmCodeCallback(String code) {
        if (mFirstCode.equals(code)) {
            saveCode();
            AlertDialog errorDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Code changé")
                    .setIcon(R.drawable.ic_check_circle_black_24dp)
                    .setMessage("Votre nouveau code est enregistré")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false)

                    .create();
            errorDialog.show();
        }
        else {
            AlertDialog errorDialog = new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setMessage("Les deux codes ne correspondent pas")
                    .setPositiveButton("Recommencer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CreateCodeKeyPadDialog keyPadDialog = new CreateCodeKeyPadDialog(getContext(), KeyPadPreference.this);
                            keyPadDialog.show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            errorDialog.show();
        }
    }

    @Override
    public void checkCodeCallback(String code) {

    }*/
}
