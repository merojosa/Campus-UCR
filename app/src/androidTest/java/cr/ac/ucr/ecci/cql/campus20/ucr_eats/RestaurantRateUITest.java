package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RestaurantRateUITest {

    private CountDownLatch lock;
    private String previousRate;

    @Before
    public void setup()
    {
        lock = new CountDownLatch(1);
    }

    @Rule
    public ActivityTestRule<MainUcrEats> activityRule
            = new ActivityTestRule<>(MainUcrEats.class);

    @Test
    public void testRateOkUINumber() throws InterruptedException
    {
        // Wait for Firebase read
        lock.await(5000, TimeUnit.MILLISECONDS);

        // Click Soda la U
        onView(ViewMatchers.withId(R.id.ucr_eats_rv)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        previousRate = getText(withId(R.id.rating_num));

        // Click a la estrella para abrir dialog
        onView(withId(R.id.rating_star)).perform(ViewActions.click());

        // Click en calificación de 1.0
        onView(withId(R.id.ratingBar)).perform(setRating(2.0f));

        // Click en Si
        onView(withId(android.R.id.button1)).perform(ViewActions.click());

        onView(withId(R.id.rating_num)).
                check(matches(withText("2.0")));

    }

    @Test
    public void testRateCancelUINumber() throws InterruptedException
    {
        // Wait for Firebase read
        lock.await(5000, TimeUnit.MILLISECONDS);

        // Click Soda la U
        onView(withId(R.id.ucr_eats_rv)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        previousRate = getText(withId(R.id.rating_num));

        // Click a la estrella para abrir dialog
        onView(withId(R.id.rating_star)).perform(ViewActions.click());

        // Click en calificación de 1.0
        onView(withId(R.id.ratingBar)).perform(setRating(2.0f));

        // Click en Si
        onView(withId(android.R.id.button2)).perform(ViewActions.click());

        onView(withId(R.id.rating_num)).
                check( matches( withText(previousRate)));

    }


    private static ViewAction setRating(final float rating) {
        if (rating % 0.5 != 0) {
            throw new IllegalArgumentException("Rating must be multiple of 0.5f");
        }

        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RatingBar.class);
            }

            @Override
            public String getDescription() {
                return "Set rating on RatingBar in 0.5f increments";
            }

            @Override
            public void perform(UiController uiController, View view) {
                GeneralClickAction viewAction = new GeneralClickAction(
                        Tap.SINGLE,
                        new CoordinatesProvider() {
                            @Override
                            public float[] calculateCoordinates(View view) {
                                int[] locationOnScreen = new int[2];
                                view.getLocationOnScreen(locationOnScreen);
                                int screenX = locationOnScreen[0];
                                int screenY = locationOnScreen[1];
                                int numStars = ((RatingBar) view).getNumStars();
                                float widthPerStar = 1f * view.getWidth() / numStars;
                                float percent = rating / numStars;
                                float x = screenX + view.getWidth() * percent;
                                float y = screenY + view.getHeight() * 0.5f;
                                return new float[]{x - widthPerStar * 0.5f, y};
                            }
                        },
                        Press.FINGER,
                        InputDevice.SOURCE_UNKNOWN,
                        MotionEvent.BUTTON_PRIMARY
                );
                viewAction.perform(uiController, view);
            }
        };
    }

    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }
}





