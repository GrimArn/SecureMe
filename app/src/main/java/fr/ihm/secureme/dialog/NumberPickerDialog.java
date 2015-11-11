package fr.ihm.secureme.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import fr.ihm.secureme.R;

/**
 * Created by nonau on 10/11/15.
 */
public class NumberPickerDialog extends Dialog {

    public NumberPickerDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.setContentView(R.layout.number_picker_dialog_layout);
        super.setTitle(context.getResources().getString(R.string.time_alarm_trigger));
    }




}
