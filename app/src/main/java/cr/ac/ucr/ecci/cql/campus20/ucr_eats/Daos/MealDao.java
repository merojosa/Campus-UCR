package cr.ac.ucr.ecci.cql.campus20.ucr_eats.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

@Dao
public interface MealDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal meal);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM Meal m " +
            "WHERE m.restaurant_id = :restaurant_id")
    LiveData<List<Meal>> getMealsByRestaurant(int restaurant_id);

    @Query("DELETE FROM Meal")
    void deleteAll();

    @Query("SELECT * FROM Meal")
    LiveData<List<Meal>> getAllMeals();
}
