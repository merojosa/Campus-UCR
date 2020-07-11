package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.RemoteException;
import android.util.Log;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;

import java.util.Collection;

import cr.ac.ucr.ecci.cql.campus20.R;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CrearComunidadUITest {

    @Before
    public void init(){
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Point[] coordinates = new Point[4];
        coordinates[0] = new Point(248, 1520);
        coordinates[1] = new Point(248, 929);
        coordinates[2] = new Point(796, 1520);
        coordinates[3] = new Point(796, 929);
        try {
            if (!uiDevice.isScreenOn()) {
                uiDevice.wakeUp();
                uiDevice.swipe(coordinates, 10);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public IntentsTestRule<CrearComunidad> intentsTestRule =
            new IntentsTestRule<>(CrearComunidad.class, true, false);
//    @Rule
//    public ActivityScenarioRule<CrearComunidad> activityScenarioRule
//            = new ActivityScenarioRule<>(CrearComunidad.class);

//    private CrearComunidad mCrearComunidad;
//
//    @Before
//    public void setUp(){
//        mCrearComunidad = mCrearComunidad.getActivity();
//    }
//    Activity actividadActual = null;
//    public Activity getActivityInstance(){
//        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
//            public void run() {
//                Collection<Activity> resumedActivities =
//                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
//                for (Activity activity: resumedActivities){
//                    Log.d("Your current activity: ", activity.getClass().getName());
//                    actividadActual = activity;
//                    break;
//                }
//            }
//        });
//
//        return actividadActual;
//    }

    // Prueba el botón para oonfirmar creación de comunidad
    @Test
    public void createCommunity(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.button_Create_Community_Form)).perform(click());
    }

    public static final String TEST_STRING_EMPTY = "";
    public static final String TEST_STRING_NAME = "Diana";
    public static final String TEST_STRING_DESCRIPTION = "Este es mi grupiii";

    //IMPORTANTE: Con tests de forms tienen que estar desactivados
    //Window animation scale, Transition animation scale, Animator animation scale
    //Para evitar problemas con escritura y manejo del teclado

    //IMPORTANTE: Existe un método en @Before que activa la pantalla del dispositivo, pero
    //es recomendable que esta esté activa (si no, es necesario desbloquear el dispositivo cuando se enciende la pantalla)

    //Testea el form de nombre con un String vacío
    @Test
    public void createCommunityForm1(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.editText_Create_Community_Name)).perform(typeText(TEST_STRING_EMPTY), closeSoftKeyboard());
        onView(withId(R.id.editText_Create_Community_Name)).check(matches(withText(TEST_STRING_EMPTY)));
    }

    //Testea el form de nombre con un String de nombre
    @Test
    public void createCommunityForm2(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.editText_Create_Community_Name)).perform(typeText(TEST_STRING_NAME), closeSoftKeyboard());
        onView(withId(R.id.editText_Create_Community_Name)).check(matches(withText(TEST_STRING_NAME)));
    }

    //Testea el form de descripción con un String vacío
    @Test
    public void createCommunityForm3(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.editText_Create_Community_Description)).perform(typeText(TEST_STRING_EMPTY), closeSoftKeyboard());
        onView(withId(R.id.editText_Create_Community_Description)).check(matches(withText(TEST_STRING_EMPTY)));
    }

    //Testea el form de descripción con un String de descripción
    @Test
    public void createCommunityForm4(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.editText_Create_Community_Description)).perform(typeText(TEST_STRING_DESCRIPTION), closeSoftKeyboard());
        onView(withId(R.id.editText_Create_Community_Description)).check(matches(withText(TEST_STRING_DESCRIPTION)));
    }



}
