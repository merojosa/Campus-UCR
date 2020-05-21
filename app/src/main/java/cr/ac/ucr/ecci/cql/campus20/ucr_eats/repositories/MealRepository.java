package cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.MealDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

public class MealRepository
{
    private MealDao mealDao;
    private LiveData<List<Meal>> meals;

    public MealRepository(Application application)
    {
        UcrEatsDatabase db = UcrEatsDatabase.getDatabase(application);
        this.mealDao = db.mealDao();
        this.meals = mealDao.getAllMeals();
    }

    public LiveData<List<Meal>> getMeals()
    {
        return meals;
    }

    public void deleteAll()
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.mealDao.deleteAll());
    }

    public void insert(Meal meal)
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.mealDao.insert(meal));
    }

    public LiveData<List<Meal>> getMealsByRestaurant(int value)
    {
        try {
            // Submit data gathering
            Future<LiveData<List<Meal>>> futureData = UcrEatsDatabase.databaseWriteExecutor.submit( () ->
                 this.mealDao.getMealsByRestaurant(value));

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
