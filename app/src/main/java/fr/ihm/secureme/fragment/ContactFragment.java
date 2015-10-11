package fr.ihm.secureme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import fr.ihm.secureme.Contact;
import fr.ihm.secureme.R;
import fr.ihm.secureme.adapter.ContactArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {



    private static final String TAG = "CardListActivity";
    private ContactArrayAdapter mContactArrayAdapter;
    private ListView listView;
    private View mView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_contact, container, false);
        Log.e(TAG, "coucou");
        listView = (ListView) mView.findViewById(R.id.card_listView);

        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));

        mContactArrayAdapter = new ContactArrayAdapter(getActivity().getApplicationContext(), R.layout.list_item_card);
        Contact contact = new Contact("0616797537", "LOST", true);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact); contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);
        contact = new Contact("0657815637", "STOLE", false);
        mContactArrayAdapter.add(contact);

        Log.e(TAG, "test on num " + contact.getNum());
        listView.setAdapter(mContactArrayAdapter);
        return mView;
    }


}
