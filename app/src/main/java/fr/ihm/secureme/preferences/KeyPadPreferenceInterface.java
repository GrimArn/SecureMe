package fr.ihm.secureme.preferences;

/**
 * Created by nonau on 19/10/15.
 */
public interface KeyPadPreferenceInterface {

    void createCodeCallback(String code);
    void confirmCodeCallback(String code);
    void checkCodeCallback(String code);
}
