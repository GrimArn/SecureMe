package fr.ihm.secureme.manager;

import android.content.Context;
import android.media.MediaPlayer;
import fr.ihm.secureme.R;

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
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.alarm);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
        isSoundPlaying = true;
    }

    public void stopAlarm(){
        if (isSoundPlaying) {
            mMediaPlayer.stop();
            isSoundPlaying = true;
        }
    }
}
