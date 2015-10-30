package fr.ihm.secureme.model;

import android.content.Context;
import android.util.Log;
import fr.ihm.secureme.json.ContactJsonReader;
import fr.ihm.secureme.json.ContactJsonWriter;
import fr.ihm.secureme.tools.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nonau on 16/10/15.
 */
public class ContactListSingleton {

    private List<Contact> mContactList;
    private static ContactListSingleton INSTANCE = null;

    private ContactListSingleton() {
    }

    public static ContactListSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContactListSingleton();
            INSTANCE.mContactList = new ArrayList<Contact>();
        }

        return INSTANCE;
    }
    public void add (Contact c) {
        INSTANCE.mContactList.add(c);
    }
    public void setContactList(List<Contact> contactList) {
        INSTANCE.mContactList = contactList;
    }
    public List<Contact> getContactList(){
        return INSTANCE.mContactList;
    }

    public List<Contact> loadContactList(Context c) {
        File file = new File(c.getExternalCacheDir(), Constants.CONTACT_FILE);
        List<Contact> contacts;
        try
        {
            FileInputStream inputStream = new FileInputStream(file);
            ContactJsonReader contactJsonReader = new ContactJsonReader(inputStream);
            INSTANCE.mContactList = contactJsonReader.readJsonStream();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return INSTANCE.mContactList;
    }
    public void saveContactList(Context c) {
        File file = new File(c.getExternalCacheDir(), Constants.CONTACT_FILE);
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            ContactJsonWriter contactJsonWriter = new ContactJsonWriter(out);
            contactJsonWriter.writeJsonStream(INSTANCE.mContactList);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void remove(int position) {
        INSTANCE.mContactList.remove(position);
    }

    public void setContactAt(Contact c, int contactAt) {
        INSTANCE.mContactList.set(contactAt, c);
    }
}
