package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import org.junit.Test;
import cr.ac.ucr.ecci.cql.campus20.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

class MainRedMujeresUITest {

    @Test
    public void testBotonPanico(){
        onView(withId(R.id.sos)).perform(click());
        onView(withId(R.id.sos)).check(matches(withText("¡EMERGENCIA!")));

        // intended(hasComponent(CrearPreguntaForoGeneral.class.getName()));
    }

}