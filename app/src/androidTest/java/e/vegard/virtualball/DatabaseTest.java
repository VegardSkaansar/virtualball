package e.vegard.virtualball;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import e.vegard.virtualball.Database.Database;
import e.vegard.virtualball.Database.Score;
import e.vegard.virtualball.Database.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private dao myDao;
    private Database db;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class)
                .allowMainThreadQueries()
                .build();
        myDao = db.mydao();
    }

    @Test
    public void testInsertToDB() {
        myDao.addScore(new Score(1, "hans", 20, 2, 300));
        myDao.addScore(new Score(2, "Owen", 18, 1.8, 280));

        List<Score> list = myDao.getScores();
        assertEquals("Correct Length", 2, list.size());
        assertThat("Gets the same value", true, is(list.get(0).getName().equals("hans")));
        assertThat("Gets the same value", true, is(list.get(1).getName().equals("Owen")));
        myDao.deleteAll();

    }

    @Test
    public void testDeleteAll() {
        myDao.addScore(new Score(1, "hans", 20, 2, 300));
        myDao.addScore(new Score(2, "Owen", 18, 1.8, 280));

        myDao.deleteAll();

        assertEquals(0, myDao.getNumberOfRows());
    }

    @Test
    public void testChangeScore() {
        myDao.addScore(new Score(1, "hans", 20, 2, 300));
        myDao.addScore(new Score(2, "Owen", 18, 1.8, 280));

        List<Score> scores = myDao.getScores();

        assertEquals("hans", scores.get(0).getName());

        myDao.changeScore(new Score(1, "heidi", 10, 1, 100));

        scores = myDao.getScores();

        // this one is in spot 1 now because of orderby DESC after score
        assertEquals("heidi", scores.get(1).getName());
    }
}
