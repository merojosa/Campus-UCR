package cr.ac.ucr.ecci.cql.campus20.ucr_eats.viewmodels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.MealRepository;

public class MealViewModel extends AndroidViewModel
{
    private MealRepository repository;
    private LiveData<List<Meal>> meals;


    public MealViewModel(@NonNull Application application)
    {
        super(application);
        this.repository = new MealRepository(application);
        this.meals = repository.getMeals();
    }

    public LiveData<List<Meal>> getAllMeals()
    {
        return meals;
    }

    public void insert(Meal meal) { this.repository.insert(meal); }

    public LiveData<List<Meal>> getMealsByRestId(int rest_id)
    {
        return repository.getMealsByRestaurant(rest_id);
    }
}
