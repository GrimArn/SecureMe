package fr.ihm.secureme.json;

import android.content.Context;
import android.util.JsonWriter;
import fr.ihm.secureme.model.Contact;

import java.io.*;
import java.util.List;

/**
 * Created by nonau on 15/10/15.
 */
public class ContactJsonWriter extends AbstractJsonWriter {

    public ContactJsonWriter(OutputStream out) throws FileNotFoundException {
        super(out);
    }

    @Override
    public void writeJsonStream(List contacts) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(mOutputStream, "UTF-8"));
        writer.setIndent("  ");
        writeContactArray(writer, contacts);
        writer.close();
    }

    private void writeContactArray(JsonWriter writer, List contacts) throws IOException {
        List<Contact> contactList = contacts;
        writer.beginArray();
        for (Contact contact : contactList) {
            writeContact(writer, contact);
        }
        writer.endArray();
    }

    private void writeContact(JsonWriter writer, Contact contact) throws IOException {
        writer.beginObject();
        writer.name("num").value(contact.getNum());
        writer.name("formatednum").value(contact.getFormatedNum());
        writer.name("mess").value(contact.getMessage());
        writer.name("gps").value(contact.isGps());
        writer.endObject();
    }
}
