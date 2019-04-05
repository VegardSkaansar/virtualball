package e.vegard.virtualball;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private TextView showProgress;
    private SeekBar seekbar;
    private MainActivity mainActivity;
    private int value;
    private SharedPreferences prefs;

    //const
    public final String SLIDER = "SLIDERVALUE";
    public final String PROGRESS = "PROGRESSVALUE";


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        // setting value to 10 as a default value
        value = 10;

        // setting the mainactivity
        mainActivity = ((MainActivity) getActivity());

        showProgress = v.findViewById(R.id.txt_progress);
        seekbar = v.findViewById(R.id.seekbar_min_value);

        // Initializing sharedprefs
        prefs = mainActivity.getSharedPreferences(SLIDER, Context.MODE_PRIVATE);

        // prefs set the value
        seekbar.setProgress(prefs.getInt(PROGRESS, 0));
        showProgress.setText("" + prefs.getInt(PROGRESS, 0));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showProgress.setText("" + progress);
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt(PROGRESS, value);
                edit.apply();
            }
        });

        return v;
    }

}
