package fr.ihm.secureme.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import fr.ihm.secureme.R;
import fr.ihm.secureme.preferences.KeyPadPreference;
import fr.ihm.secureme.preferences.KeyPadPreferenceInterface;

/**
 * Created by nonau on 18/10/15.
 */
public class CreateCodeKeyPadDialog extends AbstractKeyPadDialog {
    public CreateCodeKeyPadDialog(Context context, KeyPadPreferenceInterface keyPadPreferenceInterface) {
        super(context, keyPadPreferenceInterface);

    }

    public CreateCodeKeyPadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CreateCodeKeyPadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTitle(getContext().getString(R.string.define_code_text));
        super.setMessage(getContext().getString(R.string.input_new_code));
        super.onCreate(savedInstanceState);
        super.setPositiveButtonText(getContext().getString(R.string.define));
        setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCode();
                dismiss();
                mKeyPadPreferenceInterface.createCodeCallback(getCode());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
