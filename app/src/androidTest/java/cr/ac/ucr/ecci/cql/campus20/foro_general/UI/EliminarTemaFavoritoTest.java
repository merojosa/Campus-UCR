package cr.ac.ucr.ecci.cql.campus20.foro_general.UI;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearPreguntaForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.interest_points_test.RecyclerViewMatcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static cr.ac.ucr.ecci.cql.campus20.interest_points_test.InterestPointsTests.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

public class EliminarTemaFavoritoTest {

    public static final String TITULO = "Temas Favoritos";

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
     * Método que verifica que el hacer click en el botón del corazón de un tema, tira el mensaje para poder eliminar
     * el mismo, pero antes de eliminarlo, se cancela dicha eliminación
     */
    @Test
    public void testEliminarTemaFavorito(){

        // Chequea si existen temas como favoritos añadidos
        if (getRVcount() > 0){

            onView(new RecyclerViewMatcher(R.id.listaTemasFavoritos)
                    .atPositionOnView(0, R.id.botonFollow))
                    .perform(click());

            onView(withText("Cancelar")).inRoot(isDialog()) // <---
                    .check(matches(isDisplayed()))
                    .perform(click());
        }
    }

    /**
     * Método que se encarga de contar cuántos items están actualmente dentro del RecyclerView
     * @return la cantidad de items que están actualmente renderizados en el view
     */
    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) mActivity.getActivity().findViewById(R.id.listaTemasFavoritos);
        return recyclerView.getAdapter().getItemCount();
    }
}
