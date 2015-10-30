package fr.ihm.secureme.model;

/**
 * Created by nonau on 10/10/15.
 */
public class Contact {

    private String mNum;
    private String mFormatedNum;
    private String mMessage;
    private boolean mIsGps;

    public Contact(String num, String message, boolean isGps) {
        mNum = num;
        setFormatedNum(mNum);
        mMessage = message;
        mIsGps = isGps;
    }

    public Contact(String num, String formatedNum, String message, boolean isGps) {
        mNum = num;
        mFormatedNum = formatedNum;
        mMessage = message;
        mIsGps = isGps;
    }

    public String getNum() {
        return mNum;
    }

    public void setNum(String num) {
        mNum = num;
        setFormatedNum(mNum);
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

    public void setFormatedNum (String num){
        if (num.charAt(0) == '0'){
            mFormatedNum = "+33";
            for (int i = 1; i < num.length(); ++i) {
                mFormatedNum += num.charAt(i);
            }
        }
        else {
            mFormatedNum = num;
        }
        mFormatedNum = mFormatedNum.replaceAll("\\s","");
    }

    public String getFormatedNum() {
        return mFormatedNum;
    }

}
