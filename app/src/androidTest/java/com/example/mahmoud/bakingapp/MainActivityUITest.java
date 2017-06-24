package com.example.mahmoud.bakingapp;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
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
        try {
            onView(withId(R.id.tv_toolbartitle))
                    .check(matches(withText("Baking Recipes")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Before
    public void grantPermission() {
        getInstrumentation().getUiAutomation().executeShellCommand(
                "pm grant " + getTargetContext().getPackageName()
                        + " android.permission.INTERNET");

    }

    @Test
    public void checkRecyclerViewTestRecipes() throws Exception {
        try {
            IdlingResource idlingResource = new MyCustomIdlingResource(3000);
            Espresso.registerIdlingResources(idlingResource);

            onView(withId(R.id.recycleview))
                    .check(matches(atPosition(0, hasDescendant(withText("Nutella Pie")))));
            onView(withId(R.id.recycleview))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.tv_name)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_name)).check(matches(withText("Nutella Pie")));
            onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
            onView(withId(R.id.rv_steps))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            intended(toPackage("com.example.mahmoud.bakingapp.VideoActivity"));
            Espresso.unregisterIdlingResources(idlingResource);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    class MyCustomIdlingResource implements IdlingResource {
        int time;
        private boolean isIdle;
        long startTime;
        ResourceCallback mResourceCallback;

        public MyCustomIdlingResource(int time) {
            this.startTime = System.currentTimeMillis();
            isIdle = false;
            this.time = time;
        }

        @Override
        public String getName() {
            return MyCustomIdlingResource.class.getName();
        }

        @Override
        public boolean isIdleNow() {

            if (System.currentTimeMillis() - startTime >= time) {

                isIdle = true;
                mResourceCallback.onTransitionToIdle();

            }
            return isIdle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            this.mResourceCallback = callback;

        }
    }

}
