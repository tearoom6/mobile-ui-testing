package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.view.View;
import android.widget.TextView;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import biz.tearoom6.mobile_ui_testing_android.MainActivity;
import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.models.MainPage;
import biz.tearoom6.mobile_ui_testing_android.uitest.helper.idling.IdlingResourceText;
import biz.tearoom6.mobile_ui_testing_android.uitest.helper.idling.IdlingResourceVisible;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static biz.tearoom6.mobile_ui_testing_android.uitest.matcher.UITestViewMatcher.toMatcher;

public class ViewHelper {

    public static Activity mActivity;

    /**
     * Select specific tab.
     * @param name tab name.
     */
    public static void selectTab(Matcher<View> name) {
        if (mActivity instanceof MainActivity) {
            waitUntilDisplayed(R.id.pager_tab_strip);
            for (MainPage _page : MainPage.values()) {
                try {
                    onView(name).perform(click());
                    return;
                } catch (NoMatchingViewException | AssertionFailedError | PerformException ignore) {
                }
                onView(withId(R.id.pager_tab_strip)).check(matches(isDisplayed())).perform(swipeLeft());
            }
        }
        throw new IllegalArgumentException("Specified tab not found: " + name);
    }

    /**
     * Wait until text displayed.
     * @param view TextView
     */
    public static void waitUntilTextDisplayed(TextView view) {
        IdlingResourceText resource = new IdlingResourceText(view);
        try {
            Espresso.registerIdlingResources(resource);
            onView(toMatcher(view)).check(matches(isDisplayed()));
        } finally {
            Espresso.unregisterIdlingResources(resource);
        }
    }

    /**
     * Wait until text displayed.
     * @param resourceId Resource ID
     */
    public static void waitUntilTextDisplayed(int resourceId) {
        waitUntilTextDisplayed((TextView) mActivity.findViewById(resourceId));
    }

    /**
     * Wait until view displayed.
     * @param view View
     */
    public static void waitUntilDisplayed(View view) {
        IdlingResourceVisible resource = new IdlingResourceVisible(view);
        try {
            Espresso.registerIdlingResources(resource);
            onView(toMatcher(view)).check(matches(isDisplayed()));
        } finally {
            Espresso.unregisterIdlingResources(resource);
        }
    }

    /**
     * Wait until view displayed.
     * @param resourceId Resource ID
     */
    public static void waitUntilDisplayed(int resourceId) {
        waitUntilDisplayed(mActivity.findViewById(resourceId));
    }
}

