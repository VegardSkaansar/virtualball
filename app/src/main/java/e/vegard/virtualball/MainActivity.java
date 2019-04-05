package e.vegard.virtualball;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.vegard.virtualball.Sound.SoundUtils;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {

    // Different manager for sensor and fragment
    public SensorManager mSensorManager;
    public FragmentManager mFragmentManager;
    public AudioManager audioManager;
    private SharedPreferences prefs;

    //const
    public final String SLIDER = "SLIDERVALUE";
    public final String PROGRESS = "PROGRESSVALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here we get the fragment Manager from this
        mFragmentManager = getSupportFragmentManager();

        // Here we get the sensors from the system
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Here we get the audiomanager
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Here we get the sharedPrefs
        prefs = getSharedPreferences(SLIDER, MODE_PRIVATE);

        // if we have this container
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            if (savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                    // if we have this accelerometer sensor we will display
                    // the virtual ball simulation
                    VirtualBallFragment ball = new VirtualBallFragment();
                    fragmentTransaction.add(R.id.fragment_container, ball, null);
                    fragmentTransaction.commit();

                } else {
                    // if we do not have the accelerometer sensor we will display
                    // fragment with a simple text saying the phone does not support the app
                    NoSensor noSensorFragment = new NoSensor();
                    fragmentTransaction.add(R.id.fragment_container, noSensorFragment, null);
                    fragmentTransaction.commit();
                }

                // setting default value on load
                if (!prefs.contains(PROGRESS)) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putInt(PROGRESS, 10);
                    edit.apply();
                }
            }
        }
    }


}

