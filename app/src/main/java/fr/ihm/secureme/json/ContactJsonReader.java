package fr.ihm.secureme.json;

import android.util.JsonReader;
import fr.ihm.secureme.model.Contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nonau on 16/10/15.
 */
public class ContactJsonReader extends AbstractJsonReader {

    public static final String NUM = "num";
    public static final String MESS = "mess";
    public static final String ISGPS = "gps";


    public ContactJsonReader(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public List<Contact> readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(mInputStream, "UTF-8"));
        try {
            return readContactArray(reader);
        }
        finally{
            reader.close();
        }
    }


    private List<Contact> readContactArray(JsonReader reader) throws IOException {
        List<Contact> contacts = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            contacts.add(readContact(reader));
        }
        reader.endArray();
        return contacts;
    }

    private Contact readContact(JsonReader reader) throws IOException {
        String num = "";
        String mess = "";
        boolean gps = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals(NUM)) {
                num = reader.nextString();

            } else if (name.equals(MESS)) {
                mess = reader.nextString();

            } else if (name.equals(ISGPS)) {
                gps = reader.nextBoolean();
            }
        }
        reader.endObject();
        return new Contact(num, mess, gps);
    }
}