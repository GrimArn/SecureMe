package fr.ihm.secureme.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;
import fr.ihm.secureme.R;
import fr.ihm.secureme.tools.Constants;

/**
 * Created by nonau on 30/10/15.
 */
public class SoundAlarmManager {

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private boolean isSoundPlaying = false;

    public SoundAlarmManager(Context c) {
        mContext = c;
        mMediaPlayer = new MediaPlayer();
    }

    public void startAlarm() {
        if (!Constants.IS_ENVIRONMENT_QUIET) {
            mMediaPlayer = MediaPlayer.create(mContext, R.raw.alarm);
            mMediaPlayer.start();
            mMediaPlayer.setLooping(true);
            isSoundPlaying = true;
            //Toast.makeText(mContext, "AlarmStartFOREAL", Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(mContext, "AlarmStart", Toast.LENGTH_LONG).show();
        }
    }

    public void stopAlarm(){
        if (!Constants.IS_ENVIRONMENT_QUIET) {
            if (isSoundPlaying) {
                mMediaPlayer.stop();
                isSoundPlaying = true;
            }
            //Toast.makeText(mContext, "AlarmStOPFOREAL", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "AlarmStop", Toast.LENGTH_LONG).show();
        }

    }
}
