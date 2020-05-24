package cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Rating;
@Dao
public interface RatingDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Rating point);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Rating point);

    @Delete
    void delete(Rating point);

    @Query("SELECT AVG(m.point) FROM Rating m " +
            "WHERE m.restaurant_id = :restaurant_id")
    Double getRatingByRestaurant(int restaurant_id);

    @Query("DELETE FROM Rating")
    void deleteAll();

    @Query("SELECT * FROM Rating")
    LiveData<List<Rating>> getAllMeals();

}
