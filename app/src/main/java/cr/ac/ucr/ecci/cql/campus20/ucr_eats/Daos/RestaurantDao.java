package cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;

@Dao
public interface RestaurantDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("SELECT * FROM Restaurant WHERE name LIKE :name")
    List<Restaurant> searchRestaurantByName(String name);

    @Query("SELECT * FROM Restaurant")
    List<Restaurant> getAllRestaurants();

    @Query("DELETE FROM Restaurant")
    void deleteAll();
}
