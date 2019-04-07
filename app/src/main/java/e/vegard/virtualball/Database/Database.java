package e.vegard.virtualball.Database;

import android.arch.persistence.room.RoomDatabase;


@android.arch.persistence.room.Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract dao mydao();

}
