package fr.ihm.secureme.adapter;

/**
 * Created by nonau on 10/10/15.
 */import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ihm.secureme.Contact;
import fr.ihm.secureme.R;

import java.util.ArrayList;
import java.util.List;

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    private static final String TAG = "ContactArrayAdapter";
    private List<Contact> mContactArrayList = new ArrayList<Contact>();

    static class ContactViewHolder {
        TextView tvNum;
        TextView tvMessage;
        ImageView ivGps;
    }

    public ContactArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new ContactViewHolder();
            viewHolder.tvNum = (TextView) row.findViewById(R.id.num);
            viewHolder.tvMessage = (TextView) row.findViewById(R.id.message);
            viewHolder.ivGps = (ImageView) row.findViewById(R.id.gps);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ContactViewHolder)row.getTag();
        }
        Contact contact = getItem(position);
        viewHolder.tvNum.setText(contact.getNum());
        viewHolder.tvMessage.setText(contact.getMessage());
        int res = contact.isGps() ? R.drawable.ic_gps_fixed_black_18dp : R.drawable.ic_gps_not_fixed_black_18dp;
        viewHolder.ivGps.setImageResource(res);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}