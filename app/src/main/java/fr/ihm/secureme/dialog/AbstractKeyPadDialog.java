package fr.ihm.secureme.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import fr.ihm.secureme.R;
import fr.ihm.secureme.preferences.KeyPadPreferenceInterface;

/**
 * Created by nonau on 17/10/15.
 */
public abstract class AbstractKeyPadDialog extends Dialog {

    private TextView mCodeField;

    protected Button mButtonZero;
    protected Button mButtonOne;
    protected Button mButtonTwo;
    protected Button mButtonThree;
    protected Button mButtonFour;
    protected Button mButtonFive;
    protected Button mButtonSix;
    protected Button mButtonSeven;
    protected Button mButtonEight;
    protected Button mButtonNine;

    private String mCode;

    protected Button mPositiveButton;
    protected Button mNegativeButton;
    protected Button mClearButton;
    protected TextView mMessageDialog;

    protected KeyPadPreferenceInterface mKeyPadPreferenceInterface;


    public AbstractKeyPadDialog(Context context, KeyPadPreferenceInterface keyPadPreferenceInterface) {
        super(context);
        mKeyPadPreferenceInterface = keyPadPreferenceInterface;
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.setContentView(R.layout.keypad_layout);
        mMessageDialog = (TextView) findViewById(R.id.messagetext);
    }

    public AbstractKeyPadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AbstractKeyPadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_dialpad_black_24dp);
        init();

    }
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    public void setMessage(CharSequence message) {
        mMessageDialog.setText(message);
    }

    public void setPositiveButtonText(CharSequence text) {
        mPositiveButton.setText(text);

    }


    private void init() {
        mCodeField = (TextView) findViewById(R.id.code_tv);
        mPositiveButton = (Button) findViewById(R.id.positive_button);
        mNegativeButton = (Button) findViewById(R.id.negative_button);
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mClearButton = (Button) findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText("");
                mPositiveButton.setEnabled(false);
                mClearButton.setEnabled(false);
                enableKeyPad(true);
            }
        });
        mPositiveButton.setEnabled(false);
        mClearButton.setEnabled(false);

        initTextField();
        initKeyPad();
    }
    private void initKeyPad() {
        mButtonZero = (Button) findViewById(R.id.button0);
        mButtonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "0");
                onKeyPressed("0");
            }
        });
        mButtonOne = (Button) findViewById(R.id.button1);
        mButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "1");
                onKeyPressed("1");
            }
        });
        mButtonTwo = (Button) findViewById(R.id.button2);
        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "2");
                onKeyPressed("2");
            }
        });
        mButtonThree = (Button) findViewById(R.id.button3);
        mButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "3");
                onKeyPressed("3");
            }
        });
        mButtonFour = (Button) findViewById(R.id.button4);
        mButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "4");
                onKeyPressed("4");
            }
        });
        mButtonFive = (Button) findViewById(R.id.button5);
        mButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "5");
                onKeyPressed("5");
            }
        });
        mButtonSix = (Button) findViewById(R.id.button6);
        mButtonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "6");
                onKeyPressed("6");
            }
        });
        mButtonSeven = (Button) findViewById(R.id.button7);
        mButtonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "7");
                onKeyPressed("7");
            }
        });
        mButtonEight = (Button) findViewById(R.id.button8);
        mButtonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "8");
                onKeyPressed("8");
            }
        });
        mButtonNine = (Button) findViewById(R.id.button9);
        mButtonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeField.setText(mCodeField.getText() + "9");
                onKeyPressed("9");
            }
        });
    }

    protected void setPositiveButtonListener(final View.OnClickListener listener) {
        mPositiveButton.setOnClickListener(listener);
    }
    private void initTextField() {
        mCodeField.setFocusable(false);
    }

    public String getCode() {
        return mCode;
    }

    protected String setCode() {
        mCode = mCodeField.getText().toString();
        return getCode();
    }

    private void onKeyPressed(String key){
        if (mCodeField.length() == 0) {
            mClearButton.setEnabled(true);
            enableKeyPad(true);
        }
        else if (mCodeField.length() < 4) {
            mPositiveButton.setEnabled(false);
            mClearButton.setEnabled(true);
            enableKeyPad(true);
        }
        else if (mCodeField.length() == 4) {
            mClearButton.setEnabled(true);
            mPositiveButton.setEnabled(true);
            enableKeyPad(false);
        }else {
            mClearButton.setEnabled(true);
            mPositiveButton.setEnabled(false);
            enableKeyPad(false);
        }

    }

    private void enableKeyPad(boolean enable) {
        mButtonZero.setEnabled(enable);
        mButtonOne.setEnabled(enable);
        mButtonTwo.setEnabled(enable);
        mButtonThree.setEnabled(enable);
        mButtonFour.setEnabled(enable);
        mButtonFive.setEnabled(enable);
        mButtonSix.setEnabled(enable);
        mButtonSeven.setEnabled(enable);
        mButtonEight.setEnabled(enable);
        mButtonNine.setEnabled(enable);
    }
}
