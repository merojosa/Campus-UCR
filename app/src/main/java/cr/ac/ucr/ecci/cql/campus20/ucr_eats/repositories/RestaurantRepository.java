package cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories;

import android.app.Application;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

public class RestaurantRepository
{
    private RestaurantDao restaurantDao;

    public RestaurantRepository(Application application) {
        UcrEatsDatabase db = UcrEatsDatabase.getDatabase(application);
        this.restaurantDao = db.restaurantDao();
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

    public List<Restaurant> getAllRestaurants()
    {
        AtomicReference<List<Restaurant>> restaurants = new AtomicReference<>();
        UcrEatsDatabase.databaseWriteExecutor.execute( () ->
                restaurants.set(this.restaurantDao.getAllRestaurants()));

        return restaurants.get();
    }
}
