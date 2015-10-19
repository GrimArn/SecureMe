package fr.ihm.secureme.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import fr.ihm.secureme.R;
import fr.ihm.secureme.preferences.KeyPadPreferenceInterface;

/**
 * Created by nonau on 18/10/15.
 */
public class ConfirmCodeKeyPadDialog extends AbstractKeyPadDialog {

    public ConfirmCodeKeyPadDialog(Context context, KeyPadPreferenceInterface keyPadPreferenceInterface) {
        super(context, keyPadPreferenceInterface);

    }

    public ConfirmCodeKeyPadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmCodeKeyPadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTitle(getContext().getString(R.string.confirm_code_text));
        super.onCreate(savedInstanceState);
        super.setPositiveButtonText(getContext().getString(R.string.confirm_text));
        setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCode();
                dismiss();
                mKeyPadPreferenceInterface.confirmCodeCallback(getCode());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
