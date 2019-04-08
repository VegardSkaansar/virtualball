package e.vegard.virtualball.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.vegard.virtualball.Database.DatabaseWrapper;
import e.vegard.virtualball.Fragment.NoSensor;
import e.vegard.virtualball.Fragment.ScoreModel;
import e.vegard.virtualball.Fragment.VirtualBallFragment;
import e.vegard.virtualball.R;
import e.vegard.virtualball.dialog.Dialog;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListner {

    // Different manager for sensor and fragment
    public SensorManager mSensorManager;
    public FragmentManager mFragmentManager;
    public DatabaseWrapper database;
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

        // Here we get the Database
        database = new DatabaseWrapper(getApplicationContext(), "scoredb");

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

    // starts the popup window and sends with the info we need for adding
    // into database
    public void setPopup(int id, double score, double distance, double time) {

        Bundle bundle = new Bundle();
        bundle.putDouble("score", score);
        bundle.putDouble("distance", distance);
        bundle.putDouble("time", time);
        bundle.putInt("id", id);

        Dialog dialog = new Dialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "dialog");
    }



    @Override
    public void applyTexts(int id, String name, double score, double distance, double time) {
        if (id == -1) {
            ScoreModel newScore = new ScoreModel(name, score, distance, time);
            database.insertToDBScore(newScore);
        } else {
            ScoreModel newScore = new ScoreModel(name, score, distance, time);
            database.UpdateScore(newScore, id);
        }

    }
}

