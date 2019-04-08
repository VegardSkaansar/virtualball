package e.vegard.virtualball.Score;


import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e.vegard.virtualball.Database.Score;
import e.vegard.virtualball.Fragment.MainActivity;
import e.vegard.virtualball.Math.MathUtils;
import e.vegard.virtualball.R;
import e.vegard.virtualball.Sound.SoundUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThrowFragment extends Fragment {

private MainActivity mainActivity;
private Sensor accelerometer;
private Button btnThrow;

private Boolean cooldown = true;
private Boolean userThrow = false;

private SoundUtils sound;

private static DecimalFormat df2 = new DecimalFormat(".##");


// const
    private final String TAG = "ThrowFragment";

    // variable for textviews
    private TextView second;
    private TextView dist;
    private TextView viewCooldown;

    private ArrayList<Double> accArray;

    private SharedPreferences prefs;

    public ThrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        setRetainInstance(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_throw, container, false);

        // getting the activity for the fragment
        mainActivity = ((MainActivity)getActivity());

        // setting sensor
        mainActivity.mSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mainActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //setting the int to be 0
        accArray = new ArrayList<Double>();

        // setting the textviews
        second = v.findViewById(R.id.txt_seconds);
        dist = v.findViewById(R.id.txt_distance);
        viewCooldown = v.findViewById(R.id.txt_countdown);

        // setting button
        btnThrow = v.findViewById(R.id.btn_startThrow);

        // initializing my sound class
        sound = new SoundUtils(getContext());

        // SharedPreference get the value for the sensor
        prefs = mainActivity.getSharedPreferences(mainActivity.SLIDER, Context.MODE_PRIVATE);
        final int constraint = prefs.getInt(mainActivity.PROGRESS, 0);


        // adding event listener to the accelerometer
       final SensorEventListener eventListener = new SensorEventListener() {

            // Functionality: We used it for accelerometer, and gets the position when the sensor change
            //                position.
            // Reason: To get the x, y, z position on change to calculate acceleration
            // Extra:
            @Override
            public void onSensorChanged(SensorEvent event) {

                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                double acc = MathUtils.acceleration(x, y, z);

                Log.d(TAG, "acceleration: " + acc);

                // this two if statments do so we only record the first
                // throw, when the constraint user have given have been passed.
                // the code will take the 20 next acceleration and thats it.
                // even if you throw mulitple times in the 5 seconds it will only
                // register the first throw
                if(constraint < acc) {
                    userThrow = true;
                }

                if (userThrow && accArray.size() <= 20){
                    accArray.add(acc);
                    Log.d(TAG, "onSensorChanged: " + accArray.size());
                } else if (accArray.size() == 21) {
                    mainActivity.mSensorManager.unregisterListener(this, accelerometer);
                    double num = Collections.max(accArray);
                    setCountDown(MathUtils.secondsToPoint(num));
                }

            }
           // Functionality: no functionality
           // Reason: has to be implemented because of SensorEventListener
           // Extra:
           @Override
           public void onAccuracyChanged(Sensor sensor, int accuracy) {

           }
        };

        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setting the listner when button is pressed
                mainActivity.mSensorManager.registerListener(eventListener, accelerometer, mainActivity.mSensorManager.SENSOR_DELAY_NORMAL);
                // Setting the button to disabled, reason users cant spam the button
                btnThrow.setEnabled(false);
                // cooldown functionality is too set the timer of 5 sek
                userThrow = false;
                // sets ready
                viewCooldown.setText("READY");
            }
        });


        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        sound.releaseSound();
    }

    // Functionality: Sets the textview time and distance view with math
    // Reason: sets the textview text
    // Extra:
    public void setStats(double acc) {
        String time = df2.format(MathUtils.secondsToPoint(acc));
        String distance = df2.format(MathUtils.distanceTravelled(acc));

        dist.setText("Distance: " + distance);
        second.setText("Seconds: " + time);

    }

    public void checkForTopFive(double acc) {

        double time = MathUtils.secondsToPoint(acc);
        double distance = MathUtils.distanceTravelled(acc);
        double score = MathUtils.calculateScore(distance, time);

        int nr = mainActivity.database.getAmountOfRows();

        Log.d("check", "checkForTopFive: " + nr);

        if (nr < 5) {
            mainActivity.setPopup(-1, score, distance, time);
        } else {
            List<Score> mScore = mainActivity.database.getFromDBScore();
            double lowest = 1000000000;
            Score obj = null;
            for (Score one : mScore) {
                if(one.getScore() < lowest) {
                    lowest = one.getScore();
                    obj = one;
                }
            }

            if (lowest < score) {
                mainActivity.setPopup(obj.getId(),score, distance, time);
            }
        }

    }


    // Functionality: 5 seconds tick every sek, after 5 sek unregisterlistener
    // Reason: to get the highest onsensorchanged value in this timeinterval
    // Extra:
    public void setCountDown(Double count) {

        long i = Math.round(count * 1000);
        Log.d(TAG, "setCountDown: " + i);
        new CountDownTimer(i, 1000) {

            // Functionality: Every tick update countdown
            // Reason: Make a countdown, update a textview
            // Extra:
            @Override
            public void onTick(long millisUntilFinished) {
                viewCooldown.setText(""+ Math.round(Math.floor(millisUntilFinished / 1000)));
                Log.d(TAG, String.valueOf(millisUntilFinished / 1000));
            }

            // Functionality: When the countdown is finished unregister listener
            // Reason: Solve the problem with when user do the throw
            @Override
            public void onFinish() {
                if (accArray.size() != 0) {
                    Log.d(TAG, "onFinish: done");
                    //gets the highest value and send it to the setstats function
                    setStats(Collections.max(accArray));
                    checkForTopFive(Collections.max(accArray));

                    // resets the timeer to zero text so its not visble

                    // playing the ball sound
                    sound.playStartSound();

                    // clearing up after the array been used
                    accArray.clear();
                } else {
                    // the user has not got over the acceleration limit the user specified in settings
                    dist.setText("No distance");
                    second.setText("No Seconds");
                }
                viewCooldown.setText("");
                btnThrow.setEnabled(true);

            }
        }.start();
    }

}
