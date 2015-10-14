package fr.ihm.secureme;

/**
 * Created by nonau on 10/10/15.
 */
public class Contact {

    private String mNum;
    private String mFullName;
    private String mMessage;
    private boolean mIsGps;

    public Contact(String num, String message, boolean isGps) {
        mNum = num;
        mMessage = message;
        mIsGps = isGps;
    }

    public Contact(String num, String fullName, String message, boolean isGps) {
        mNum = num;
        mFullName = fullName;
        mMessage = message;
        mIsGps = isGps;
    }

    public String getNum() {
        return mNum;
    }

    public void setNum(String num) {
        mNum = num;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public boolean isGps() {
        return mIsGps;
    }

    public void setIsGps(boolean isGps) {
        mIsGps = isGps;
    }

    public String getFullName()  {
        if (mFullName == null  || mFullName.equals("") )
            return "";
        else {
            return "("+mFullName+")";
        }
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }
}
