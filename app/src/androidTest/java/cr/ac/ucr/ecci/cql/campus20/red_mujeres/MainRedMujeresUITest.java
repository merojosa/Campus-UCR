package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import cr.ac.ucr.ecci.cql.campus20.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainRedMujeresUITest {
    @Rule
    public IntentsTestRule<MainRedMujeres> intentsTestRule = new IntentsTestRule<>(MainRedMujeres.class, true, false);
    
    @Test
    public void testBotonCompartir(){
        Intent intent = new Intent();
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.shareTrip)).perform(click());
    }

    @Test
    public void testBotonEsconder(){
        Intent intent = new Intent();
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.floatingActionButton)).perform(click());
    }

    @Test
    public void mapClick(){
        Intent intent = new Intent();
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.mapView)).perform(click());
    }

}