package com.example.mahmoud.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Mahmoud on 16/06/2017.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkTitle() throws Exception {

        onView(withId(R.id.tv_toolbartitle))
                .check(matches(withText("Baking Recipes")));

    }

    @Test
    public void checkRecyclerViewTestRecipes() throws Exception {

       // onData(anything()).inAdapterView(withId(R.id.recycleview)).atPosition(1).perform(click());
//        onView(ViewMatchers.withId(R.id.recycleview))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_title)).check(matches(withText("Nutella Pei")));


    }


}
