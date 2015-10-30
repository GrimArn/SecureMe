package fr.ihm.secureme.callback;

import fr.ihm.secureme.model.Contact;

/**
 * Created by nonau on 12/10/15.
 */
public interface ContactFragmentInterface {
    void deleteEvent(int itemCount);
    void dialogCancelHandler();
    void dialogCallBackHandler(Contact c);
    void dialogEdit(int position);
    void dialogEditCallback(Contact c, int postion);

}
