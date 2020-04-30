package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

@Database(entities = {Restaurant.class}, version = 1, exportSchema = false)
public abstract class UcrEatsDatabase extends RoomDatabase
{
    public abstract RestaurantDao restaurantDao();

    private static UcrEatsDatabase INSTANCE = null;

    // The following commented code was intended to be used avoiding Asynctasks,
    // but the minimum API does not support Lambda expressions, hence, this code is not compatible
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UcrEatsDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (UcrEatsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UcrEatsDatabase.class, "UCREats-database").build();
                }
            }
        }

        return INSTANCE;
    }

}
