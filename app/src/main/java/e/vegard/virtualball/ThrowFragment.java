package e.vegard.virtualball;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThrowFragment extends Fragment {

private MainActivity mainActivity;
private Sensor accelerometer;
private double accBall;


// const
    private final String TAG = "ThrowFragment";
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public ThrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_throw, container, false);

        // getting the activity for the fragment
        mainActivity = ((MainActivity)getActivity());

        mainActivity.mSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);

        // setting sensor
        accelerometer = mainActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //setting the int to be 0
        accBall = 0;

        // adding event listener to the accelerometer
        SensorEventListener eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                double acc = mainActivity.acceleration(x, y, z);


                //double time =


                if(acc > 9.81) {
                    Log.d(TAG, "acceleration: " + acc);
                    accBall = acc;
                    mainActivity.mSensorManager.unregisterListener(this, accelerometer);
                    double time = accBall / mainActivity.EARTHGRAVITY;
                    // formula used V^2 = v0^2 + 2a(r-r0)
                    double distance = (accBall*accBall) / (2*mainActivity.EARTHGRAVITY);
                    Toast.makeText(getActivity(), "time: " + df2.format(time) + " distance: " + distance, Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mainActivity.mSensorManager.registerListener(eventListener, accelerometer, mainActivity.mSensorManager.SENSOR_DELAY_NORMAL);


        return v;
    }

}
