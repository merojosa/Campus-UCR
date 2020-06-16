package cr.ac.ucr.ecci.cql.campus20;

import android.app.Instrumentation;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier.PhotocopierActivity;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InterestPointsTests {

    @Rule
    public ActivityScenarioRule<InterestPointsActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(InterestPointsActivity.class);

    private void initDb(Context context){
        FirebaseDB db = new FirebaseDB();
        new DeploymentScript().RunScript(db, context);
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
        String [] names = new String[7];
        int i = 0;
        for(DeploymentScript.ActivityNames activityName : DeploymentScript.ActivityNames.values()){
            names[i++] = activityInfoDao.getActivityName(activityName.ordinal());
        }

        assertArrayEquals(expectedActivityNames, names);
    }

    /**
     * Entra a InterestPointsActivity, hace click en Fotocopiadoras y revisa que exista la entrada "Mis Copias".
     * */
    @Test
    public void testInterestPointsIndex(){
        onView(withText("Fotocopias")).perform(click());
        onData(allOf(is(instanceOf(PhotocopierActivity.class)), hasEntry(equalTo("Mis Copias"), is("Mis Copias"))));
    }

}
