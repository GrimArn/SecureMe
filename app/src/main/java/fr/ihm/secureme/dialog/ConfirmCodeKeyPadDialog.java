package fr.ihm.secureme.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.KeyPadDialogCallBack;

/**
 * Created by nonau on 18/10/15.
 */
public class ConfirmCodeKeyPadDialog extends AbstractKeyPadDialog {

    public ConfirmCodeKeyPadDialog(Context context, KeyPadDialogCallBack keyPadDialogCallBack) {
        super(context, keyPadDialogCallBack);

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
        super.setMessage(getContext().getString(R.string.input_confirm_code));
        super.onCreate(savedInstanceState);
        super.setPositiveButtonText(getContext().getString(R.string.confirm_text));
        setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCode();
                dismiss();
                mKeyPadDialogCallBack.confirmCodeCallback(getCode());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }
}
