package cr.ac.ucr.ecci.cql.campus20.foro_general.UI;

import android.app.ActionBar;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerMisPreguntas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class IrMisPreguntasForoGeneralTest {

    public static final String TITULO = "Temas Favoritos";

    private CountDownLatch lock;

    @Before
    public void setup()
    {
        lock = new CountDownLatch(1);
    }

    @Rule
    public IntentsTestRule<MainForoGeneral> mActivity = new IntentsTestRule<>(MainForoGeneral.class);


    /**
     * Verifica el titulo de la pantalla principal del foro
     */
    @Test
    public void titulo(){
        onView(withId(R.id.tituloTemasSugeridos)).check(matches(withText(TITULO)));
    }

    /**
     * Método que abre el menú desplegable del foro y selecciona la opción "Mis Preguntas" para ir a
     * la actividad de visualizar las preguntas planteadas por el usuario
     */
    @Test
    public void testIrActividadMisPreguntas() throws InterruptedException {

        lock.await(2000, TimeUnit.MILLISECONDS);

        onView(withId(R.id.action_bar)).check(matches(hasDescendant(withText("Campus Virtual UCR"))));

        // Click al ícono para desplegar el menú de navegación dentro del foro, usando como ruta padre el Action Bar
        onView(isAssignableFrom(AppCompatImageButton.class)).check(matches(withParent(withId(R.id.action_bar)))).perform(click());

        // Click a la opción "Mis preguntas"
        onView(withText("Mis preguntas")).perform(click());

        intended(hasComponent(ForoGeneralVerMisPreguntas.class.getName()));
    }
}
