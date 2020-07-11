package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

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

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CrearComunidadUITest {

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
    public void createCommunityForm(){
        Intent intent = new Intent();
        intent.putExtra("userID", "usuarioID");
        intentsTestRule.launchActivity(intent);
        onView(withId(R.id.button_Create_Community_Form)).perform(click());
    }
}
