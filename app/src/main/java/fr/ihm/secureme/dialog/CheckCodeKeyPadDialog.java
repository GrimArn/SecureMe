package fr.ihm.secureme.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.KeyPadDialogCallBack;

/**
 * Created by nonau on 18/10/15.
 */
public class CheckCodeKeyPadDialog extends AbstractKeyPadDialog {
    public CheckCodeKeyPadDialog(Context context, KeyPadDialogCallBack keyPadDialogCallBack) {
        super(context, keyPadDialogCallBack);

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
                mKeyPadDialogCallBack.checkCodeCallback(getCode());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
