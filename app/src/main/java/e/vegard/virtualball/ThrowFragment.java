package e.vegard.virtualball;


import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThrowFragment extends Fragment {

private MainActivity mainActivity;
private Sensor accelerometer = mainActivity.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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


        return v;
    }

}
