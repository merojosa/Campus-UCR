package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

@Database(entities = {Restaurant.class}, version = 3, exportSchema = false)
public abstract class UcrEatsDatabase extends RoomDatabase
{
    public abstract RestaurantDao restaurantDao();

    private static UcrEatsDatabase INSTANCE = null;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UcrEatsDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (UcrEatsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UcrEatsDatabase.class, "UCREats-database")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback()
    {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
            UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                    UcrEatsDatabase.populateDb(INSTANCE));
        }
    };

    private static void populateDb(UcrEatsDatabase db)
    {
        RestaurantDao dao = db.restaurantDao();

        dao.deleteAll();

        Restaurant restaurant1 = new Restaurant(R.drawable.la_u, "Soda La U", "la_u", 0.0,0.0,
                "Mo", (short)0, (short)1000);
        dao.insert(restaurant1);

        Restaurant restaurant2 = new Restaurant(R.drawable.plaza_chou, "Plaza Chou", "plaza_chou", 0.0,0.0,
                "Mo", (short)0, (short)1000);
        dao.insert(restaurant2);
    }
}
