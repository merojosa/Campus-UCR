package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.MealsAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

import static org.mockito.Mockito.when;

public class MealsPresenterTest
{
    private MealsAdapter.MealsViewHolder view;
    private MealsPresenter presenter;

    @Before
    public void setup()
    {
        view = Mockito.mock(MealsAdapter.MealsViewHolder.class);
        presenter = new MealsPresenter(null);
    }

    @Test
    public void testOnNullListName() throws Exception
    {
        presenter.onBindMealItemAtPosition(0, null);
        Mockito.verify(view, Mockito.times(0)).setName("test");
    }

    @Test
    public void testListName() throws Exception
    {
        List<Meal> meals = new ArrayList<Meal>();
        Meal meal = Mockito.mock(Meal.class);
        meals.add(meal);
        Mockito.when(meal.getName()).thenReturn("Test meal");

        this.presenter.setMeals(meals);

        presenter.onBindMealItemAtPosition(0, this.view);
        Mockito.verify(view, Mockito.times(1)).setName("Test meal");
    }

}
