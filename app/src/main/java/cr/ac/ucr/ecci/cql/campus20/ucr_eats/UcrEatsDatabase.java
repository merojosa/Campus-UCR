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
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.MealDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RatingDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Rating;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

@Database(entities = {Restaurant.class, Meal.class, Rating.class}, version = 5, exportSchema = false)
public abstract class UcrEatsDatabase extends RoomDatabase
{
    public abstract RestaurantDao restaurantDao();
    public abstract MealDao mealDao();
    public abstract RatingDao ratingDao();

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
        RestaurantDao rest = db.restaurantDao();
        MealDao mealDao = db.mealDao();
        RatingDao ratingDao = db.ratingDao();

        ratingDao.deleteAll();
        mealDao.deleteAll();
        rest.deleteAll();

        Restaurant restaurant1 = new Restaurant(R.drawable.la_u, "Soda La U", "la_u", 9.934497, -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun", (short)8, (short)21);
        rest.insert(restaurant1);

        Restaurant restaurant2 = new Restaurant(R.drawable.plaza_chou, "Plaza Chou", "plaza_chou", 9.934748, -84.051578,
                "Mon-Wed-Thu-Fri", (short)10, (short)20);
        rest.insert(restaurant2);

        Meal[] meals = {
                new Meal(R.drawable.la_u, "Desayuno 1", "la_u", Meal.BREAKFAST, 1000),
                new Meal(R.drawable.la_u, "Almuerzo 1", "la_u", Meal.LUNCH, 1500),
                new Meal(R.drawable.la_u, "Cena 1", "la_u", Meal.DINNER, 1500),
                new Meal(R.drawable.plaza_chou, "Económico 1", "plaza_chou", Meal.LUNCH, 1500)
        };

        Rating[] rating = {
                new Rating(R.drawable.la_u, 5),
                new Rating(R.drawable.la_u, 2),
                new Rating(R.drawable.la_u, 3),
                new Rating(R.drawable.la_u, 4),
                new Rating(R.drawable.plaza_chou, 4),
                new Rating(R.drawable.plaza_chou, 5),
                new Rating(R.drawable.plaza_chou, 3),
                new Rating(R.drawable.plaza_chou, 4),
        };

        for(Meal meal : meals)
            mealDao.insert(meal);

        for(Rating points : rating)
            ratingDao.insert(points);
    }
}
