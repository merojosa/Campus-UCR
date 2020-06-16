package cr.ac.ucr.ecci.cql.campus20;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MealUITest
{
    private CountDownLatch lock;

    @Before
    public void setup()
    {
        lock = new CountDownLatch(1);
    }

    @Rule
    public ActivityTestRule<MainUcrEats> activityRule
            = new ActivityTestRule<>(MainUcrEats.class);

    @Test
    public void testMealsUIName() throws InterruptedException
    {
        // Wait for Firebase read
        lock.await(5000, TimeUnit.MILLISECONDS);

        // Click Soda la U
        onView(withId(R.id.ucr_eats_rv)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // Check name
        onView(withId(R.id.meal_rest_name)).
                check(matches(withText("Soda la U")));
    }
}
