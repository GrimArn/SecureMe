package fr.ihm.secureme.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import fr.ihm.secureme.AddContactDialog;
import fr.ihm.secureme.model.Contact;
import fr.ihm.secureme.R;
import fr.ihm.secureme.adapter.ContactArrayAdapter;
import fr.ihm.secureme.callback.ContactFragmentInterface;
import fr.ihm.secureme.model.ContactListSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactFragmentInterface {

    private static final String TAG = "CardListActivity";

    private ContactArrayAdapter mContactArrayAdapter;
    private ListView mListview;
    private LayoutInflater mLayoutInflater;
    private View mFragmentView;
    private FloatingActionButton mActionButton;
    public static CharSequence sTitle = "Contacts d'urgence";
    private State mState;
    private State mDialogHistoState;

    public enum State {
        EMPTY,
        DIALOG,
        FILLED
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        init(inflater, container);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonEventHandler();
            }
        });
        return mFragmentView;

    }

    private void init(LayoutInflater inflater, ViewGroup container) {

        /**INIT LAYOUT & VARIABLES **/
        mFragmentView = inflater.inflate(R.layout.fragment_contact, container, false);
        mLayoutInflater = inflater;
        mActionButton = (FloatingActionButton) mFragmentView.findViewById(R.id.fab);
        mContactArrayAdapter = new ContactArrayAdapter(getActivity().getApplicationContext(), R.layout.list_item_card, this);

        //On récupere les contacts en base
        List<Contact> listContact = getContactList();

        /**ON AFFICHE LE BON LAYOUT EN FONCTION DU NOMBRE DE CONTACT **/
        if (listContact.size() == 0) {
            mState = State.EMPTY;
            View.inflate(getActivity(), R.layout.empty_message_layout, (ViewGroup) mFragmentView);
        }
        else {
            mState = State.FILLED;
            for (Contact contact : listContact) mContactArrayAdapter.add(contact);
            View.inflate(getActivity(), R.layout.contacts_list_layout, (ViewGroup) mFragmentView);
            mListview = (ListView) mFragmentView.findViewById(R.id.card_listView);
            mListview.addHeaderView(new View(getActivity()));
            mListview.addFooterView(new View(getActivity()));
            mListview.setAdapter(mContactArrayAdapter);
        }


    }


    private void addButtonEventHandler(){
        AddContactDialog addContactDialog;
        switch (mState) {
            case EMPTY:
                mState = State.DIALOG;
                mDialogHistoState = State.EMPTY;
                addContactDialog = new AddContactDialog();
                addContactDialog.setContactFragmentInterface(this);
                addContactDialog.show(getActivity(), "addContactDialog");
                break;
            case FILLED:
                mState = State.DIALOG;
                mDialogHistoState = State.FILLED;
                addContactDialog = new AddContactDialog();
                addContactDialog.setContactFragmentInterface(this);
                addContactDialog.show(getActivity(), "addContactDialog");
                break;
            case DIALOG:
                //Interdit
        }
    }
    @Override
    public void dialogCallBackHandler(Contact c) {
        switch (mState) {
            case EMPTY: case FILLED:
                //Interdit
            case DIALOG:
                if (mDialogHistoState == State.EMPTY) {
                    mState = State.FILLED;
                    changeLayoutInView(mFragmentView.findViewById(R.id.ll_empty), R.layout.contacts_list_layout);
                } else if (mDialogHistoState == State.FILLED){
                    mState = State.FILLED;
                }
                ContactListSingleton.getInstance().add(c);
                mContactArrayAdapter.add(c);
                break;
        }
    }

    @Override
    public void dialogCancelHandler() {
        switch (mState) {
            case EMPTY: case FILLED:
            //Interdit
                break;
            case DIALOG:
                mState = mDialogHistoState;
                break;
        }
    }

    private void deleteEventHandler(int itemCount) {
        View view = mFragmentView.findViewById(R.id.container);
        switch (mState) {
            case EMPTY:
                //'Interdit'
                break;
            case FILLED:
                if (itemCount > 0) {
                    mState = State.FILLED;
                    break;
                }
                else if (itemCount == 0) {
                    mState = State.EMPTY;
                    changeLayoutInView(mFragmentView.findViewById(R.id.linear_listView), R.layout.empty_message_layout);
                    break;
                }
                break;
            case DIALOG:
                //Interdit
        }
    }

    @Override
    public void deleteEvent(int itemCount) {
        deleteEventHandler(itemCount);
    }


    void changeLayoutInView (View view, int ressource) {
        ViewGroup parent = (ViewGroup) view.getParent();
        int index = parent.indexOfChild(view);
        parent.removeView(view);
        view = mLayoutInflater.inflate(ressource, parent, false);
        parent.addView(view, index);
        if (ressource == R.layout.contacts_list_layout) {
            ListView listView = (ListView) mFragmentView.findViewById(R.id.card_listView);
            listView.addHeaderView(new View(getActivity()));
            listView.addFooterView(new View(getActivity()));
            listView.setAdapter(mContactArrayAdapter);
        }
    }


    public List<Contact> getContactList() {
        return ContactListSingleton.getInstance().getContactList();
    }

    @Override
    public void onPause() {
        ContactListSingleton.getInstance().saveContactList(getActivity());
        super.onPause();

    }
}
