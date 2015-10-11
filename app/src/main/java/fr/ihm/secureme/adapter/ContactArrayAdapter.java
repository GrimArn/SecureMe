package fr.ihm.secureme.adapter;

/**
 * Created by nonau on 10/10/15.
 */import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ihm.secureme.Contact;
import fr.ihm.secureme.R;

import java.util.ArrayList;
import java.util.List;

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    private static final String TAG = "ContactArrayAdapter";
    private List<Contact> mContactArrayList = new ArrayList<Contact>();
    private View mRow;
    private CardView mCardView;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        mRow = convertView;
        mCardView = (CardView) convertView;
        final ContactViewHolder viewHolder;
        if (mRow == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mRow = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new ContactViewHolder();
            viewHolder.tvNum = (TextView) mRow.findViewById(R.id.num);
            viewHolder.tvMessage = (TextView) mRow.findViewById(R.id.message);
            viewHolder.ivGps = (ImageView) mRow.findViewById(R.id.gps);
            mRow.setTag(viewHolder);
        } else {
            viewHolder = (ContactViewHolder)mRow.getTag();
        }
        final Contact contact = getItem(position);
        viewHolder.tvNum.setText(contact.getNum());
        viewHolder.tvMessage.setText(contact.getMessage());
        int res = contact.isGps() ? R.drawable.ic_gps_fixed_black_18dp : R.drawable.ic_gps_not_fixed_black_18dp;
        viewHolder.ivGps.setImageResource(res);

        ImageButton btDelete = (ImageButton) mRow.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "tabDelete on num " + contact.getNum());
                viewHolder.tvMessage.setText("CLIIIICK");
                deleteView(position);
            }
        });
        mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        return mRow;
    }


    private void deleteView(int position) {
       mContactArrayList.remove(position);
        notifyDataSetChanged();
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}