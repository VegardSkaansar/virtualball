package e.vegard.virtualball;


import android.content.Context;
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

import e.vegard.virtualball.Math.MathUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThrowFragment extends Fragment {

private MainActivity mainActivity;
private Sensor accelerometer;
private double accBall;
private Button btnThrow;

private Boolean cooldown = true;


// const
    private final String TAG = "ThrowFragment";

    // variable for textviews
    private TextView second;
    private TextView dist;
    private TextView viewCooldown;

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
        accBall = 0;

        // setting the textviews
        second = v.findViewById(R.id.txt_seconds);
        dist = v.findViewById(R.id.txt_distance);
        viewCooldown = v.findViewById(R.id.txt_countdown);

        // setting button
        btnThrow = v.findViewById(R.id.btn_startThrow);


        // adding event listener to the accelerometer
       final SensorEventListener eventListener = new SensorEventListener() {

            // Functionality: We used it for accelerometer, and gets the position when the sensor change
            //                position.
            // Reason: To get the x, y, z position on change to calculate acceleration
            // Extra:
            @Override
            public void onSensorChanged(SensorEvent event) {

                if(cooldown) {
                    setCountDown(this);
                    cooldown = false;
                }
                
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                double acc = MathUtils.acceleration(x, y, z);

                Log.d(TAG, "acceleration: " + acc);
                if(acc > accBall) {
                    accBall = acc;
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
                cooldown = true;
            }
        });


        return v;
    }

    // Functionality: Sets the textview time and distance view with math
    // Reason: sets the textview text
    // Extra:
    public void setStats(double acc) {
        String time = MathUtils.secondsToPoint(acc);
        String distance = MathUtils.distanceTravelled(acc);

        dist.setText("Distance: " + distance);
        second.setText("Seconds: " + time);

    }

    // Functionality: 5 seconds tick every sek, after 5 sek unregisterlistener
    // Reason: to get the highest onsensorchanged value in this timeinterval
    // Extra:
    public void setCountDown(final SensorEventListener listener) {
        new CountDownTimer(5000, 1000) {

            // Functionality: Every tick update countdown
            // Reason: Make a countdown, update a textview
            // Extra:
            @Override
            public void onTick(long millisUntilFinished) {
                viewCooldown.setText(String.valueOf(Math.round(millisUntilFinished * 0.001f)));
            }

            // Functionality: When the countdown is finished unregister listener
            // Reason: Solve the problem with when user do the throw
            @Override
            public void onFinish() {
                mainActivity.mSensorManager.unregisterListener(listener, accelerometer);
                Log.d(TAG, "onFinish: done");
                btnThrow.setEnabled(true);
                setStats(accBall);
                viewCooldown.setText("");

            }
        }.start();
    }

}
