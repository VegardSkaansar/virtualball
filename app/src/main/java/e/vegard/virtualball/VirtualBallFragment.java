package e.vegard.virtualball;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class VirtualBallFragment extends Fragment {

    private Button btnSettings;
    private Button btnThrow;
    private MainActivity mainActivity;

    public VirtualBallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_virtual_ball, container, false);

        // setting buttons views
        btnSettings = v.findViewById(R.id.btn_settings);
        btnThrow = v.findViewById(R.id.btn_throw);

        // here we will initilaize the variable and get our mainactivity
        // so we can store values from fragments to the activity
        mainActivity = ((MainActivity) getActivity());

        // setting onclick on the buttons
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new SettingsFragment(), null)
                        .commit();
            }
        });

        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ThrowFragment(), null)
                        .commit();
            }
        });

        return v;
    }

}
