package e.vegard.virtualball.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import e.vegard.virtualball.Fragment.ScoreModel;
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

    @Override
    public ScoreHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_scoreboard, parent, false);
        ScoreHolder holder = new ScoreHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScoreHolder holder, int i) {
        DecimalFormat df2 = new DecimalFormat(".##");
        final ScoreModel scoreBoard = mScoreBoard.get(i);
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreName)).setText("Name: " + scoreBoard.getName());
        ((TextView)holder.scoreView.findViewById(R.id.txt_Score)).setText("" + df2.format(scoreBoard.getScore()));
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreSecond)).setText("Seconds: " + df2.format(scoreBoard.getSeconds()));
        ((TextView)holder.scoreView.findViewById(R.id.txt_ScoreDistance)).setText("Distance: " + df2.format(scoreBoard.getDistance()) + " meter");
    }

    @Override
    public int getItemCount() {
        return mScoreBoard.size();
    }
}