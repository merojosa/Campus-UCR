package cr.ac.ucr.ecci.cql.campus20.foro_general.UI;

import android.view.Gravity;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearPreguntaForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerMisPreguntas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.DrawerActions.open;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityForoGeneralTest {

    @Rule
    public IntentsTestRule<MainForoGeneral> mActivity = new IntentsTestRule<MainForoGeneral>(MainForoGeneral.class);

    /**
     * Método que verifica que el hacer click en el botón de agregar pregunta lo redirige a la actividad de
     * CrearPregunta del foro
     */
    @Test
    public void testBotonAgregarPreguntas(){
        onView(withId(R.id.buttonAgregarPreguntas)).perform(click());
        intended(hasComponent(CrearPreguntaForoGeneral.class.getName()));
    }
}
