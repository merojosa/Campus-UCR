package cr.ac.ucr.ecci.cql.campus20;

import android.content.Intent;


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
import static androidx.test.espresso.action.ViewActions.click;
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
    public void testMealsUI() throws InterruptedException
    {
        lock.await(5000, TimeUnit.MILLISECONDS);
        onView(withText("Soda la U")).perform(click());
    }
}
