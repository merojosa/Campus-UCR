package cr.ac.ucr.ecci.cql.campus20.foro_general.UI;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearRespuestaForoGeneral;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CrearRespuestaForoGeneralTest {


    public static final String TITULO = "Nuevo comentario";
    private static final String TEXTO_PRUEBA = "Respuesta 1";
    private static final String TEXTO_BTN_CREAR_RESPUESTA = "CREAR RESPUESTA";

    @Rule
    public ActivityTestRule<CrearRespuestaForoGeneral> mActivityRule = new ActivityTestRule<CrearRespuestaForoGeneral>(CrearRespuestaForoGeneral.class);


    /**
     * Verifica el titulo de agregar comentario en la pantalla
     */
    @Test
    public void titulo(){
        onView(withId(R.id.tituloCrearRespuesta)).check(matches(withText(TITULO)));
    }


    /**
     * Verifica que el texto que sería la respuesta o comentario se rellena de manera correcta
     */
    @Test
    public void rellenarTexto(){
        onView(withId(R.id.textoCrearRespuesta)).perform(typeText(TEXTO_PRUEBA), closeSoftKeyboard());
        onView(withId(R.id.textoCrearRespuesta)).equals(withText(TEXTO_PRUEBA));
    }

    /**
     * Verifica el contenido del boton
     */
    @Test
    public void btnCrearRespuesta(){
        onView(withId(R.id.btnCrearRespuesta)).check(matches(withText(TEXTO_BTN_CREAR_RESPUESTA)));
    }
}