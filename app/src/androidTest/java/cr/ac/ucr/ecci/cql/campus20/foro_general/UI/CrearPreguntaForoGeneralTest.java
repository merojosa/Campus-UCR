package cr.ac.ucr.ecci.cql.campus20.foro_general.UI;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearPreguntaForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.FavoritoDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.PreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RankPreguntaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.RespuestaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Daos.TemaDao;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralDatabase;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerPreguntas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Tema;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onData;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class CrearPreguntaForoGeneralTest {

    public static final String TITULO_DROPDOWN = "Tema";
    private static final String TEXTO_PREGUNTA_PRUEBA = "Pregunta de prueba";
    private static final String TEXTO_BTN_CREAR_PREGUNTAS = "CREAR PREGUNTA";

    private static Tema temaUno = new Tema(1, "General", "Lo más nuevo", 10, R.drawable.foro1);
    private static Tema temaDos = new Tema(2, "Escuelas", "Información sobre distintas escuelas", 3, R.drawable.foro_escuelas);
    private static Tema temaTres = new Tema(3, "Becas", "Desde como aplicar hasta como gastar", 5, R.drawable.foro_becas);
    private static Tema temaCuatro = new Tema(4, "Profesores", "Información sobre distintos profesores", 6, R.drawable.foro_profesores);
    private static Tema temaCinco = new Tema(5, "Residencias", "Más barato que alquilar ...", 15, R.drawable.foro_residencias);
    private static Tema temaSeis = new Tema(6, "Buses", "Todo sobre el interno, algo sobre los externos", 11, R.drawable.foro_buses);
    private static Tema temaSiete = new Tema(7, "Mejores calificadas", "Las preguntas más valoradas por la comunidad", 6, R.drawable.foro_calificado);

    @Rule
    public ActivityTestRule<CrearPreguntaForoGeneral> mActivityRule = new ActivityTestRule<CrearPreguntaForoGeneral>(CrearPreguntaForoGeneral.class);


    /**
     * Verifica el titulo de Tema en la pantalla
     */
    @Test
    public void tituloDropdown(){
        onView(withId(R.id.tituloCrearPregunta)).check(matches(withText(TITULO_DROPDOWN)));
    }

    /**
     * Método que testea que el dropdown tiene valores de tipo Tema
     * Y verifica que sean los temas que se esperan obtener de la base de datos
     */
    @Test
    public void  valoresDropdown(){
        onView(withId(R.id.listaTemasCrearPregunta)).perform(click());
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(0).equals(temaUno);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(1).equals(temaDos);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(2).equals(temaTres);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(3).equals(temaCuatro);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(4).equals(temaCinco);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(5).equals(temaSeis);
        onData(allOf(is(instanceOf(Tema.class)))).atPosition(6).equals(temaSiete);
    }

    /**
     * Verifica que el texto que sería la pregunta se rellena de manera correcta
     */
    @Test
    public void rellenarTexto(){
        onView(withId(R.id.textoCrearPregunta)).perform(typeText(TEXTO_PREGUNTA_PRUEBA), closeSoftKeyboard());
        onView(withId(R.id.textoCrearPregunta)).equals(withText(TEXTO_PREGUNTA_PRUEBA));
    }

    /**
     * Verifica el contenido del boton crear pregunta
     */
    @Test
    public void btnCrearPreguntas(){
        onView(withId(R.id.btnCrearPregunta)).check(matches(withText(TEXTO_BTN_CREAR_PREGUNTAS)));
    }
}
