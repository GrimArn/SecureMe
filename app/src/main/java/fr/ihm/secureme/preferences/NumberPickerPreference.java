package fr.ihm.secureme.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.NumberPickerCallBack;
import fr.ihm.secureme.dialog.NumberPickerDialog;

/**
 * Created by nonau on 10/11/15.
 */
public class NumberPickerPreference extends Preference implements NumberPickerCallBack{

    private int mCurrentNumber;
    private Context mContext;

    public NumberPickerPreference(Context context) {
        super(context);
        mContext = context;
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onClick() {
        super.onClick();
        Log.e("PREF", getKey());
        mCurrentNumber = getSharedPreferences().getInt(getKey(), 0);
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog(getContext(), this, mCurrentNumber, 0, 5);
        numberPickerDialog.setTitle(mContext.getResources().getString(R.string.time_alarm_trigger));
        numberPickerDialog.show();
    }

    @Override
    public void onNumberPicked(int number) {
        mCurrentNumber = number;
        getPreferenceManager().getSharedPreferences().edit().putInt(getKey(), mCurrentNumber).commit();
    }

    @Override
    public void onCancel() {

    }
}
