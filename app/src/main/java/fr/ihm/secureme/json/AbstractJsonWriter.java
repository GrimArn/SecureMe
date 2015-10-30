package fr.ihm.secureme.json;

import android.content.Context;
import fr.ihm.secureme.model.Contact;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by nonau on 15/10/15.
 */
public abstract class AbstractJsonWriter {

    protected OutputStream mOutputStream;

    public AbstractJsonWriter(OutputStream out) throws FileNotFoundException {
        mOutputStream = out;
    }

    public abstract void writeJsonStream(List l) throws IOException;

}
