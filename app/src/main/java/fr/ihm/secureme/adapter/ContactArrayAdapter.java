package fr.ihm.secureme.adapter;

/**
 * Created by nonau on 10/10/15.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ihm.secureme.model.Contact;
import fr.ihm.secureme.R;
import fr.ihm.secureme.callback.ContactFragmentInterface;
import fr.ihm.secureme.model.ContactListSingleton;

import java.util.ArrayList;
import java.util.List;

public class ContactArrayAdapter extends ArrayAdapter<Contact>{
    private static final String TAG = "ContactArrayAdapter";
    private List<Contact> mContactArrayList = new ArrayList<Contact>();
    private ContactFragmentInterface mContactFragmentInterface;

    public void setContactAt(Contact c, int position) {
        mContactArrayList.set(position, c);
        notifyDataSetChanged();
    }

    static class ContactViewHolder {
        TextView tvNum;
        TextView tvMessage;
        ImageView ivGps;
    }

    public ContactArrayAdapter(Context context, int textViewResourceId, ContactFragmentInterface contactFragmentInterface) {
        super(context, textViewResourceId);
        mContactFragmentInterface = contactFragmentInterface;
    }

    @Override
    public void add(Contact object) {
        mContactArrayList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.mContactArrayList.size();
    }

    @Override
    public Contact getItem(int index) {
        return this.mContactArrayList.get(index);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ContactViewHolder viewHolder;
        Typeface myTypefaceRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface myTypefaceBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new ContactViewHolder();
            viewHolder.tvNum = (TextView) row.findViewById(R.id.num);
            viewHolder.tvMessage = (TextView) row.findViewById(R.id.message);
            viewHolder.ivGps = (ImageView) row.findViewById(R.id.gps);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ContactViewHolder) row.getTag();
        }
        final Contact contact = getItem(position);
        viewHolder.tvNum.setTypeface(myTypefaceBold);
        viewHolder.tvNum.setText(contact.getNum());
        viewHolder.tvMessage.setTypeface(myTypefaceRegular);
        viewHolder.tvMessage.setText(contact.getMessage());
        int res = contact.isGps() ? R.drawable.ic_gps_fixed_black_18dp : R.drawable.ic_gps_not_fixed_black_18dp;
        viewHolder.ivGps.setImageResource(res);

        ImageButton btDelete = (ImageButton) row.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteView(position);
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactFragmentInterface.dialogEdit(position);
            }
        });
        return row;
    }


    private void deleteView(int position) {
        mContactArrayList.remove(position);
        ContactListSingleton.getInstance().remove(position);
        notifyDataSetChanged();
        mContactFragmentInterface.deleteEvent(mContactArrayList.size());
    }
}