package fr.ihm.secureme.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.KeyPadDialogCallBack;
import fr.ihm.secureme.dialog.CheckCodeKeyPadDialog;
import fr.ihm.secureme.dialog.ConfirmCodeKeyPadDialog;
import fr.ihm.secureme.dialog.CreateCodeKeyPadDialog;

/**
 * Created by nonau on 17/10/15.
 */
public class KeyPadPreference extends Preference implements KeyPadDialogCallBack {


    String mFirstCode;
    String mConfirmCode;

    public KeyPadPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public KeyPadPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void saveCode() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(getKey(), mConfirmCode);
        editor.commit();
    }

    @Override
    protected void onClick() {
        super.onClick();
        SharedPreferences sharedPreferences = getSharedPreferences();
        String code = sharedPreferences.getString(getKey(), "NULL");
        Log.e("TAG", code);
        if (code.equals("NULL")) {
            CreateCodeKeyPadDialog keyPadDialog = new CreateCodeKeyPadDialog(getContext(), this);
            keyPadDialog.show();
        } else {
            mConfirmCode = code;
            CheckCodeKeyPadDialog checkCodeKeyPadDialog = new CheckCodeKeyPadDialog(getContext(), this);
            checkCodeKeyPadDialog.show();
        }

    }

    @Override
    public void createCodeCallback(String code) {
        mFirstCode = code;
        ConfirmCodeKeyPadDialog confirmCodeKeyPadDialog = new ConfirmCodeKeyPadDialog(getContext(), this);
        confirmCodeKeyPadDialog.show();
    }

    @Override
    public void confirmCodeCallback(String code) {
        mConfirmCode = code;
        if (mFirstCode.equals(mConfirmCode)) {
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
            AlertDialog errorDialog = new AlertDialog.Builder(getContext())
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
        if (mConfirmCode.equals(code)) {
            CreateCodeKeyPadDialog keyPadDialog = new CreateCodeKeyPadDialog(getContext(), this);
            keyPadDialog.show();
        } else {
            AlertDialog errorDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Erreur")
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setMessage("Le code est incorrect")
                    .setPositiveButton("FERMER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            errorDialog.show();
        }
    }
}
