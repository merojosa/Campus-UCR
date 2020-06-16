package cr.ac.ucr.ecci.cql.campus20;

import android.content.Intent;
import android.os.Parcel;

import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class MealInsturmentedTest
{
    @Test
    // Reference: https://medium.com/@dbottillo/android-junit-parcelable-model-test-37a2f2ae18b1
    public void testParcelableName()
    {
        //String name, String photo, int type, int price
        Meal meal = new Meal(0, "Mock", "mock photo", Meal.BREAKFAST, 1500);

        Parcel parcel = Parcel.obtain();
        meal.writeToParcel(parcel, meal.describeContents());

        parcel.setDataPosition(0);

        Meal parcelMeal = Meal.CREATOR.createFromParcel(parcel);

        assertEquals(meal.getName(), parcelMeal.getName());

    }
}
