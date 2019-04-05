package e.vegard.virtualball.Sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import e.vegard.virtualball.MainActivity;
import e.vegard.virtualball.R;

public class SoundUtils {
    private Context context;
    private MediaPlayer mediaPlayerStart;
    private MediaPlayer mediaPlayerDone;



    public SoundUtils(Context context) {

        this.context = context;
        mediaPlayerStart = MediaPlayer.create(context, R.raw.fotball_kick);
        mediaPlayerStart.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayerStart.setVolume(100,100);
        mediaPlayerDone = MediaPlayer.create(context, R.raw.football_land);
    }

    public void playStartSound() {

        mediaPlayerStart.start();
    }

    public void playDoneSound() {
        mediaPlayerDone.start();
    }

    public void releaseSound() {
        mediaPlayerStart.release();
        mediaPlayerDone.release();
    }


}
