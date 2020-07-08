package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import androidx.test.espresso.action.ViewActions;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import org.junit.rules.ExternalResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static org.hamcrest.Matchers.not;


import java.lang.reflect.Array;
import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.R;


@RunWith(AndroidJUnit4.class)
public class MisComunidadesUITest
{
    ArrayList<Comunidad> comunidadList = new ArrayList<>();
    ArrayList<String> misComunidades = new ArrayList<>();

    public static final String ACTIVITY_TITLE = "Mis Comunidades";

    @Rule
    public ActivityScenarioRule<MisComunidades> mActivityRule = new ActivityScenarioRule<>(MisComunidades.class);
    @Rule
    public IntentsTestRule<MisComunidades> intentsTestRule = new IntentsTestRule<>(MisComunidades.class, true, false);


    @Test
    public void test_Title_No_Communities()     //Test para verificar que el título de la actividad es el correcto cuando no hay comunidades asociadas al usuario actual
    {
        Intent intent = new Intent();

        intent.putStringArrayListExtra("misComunidades", new ArrayList<String>());         //Envío de las comunidades a las que pertenece el usuario actual
        intent.putStringArrayListExtra("comunidadesTotales", new ArrayList<String>());     //Envío de todos las comunidades
        intent.putExtra("userID", "currentUser");                                //Envío del ID del usuario actual
        intent.putExtra("userName", "nombre");

        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.text_Activity_Title)).check(matches(withText(ACTIVITY_TITLE)));
    }

    @Test
    public void test_No_Member_Communities_Msg()     //Test para verificar que cuando no se pertenece a ninguna comunidad, aparezca un mensaje indicándolo
    {
        onView(withId(R.id.text_Not_Joined)).check(matches(withText("No se ha unido a ningún grupo")));
    }

    @Test
    public void test_No_Member_Communities_Msg_With_Communities()     //Test para verificar que cuando se pertenece a ninguna comunidad, no aparezca un mensaje indicándo lo contrario
    {
        Intent intent = new Intent();

        generarComunidades();

        intent.putStringArrayListExtra("misComunidades", misComunidades);
        onView(withId(R.id.text_Not_Joined)).check(matches(not(withText(""))));
    }

    @Test
    public void test_Title_With_Communities()     //Test para verificar que el título de la actividad es el correcto cuando existen comunidades asociadas al usuario actual
    {
        Intent intent = new Intent();

        generarComunidades();

        intent.putStringArrayListExtra("misComunidades", misComunidades);         //Envío de las comunidades a las que pertenece el usuario actual
        intent.putStringArrayListExtra("comunidadesTotales", misComunidades);     //Envío de todos las comunidades
        intent.putExtra("userID", "currentUser");                                //Envío del ID del usuario actual
        intent.putExtra("userName", "nombre");

        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.text_Activity_Title)).check(matches(withText(ACTIVITY_TITLE)));
    }


    @Test
    public void test_FAB_Main_Button(){
        onView(withId(R.id.fabMain)).perform(click());
    }

    @Test
    public void test_FAB_Open_Close_Main_Button(){
        onView(withId(R.id.fabMain)).perform(click());
        onView(withId(R.id.fabMain)).perform(click());
    }

    @Test
    public void test_FAB_Join_Community_Button(){
        test_FAB_Main_Button();

        generarComunidades();

        Intent intent = new Intent();
        intent.putStringArrayListExtra("misComunidades", misComunidades);         //Envío de las comunidades a las que pertenece el usuario actual
        intent.putExtra("comunidadesTotales", misComunidades);
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.fabJoinCommunity)).perform(click());
    }

    //Metodo para generar comunidades dummies
    public void generarComunidades()
    {
        misComunidades.clear();

        misComunidades.add("Comunidad 1 Test");
        misComunidades.add("Comunidad 2 Test");
        misComunidades.add("Comunidad 3 Test");

        //Ciclo que toma del arreglo con los datos de la BD para crear cada objeto Comunidad y las almacena en el arreglo de la clase
        for(int i =0; i< misComunidades.size(); ++i)
        {
            comunidadList.add(new Comunidad(R.drawable.community,
                    misComunidades.get(i),
                    "0 miembros",
                    new ArrayList<String>(),
                    "Descripción genérica de test para la Comunidad "+ misComunidades.get(0) + "."));
        }
    }

}
