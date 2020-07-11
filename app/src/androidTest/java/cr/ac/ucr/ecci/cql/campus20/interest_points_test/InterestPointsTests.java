package cr.ac.ucr.ecci.cql.campus20.interest_points_test;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfo;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertArrayEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InterestPointsTests {

    /*Se usa para esperar a que carguen los datos de los Recycler Views antes de realizar las pruebas.*/
    private CountDownLatch lock;
    
    private final int TIMEOUT = 2000;

    public InterestPointsTests(){
        lock = new CountDownLatch(1);
    }

    @Rule
    public ActivityScenarioRule<InterestPointsActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(InterestPointsActivity.class);

    private void initDb(Context context){
        FirebaseDB db = new FirebaseDB();
        new DeploymentScript().RunScript(db, context);
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    /**
     * Prueba que todos los nombres de los activities estén guardados correctamente en Room.
     * */
    @Test
    public void testInterestPointsActivityNames() {
        initDb(getApplicationContext());
        IPRoomDatabase db = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").allowMainThreadQueries().build();
        ActivityInfoDao activityInfoDao = db.activityInfoDao();
        String[] expectedActivityNames = {"Puntos de interés", "Cafeterías", "Sodas", "Fotocopiadoras", "Facultades", "Bibliotecas", "Oficinas"};
        final int NAMES_COUNT = 7;
        String [] names = new String[NAMES_COUNT];
        int i = 0;
        List<ActivityInfo> activityInfo = activityInfoDao.getAll();
        for(ActivityInfo info : activityInfo){
            names[i++] = info.getName();
        }
        assertArrayEquals(expectedActivityNames, names);
    }

    /**
     * Entra a InterestPointsActivity, hace click en Fotocopiadoras y revisa que exista la entrada "Mis Copias".
     * */
    @Test
    public void testInterestPointsPhotocopiers() throws InterruptedException{
        onView(withText("Fotocopias")).perform(click());
        lock.await(TIMEOUT, TimeUnit.MILLISECONDS);
        onView(withRecyclerView(R.id.rv_list_item).atPosition(2))
                .check(matches(hasDescendant(withText("Impresiones Millenium"))));
    }


    /**
     * Entra a InterestPointsActivity, hace click en Bibliotecas y revisa que exista la entrada "Carlos Monge Alfaro".
     * */
    @Test
    public void testInterestPointsBibliotecas() throws InterruptedException{
        onView(withText("Bibliotecas")).perform(click());
        lock.await(TIMEOUT, TimeUnit.MILLISECONDS);
        onView(withRecyclerView(R.id.rv_list_item).atPosition(3))
                .check(matches(hasDescendant(withText("Carlos Monge Alfaro"))));
    }

    /**
     * Entra a InterestPointsActivity, hace click en Oficinas y revisa que exista la entrada "OCCI".
     * */
    @Test
    public void testInterestPointsOficinas() throws InterruptedException{
        onView(withText(" Oficinas")).perform(click());
        lock.await(TIMEOUT, TimeUnit.MILLISECONDS);
        onView(withRecyclerView(R.id.rv_list_item).atPosition(2))
                .check(matches(hasDescendant(withText("OCCI"))));
    }

    /**
     * Entra a InterestPointsActivity, hace click en Facultades y revisa que exista la entrada "Artes".
     * */
    @Test
    public void testInterestPointsFaculties() throws InterruptedException{
        onView(withText("Facultades")).perform(click());
        lock.await(TIMEOUT, TimeUnit.MILLISECONDS);
        onView(withRecyclerView(R.id.rv_list_item).atPosition(0))
                .check(matches(hasDescendant(withText("Odontología"))));
    }

    /**
     * Entra a InterestPointsActivity, hace click en Cafeterías y revisa que exista la entrada "95 grados".
     * */
    @Test
    public void testInterestPointsCofeeShops() throws InterruptedException{
        onView(withText("Cafeterías")).perform(click());
        lock.await(TIMEOUT, TimeUnit.MILLISECONDS);
        onView(withRecyclerView(R.id.rv_list_item).atPosition(1))
                .check(matches(hasDescendant(withText("Aroma y Sabor"))));
    }
}
