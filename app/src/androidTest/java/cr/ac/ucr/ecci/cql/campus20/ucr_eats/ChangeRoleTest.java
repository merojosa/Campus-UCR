package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ChangeRoleTest
{
    private CountDownLatch lock;

    @Before
    public void setup()
    {
        lock = new CountDownLatch(1);
    }

    @Rule
    public ActivityTestRule<MainUcrEats> activityRule
            = new ActivityTestRule<>(MainUcrEats.class);

    @Test
    public void testToClient() throws InterruptedException
    {
        lock.await(5000, TimeUnit.MILLISECONDS);
        Intents.init();

        // Open three-dot menu
        openActionBarOverflowOrOptionsMenu(activityRule.getActivity());

        // Click the change role item and open the activity
        onView(withText(R.string.default_role_menu)).perform(click());

        // Click the spinner
        onView(withId(R.id.default_role_spinner)).perform(click());

        // Choose the client text
        onView(withText("Cliente")).perform(click());

        // Save the option and open the activity
        onView(withId(R.id.save_role_button)).perform((click()));

        // Verify that the mainucreats activity opened
        intended(hasComponent(MainUcrEats.class.getName()));

        Intents.release();
    }
}
