package cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RatingDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Rating;

public class RatingRepository
{
    private RatingDao ratingDao;
    private LiveData<List<Rating>> meals;

    public RatingRepository(Application application)
    {
        UcrEatsDatabase db = UcrEatsDatabase.getDatabase(application);
        this.ratingDao = db.ratingDao();
    }

    public LiveData<List<Rating>> getMeals()
    {
        return meals;
    }

    public void deleteAll()
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.ratingDao.deleteAll());
    }

    public void insert(Rating meal)
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.ratingDao.insert(meal));
    }

    public Double getRatingByRestaurant(int value)
    {
        try {
            // Submit data gathering
            Future<Double> futureData = UcrEatsDatabase.databaseWriteExecutor.submit( () ->
                 this.ratingDao.getRatingByRestaurant(value));

            // Wait until thread finishes to return the data
            return futureData.get();
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
