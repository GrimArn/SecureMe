package fr.ihm.secureme.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import fr.ihm.secureme.dialog.AddContactDialog;
import fr.ihm.secureme.model.Contact;
import fr.ihm.secureme.R;
import fr.ihm.secureme.adapter.ContactArrayAdapter;
import fr.ihm.secureme.callback.ContactFragmentInterface;
import fr.ihm.secureme.model.ContactListSingleton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactFragmentInterface, TitleFragmentInterface {

    private static final String TAG = "CardListActivity";

    private ContactArrayAdapter mContactArrayAdapter;
    private ListView mListview;
    private LayoutInflater mLayoutInflater;
    private View mFragmentView;
    private FloatingActionButton mActionButton;
    public static CharSequence sTitle;
    private State mState;
    private State mDialogHistoState;
    private AddContactDialog mAddContactDialog;

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
        sTitle = getActivity().getString(R.string.emergency_contacts_title);

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
            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e(TAG, "OnItemClivk");
                    Contact c = mContactArrayAdapter.getItem(position);
                    AddContactDialog addContactDialog;
                    addContactDialog = new AddContactDialog();
                    Bundle args = new Bundle();
                    args.putBoolean("isEdit", true);
                    args.putString("num", c.getNum());
                    args.putString("mess", c.getMessage());
                    args.putBoolean("gps", c.isGps());
                    addContactDialog.setArguments(args);
                    addContactDialog.setContactFragmentInterface(ContactFragment.this);
                    addContactDialog.show(getActivity(), "addContactDialog");

                }
            });
            mListview.setOnScrollListener(new AbsListView.OnScrollListener() {
                int mLastFirstVisibleItem = 0;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {   }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    /*Log.e(TAG, "onscrll");
                    if (view.getId() == mListview.getId()) {
                        final int currentFirstVisibleItem = mListview.getFirstVisiblePosition();

                        if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                            // getSherlockActivity().getSupportActionBar().hide();
                            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                        } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                            // getSherlockActivity().getSupportActionBar().show();
                            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                        }

                        mLastFirstVisibleItem = currentFirstVisibleItem;
                    }*/
                }
            });
        }


    }
    @Override
    public CharSequence getTitle() {
        return sTitle;
    }

    private void addButtonEventHandler(){
        switch (mState) {
            case EMPTY:
                mState = State.DIALOG;
                mDialogHistoState = State.EMPTY;
                mAddContactDialog = new AddContactDialog();
                mAddContactDialog.setContactFragmentInterface(this);
                mAddContactDialog.setArguments(new Bundle());
                mAddContactDialog.show(getActivity(), "addContactDialog");
                break;
            case FILLED:
                mState = State.DIALOG;
                mDialogHistoState = State.FILLED;
                mAddContactDialog = new AddContactDialog();
                mAddContactDialog.setContactFragmentInterface(this);
                mAddContactDialog.setArguments(new Bundle());
                mAddContactDialog.show(getActivity(), "addContactDialog");
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
    public void dialogEdit(int position) {
        switch (mState) {
            case FILLED:
                mDialogHistoState = State.FILLED;
                AddContactDialog addContactDialog = new AddContactDialog();
                Contact c = mContactArrayAdapter.getItem(position);
                Bundle args = new Bundle();
                args.putBoolean("isEdit", true);
                args.putString("num", c.getNum());
                args.putString("mess", c.getMessage());
                args.putBoolean("gps", c.isGps());
                args.putInt("pos", position);
                addContactDialog.setArguments(args);
                addContactDialog.setContactFragmentInterface(this);
                addContactDialog.show(getActivity(), "addContactDialog");
        }
    }

    @Override
    public void dialogEditCallback(Contact c, int position) {
        mContactArrayAdapter.setContactAt(c, position);
        ContactListSingleton.getInstance().setContactAt(c, position);

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
        Log.e(TAG, "onpause");
        ContactListSingleton.getInstance().saveContactList(getActivity());
        super.onPause();
    }


}
