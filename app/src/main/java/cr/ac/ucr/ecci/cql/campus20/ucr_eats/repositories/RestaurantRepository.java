package cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

public class RestaurantRepository
{
    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> restaurants;

    public RestaurantRepository(Application application) {
        UcrEatsDatabase db = UcrEatsDatabase.getDatabase(application);
        this.restaurantDao = db.restaurantDao();
        this.restaurants = this.restaurantDao.getAllRestaurants();
    }

    public void deleteAll()
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.restaurantDao.deleteAll());
    }

    public void insert(Restaurant restaurant)
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                this.restaurantDao.insert(restaurant));
    }

    public LiveData<List<Restaurant>> getAllRestaurants()
    {
        return this.restaurants;
    }

    public List<Restaurant> searchRestaurantByName(String name)
    {
        List<Restaurant> restaurants = null;

        Future<List<Restaurant>> data = UcrEatsDatabase.databaseWriteExecutor.submit( () ->
                this.restaurantDao.searchRestaurantByName(name));

        try {
            restaurants = data.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    public Restaurant searchRestaurantById(int id)
    {
        try {
            return UcrEatsDatabase.databaseWriteExecutor.submit( () ->
                    this.restaurantDao.searchRestaurantById(id)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
