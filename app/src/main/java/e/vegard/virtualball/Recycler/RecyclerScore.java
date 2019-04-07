package e.vegard.virtualball.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import e.vegard.virtualball.Score.ScoreModel;
import e.vegard.virtualball.R;

public class RecyclerScore extends RecyclerView.Adapter<RecyclerScore.ScoreHolder> {

    private List<ScoreModel> mScoreBoard;

    public static class ScoreHolder extends RecyclerView.ViewHolder {

        private View scoreView;

        public ScoreHolder(View v) {
            super(v);
            scoreView = v;

        }
    }

    public RecyclerScore(List<ScoreModel> scores) {
        mScoreBoard = scores;
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_scoreboard, parent, false);
        ScoreHolder holder = new ScoreHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int i) {
        final ScoreModel scoreBoard = mScoreBoard.get(i);
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreName)).setText(scoreBoard.getName());
        ((TextView)holder.scoreView.findViewById(R.id.txt_Score)).setText("" + scoreBoard.getScore());
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreSecond)).setText("" + scoreBoard.getSeconds());
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreDistance)).setText("" + scoreBoard.getDistance());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}