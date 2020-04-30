package cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories;

import android.app.Application;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos.RestaurantDao;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

public class RestaurantRepository
{
    private RestaurantDao restaurantDao;
    private List<Restaurant> restaurants;

    public RestaurantRepository(Application application) {
        UcrEatsDatabase db = UcrEatsDatabase.getDatabase(application);
        this.restaurantDao = db.restaurantDao();
        this.restaurants = restaurantDao.getAllRestaurants();
    }


    public void insert(Restaurant restaurant)
    {
        UcrEatsDatabase.databaseWriteExecutor.execute( () -> {
            this.restaurantDao.insertRestaurant(restaurant);
        });
    }
}
