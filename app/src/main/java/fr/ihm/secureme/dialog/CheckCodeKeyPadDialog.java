package fr.ihm.secureme.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import fr.ihm.secureme.R;
import fr.ihm.secureme.preferences.KeyPadPreferenceInterface;

/**
 * Created by nonau on 18/10/15.
 */
public class CheckCodeKeyPadDialog extends AbstractKeyPadDialog {
    public CheckCodeKeyPadDialog(Context context, KeyPadPreferenceInterface keyPadPreferenceInterface) {
        super(context, keyPadPreferenceInterface);

    }

    public CheckCodeKeyPadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CheckCodeKeyPadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTitle(getContext().getString(R.string.enter_code));
        super.setMessage(getContext().getString(R.string.input_old_text));
        super.onCreate(savedInstanceState);
        super.setPositiveButtonText(getContext().getString(R.string.ok));
        setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCode();
                dismiss();
                mKeyPadPreferenceInterface.checkCodeCallback(getCode());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
