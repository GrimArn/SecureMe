package fr.ihm.secureme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import fr.ihm.secureme.callback.ContactFragmentInterface;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;

/**
 * Created by nonau on 13/10/15.
 */
public class AddContactDialog extends DialogFragment {

    ContactFragmentInterface mContactFragmentInterface;
    AlertDialog.Builder mBuilder;
    private static final int REQUEST_CONTACT_NUMBER = 123456789;
    EditText numTextField;
    EditText messTextField;
    ImageButton indexImageButton;
    CheckBox gpsCheckbox;
    String currentName;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         mBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v  = inflater.inflate(R.layout.add_contact_dialog, null);


        numTextField = (EditText) v.findViewById(R.id.num_edittext);
        messTextField = (EditText) v.findViewById(R.id.mess_edittext);
        indexImageButton = (ImageButton) v.findViewById(R.id.bt_index);
        gpsCheckbox = (CheckBox) v.findViewById(R.id.gps_check);

        indexImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, REQUEST_CONTACT_NUMBER);
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mBuilder.setView(v)
                .setPositiveButton("AJOUTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mContactFragmentInterface.dialogCallBackHandler(new Contact(numTextField.getText().toString(),
                                currentName , messTextField.getText().toString(), gpsCheckbox.isChecked()));
                    }
                })
                .setIcon(R.drawable.ic_person_add_black_24dp)
                .setTitle("Ajouter un contact d'urgence")
                .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddContactDialog.this.getDialog().cancel();
                        mContactFragmentInterface.dialogCancelHandler();
                    }
                });
        return mBuilder.create();
    }

    public void setContactFragmentInterface(ContactFragmentInterface fragmentInterface) {
        mContactFragmentInterface = fragmentInterface;
    }

    public void show(Activity activity, String dial) {
        super.show(activity.getFragmentManager(), dial);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if(data != null && requestCode == REQUEST_CONTACT_NUMBER) {
                Uri uriOfPhoneNumberRecord = data.getData();
                String idOfPhoneRecord = uriOfPhoneNumberRecord.getLastPathSegment();
                Cursor cursor = getActivity().getContentResolver().query(Phone.CONTENT_URI, new String[]{Phone.NUMBER}, Phone._ID + "=?", new String[]{idOfPhoneRecord}, null);
                if(cursor != null) {
                    if(cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        String formattedPhoneNumber = cursor.getString( cursor.getColumnIndex(Phone.NUMBER) );
                        //àcurrentName =  cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) );
                        numTextField.setText(formattedPhoneNumber);
                    }
                    cursor.close();
                }
            }
            else {
                Log.w("TestActivity", "WARNING: Corrupted request response");
            }
        }
        else if (resultCode == getActivity().RESULT_CANCELED) {
            Log.i("TestActivity", "Popup canceled by user.");
        }
        else {
            Log.w("TestActivity", "WARNING: Unknown resultCode");
        }
    }

}
