package dmytro.app.retrofitstudy;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomData extends RoomDatabase {
    private static RoomData dataBase;
    private static String DATABASE_NAME = "numbers_db";
    public synchronized static RoomData getInstance(Context context) {
        if(dataBase == null){
            dataBase = Room.databaseBuilder(context.getApplicationContext(),
                    RoomData.class, DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return dataBase;
    }
    public abstract MyDao myDao();
}
