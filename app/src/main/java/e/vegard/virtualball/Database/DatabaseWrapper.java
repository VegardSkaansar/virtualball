package e.vegard.virtualball.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import e.vegard.virtualball.Score.ScoreModel;

import static android.content.ContentValues.TAG;

public class DatabaseWrapper {

    private Database mDB;
    private String dbName;

    public DatabaseWrapper(Context ctx, String dbName) {
        this.dbName = dbName;
        this.mDB = Room.databaseBuilder(ctx, Database.class, dbName).build();
    }

    public void insertToDBScore(ScoreModel mScore) {

        Score score = new Score(mScore.getName(), mScore.getDistance(), mScore.getSeconds(), mScore.getScore());

        new AsyncTask<Score, Void, Void>() {

            @Override
            protected Void doInBackground(Score... scores) {
                mDB.mydao().addScore(scores[0]);
                return null;
            }
        }.execute(score);

    }

    public List<Score> getFromDBScore() {

        List<Score> tmp = null;
        try {

             tmp = new AsyncTask<Void, Void, List<Score>>() {

                @Override
                protected List<Score> doInBackground(Void... voids) {
                    return mDB.mydao().getScores();
                }
            }.execute().get();
        }
        catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
        catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
        return tmp;
    }

}
