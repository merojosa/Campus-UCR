package cr.ac.ucr.ecci.cql.campus20.ucr_eats.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.RestaurantRepository;

public class RestaurantViewModel extends AndroidViewModel
{
    private RestaurantRepository repository;
    private LiveData<List<Restaurant>> restaurants;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        this.repository = new RestaurantRepository(application);
        this.restaurants = repository.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants()
    {
        return restaurants;
    }

    public void insert(Restaurant restaurant) { this.repository.insert(restaurant); }


}
