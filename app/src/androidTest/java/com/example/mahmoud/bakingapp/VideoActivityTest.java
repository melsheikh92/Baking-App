package com.example.mahmoud.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class VideoActivityTest {

    @Rule
    public ActivityTestRule<VideoActivity> mainActivityTestRule =
            new ActivityTestRule<>(VideoActivity.class);

    @Test
    public void splashActivityTest() throws Exception {
        try {
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rv_steps), isDisplayed()));
            onView(withId(R.id.rv_steps))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            intended(toPackage("com.example.mahmoud.bakingapp.VideoActivity"));


            try {
                Thread.sleep(4915);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ViewInteraction appCompatImageButton = onView(
                    allOf(withId(R.id.exo_pause), withContentDescription("Pause"), isDisplayed()));
            appCompatImageButton.perform(click());
            ViewInteraction appCompatImageButton2 = onView(
                    allOf(withId(R.id.exo_play), withContentDescription("Play"), isDisplayed()));
            appCompatImageButton2.perform(click());
        } catch (Exception ex) {
        }
    }

}
