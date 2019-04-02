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


/**
 * A simple {@link Fragment} subclass.
 */
public class ThrowFragment extends Fragment {

private MainActivity mainActivity;
private Sensor accelerometer;


// const
    private final String TAG = "ThrowFragment";

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
