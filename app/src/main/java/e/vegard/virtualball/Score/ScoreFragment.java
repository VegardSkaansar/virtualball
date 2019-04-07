package e.vegard.virtualball.Score;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.vegard.virtualball.Database.Score;
import e.vegard.virtualball.MainActivity;
import e.vegard.virtualball.R;
import e.vegard.virtualball.Recycler.RecyclerScore;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    private RecyclerView mRecycleView;
    private MainActivity mainActivity;

    public ScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_score, container, false);

        mainActivity = ((MainActivity) getActivity());

        mRecycleView = v.findViewById(R.id.recyclerScore);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mainActivity));

        List<Score> scores = mainActivity.database.mydao().getScores();
        List<ScoreModel> model = new ArrayList<>();

        for(Score score : scores) {
            String name = score.getName();
            double seconds = score.getSeconds();
            double distance = score.getDistance();
            double sc = score.getScore();

            ScoreModel input = new ScoreModel(name, sc, distance, seconds);
            model.add(input);
        }

        mRecycleView.setAdapter(new RecyclerScore(model));


        return v;
    }

}
