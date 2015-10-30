package fr.ihm.secureme.json;

import fr.ihm.secureme.model.Contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by nonau on 16/10/15.
 */
public abstract class AbstractJsonReader {
    protected InputStream mInputStream;

    public AbstractJsonReader (InputStream inputStream) {
        mInputStream = inputStream;
    }

    public abstract List<Contact> readJsonStream() throws IOException;
}
