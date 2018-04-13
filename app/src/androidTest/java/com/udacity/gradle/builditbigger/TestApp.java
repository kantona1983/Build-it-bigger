package com.udacity.gradle.builditbigger;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class TestApp extends AndroidTestCase {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testVerifyResponse() {
        onView(withId(R.id.btn_joke))
                .perform(click());
        onView(withId(R.id.textview))
                .check(matches((withText(not(isEmptyString())))));
        onView(withId(R.id.textview))
                .check(matches(isDisplayed()));

    }

}