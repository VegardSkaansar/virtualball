package e.vegard.virtualball.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import e.vegard.virtualball.R;

public class Dialog extends AppCompatDialogFragment {

    private EditText editName;
    private DialogListner listner;

    private double score;
    private double distance;
    private double seconds;
    private int id;

    private Bundle bundle;

    private String name;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        bundle = getArguments();
        score = bundle.getDouble("score");
        distance = bundle.getDouble("distance");
        seconds = bundle.getDouble("time");
        id = bundle.getInt("id");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.popup_fill_name, null);

        name = "";
        editName = v.findViewById(R.id.popupName);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = editName.getText().toString();
                Log.d("ONTEXTchna", "onTextChanged: " + name);
            }
        });

        builder.setView(v)
                .setTitle("TOP 5")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listner.applyTexts(id, name, score, distance, seconds);
                    }
                });


        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (DialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement DialogListner");
        }
    }

    public interface DialogListner {
        void applyTexts(int id, String name, double score, double distance, double time);
    }
}
