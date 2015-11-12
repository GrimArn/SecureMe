package fr.ihm.secureme.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.NumberPickerCallBack;
import fr.ihm.secureme.views.MinMaxNumberPicker;

/**
 * Created by nonau on 10/11/15.
 */
public class NumberPickerDialog extends Dialog {

    private int mCurrentNumber;
    private MinMaxNumberPicker mMinMaxNumberPicker;
    private Button mPositiveButton;
    private Button mNegativeButton;
    private NumberPickerCallBack mNumberPickerCallBack;
    private int mMin;
    private int mMax;

    public NumberPickerDialog(Context context, NumberPickerCallBack numberPickerCallBack, int currentNumber, int min, int max) {
        super(context);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.setContentView(R.layout.number_picker_dialog_layout);
        mCurrentNumber = currentNumber;
        mNumberPickerCallBack = numberPickerCallBack;
        mMin = min;
        mMax = max;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mPositiveButton = (Button) findViewById(R.id.positive_button);
        mNegativeButton = (Button) findViewById(R.id.negative_button);
        mMinMaxNumberPicker = (MinMaxNumberPicker) findViewById(R.id.number_picker_min_max);
        mMinMaxNumberPicker.setValue(mCurrentNumber);
        mMinMaxNumberPicker.setAttributeSet(mMin, mMax);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberPickerCallBack.onNumberPicked(mMinMaxNumberPicker.getValue());
                dismiss();
            }
        });

        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberPickerCallBack.onCancel();
                dismiss();
            }
        });
    }

}
