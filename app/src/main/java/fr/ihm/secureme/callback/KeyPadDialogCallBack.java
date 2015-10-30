package fr.ihm.secureme.callback;

/**
 * Created by nonau on 19/10/15.
 */
public interface KeyPadDialogCallBack {

    void createCodeCallback(String code);
    void confirmCodeCallback(String code);
    void checkCodeCallback(String code);
}
