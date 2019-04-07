package e.vegard.virtualball.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.lang.reflect.Member;
import java.util.List;

@Dao
public interface dao {

    @Insert
    public void addScore(Score score);

    @Query("SELECT * FROM scores ORDER BY score")
    public List<Score> getScores();

    @Query("SELECT COUNT(*) FROM scores")
    public int getNumberOfRows();


}
