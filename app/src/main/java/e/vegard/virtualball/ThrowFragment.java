package e.vegard.virtualball;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


// const
    private final String TAG = "ThrowFragment";

    // variable for textviews
    private TextView second;
    private TextView dist;

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

        // adding event listener to the accelerometer
        SensorEventListener eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                double acc = MathUtils.acceleration(x, y, z);


                //double time =

                Log.d(TAG, "acceleration: " + acc);
                if(acc > 9.81) {

                    mainActivity.mSensorManager.unregisterListener(this, accelerometer);
                    setStats(acc);

                        // Toast.makeText(getActivity(), "time: " + df2.format(time) + " distance: " + distance, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mainActivity.mSensorManager.registerListener(eventListener, accelerometer, mainActivity.mSensorManager.SENSOR_DELAY_NORMAL);


        return v;
    }

    public void setStats(double acc) {
        accBall = acc;
        String time = MathUtils.secondsToPoint(accBall);
        String distance = MathUtils.distanceTravelled(accBall);

        dist.setText(dist.getText().toString() + distance);
        second.setText(second.getText().toString() + time);

    }

}
