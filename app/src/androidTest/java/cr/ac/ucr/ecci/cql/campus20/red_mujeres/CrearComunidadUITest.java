package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.Context;

import androidx.test.espresso.internal.inject.InstrumentationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import cr.ac.ucr.ecci.cql.campus20.R;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CrearComunidadUITest {

    //Textea el contexto de la aplicación
    @Test
    public void testContext() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cr.ac.ucr.ecci.cql.campus20", appContext.getPackageName());
    }
}
