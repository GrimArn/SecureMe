package fr.ihm.secureme.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import fr.ihm.secureme.dialog.NumberPickerDialog;

/**
 * Created by nonau on 10/11/15.
 */
public class NumberPickerPreference extends Preference {
    public NumberPickerPreference(Context context) {
        super(context);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        super.onClick();
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog(getContext());
        numberPickerDialog.show();
    }
}
