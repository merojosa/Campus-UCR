package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import android.os.Parcel;

import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import cr.ac.ucr.ecci.cql.campus20.MockParcel;

import static org.junit.Assert.*;

public class MealTest {

    @Test
    public void test()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL",
                Meal.BREAKFAST, 1500);

        // Parcelable mock taken from internet, see class link
        Parcel parcel = MockParcel.obtain();

        meal.writeToParcel(parcel, meal.describeContents());

        parcel.setDataPosition(0);

        Meal parcelMeal =  Meal.CREATOR.createFromParcel(parcel);

        assertEquals(meal.getName(), parcelMeal.getName());
    }

    @Test
    public void testEmptyName()
    {
        Meal meal = new Meal();

        assertEquals(meal.getName(), "");
    }

    @Test
    public void testName()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertEquals(meal.getName(), "Pollo con papas");
    }

    @Test
    public void testPhotoURL()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertEquals(meal.getPhoto(), "URL");
    }

    @Test
    public void testPrice()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertNotEquals(meal.getPrice(), 2000);
    }

    @Test
    public void testType()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertEquals(meal.getType(), Meal.BREAKFAST);
    }

    @Test
    public void testDefaultMax()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertEquals(meal.getMaxServings(), 0);
    }

    @Test
    public void testDefaultAvailable()
    {
        Meal meal = new Meal(0, "Pollo con papas", "URL", Meal.BREAKFAST, 1500);
        assertEquals(meal.getAvailableServings(), 0);
    }

}