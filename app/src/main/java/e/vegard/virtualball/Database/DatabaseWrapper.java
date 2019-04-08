package e.vegard.virtualball.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import e.vegard.virtualball.ScoreModel;

import static android.content.ContentValues.TAG;

public class DatabaseWrapper {

    private Database mDB;
    private String dbName;

    public DatabaseWrapper(Context ctx, String dbName) {
        this.dbName = dbName;
        this.mDB = Room.databaseBuilder(ctx, Database.class, dbName).build();
    }

    public void insertToDBScore(ScoreModel mScore) {

        Score score = new Score(getAmountOfRows()+1,mScore.getName(), mScore.getDistance(), mScore.getSeconds(), mScore.getScore());

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

    public int getAmountOfRows() {
        int i = 0;

        try {
            i =new AsyncTask<Void, Void, Integer>() {

                @Override
                protected Integer doInBackground(Void... voids) {
                    return mDB.mydao().getNumberOfRows();
                }
            }.execute().get();
        }
        catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
        catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }

        return i;
    }

    public void DeleteAll() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                mDB.mydao().deleteAll();
                return null;
            }
        }.execute();
    }

    public void UpdateScore(ScoreModel mScore, int id) {

        Score score = new Score(id ,mScore.getName(), mScore.getDistance(), mScore.getSeconds(), mScore.getScore());

     new AsyncTask<Score, Void, Void>() {

         @Override
         protected Void doInBackground(Score... scores) {
             mDB.mydao().changeScore(scores[0]);
             return null;
         }
     }.execute(score);
    }

}
