package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.support.test.espresso.NoMatchingViewException;

import junit.framework.AssertionFailedError;

import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.uitest.model.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.AssetHelper.loadTestUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.selectTab;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.waitUntilDisplayed;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserHelper {

    public static String temporaryNickname() {
        return "test" + random(3, 'あ', 'ん', false, false) + System.currentTimeMillis();
    }

    public static String temporaryMailAddress() {
        return randomAlphabetic(10).toLowerCase() + System.currentTimeMillis() + "@example.com";
    }

    public static String temporaryPassword() {
        return "password." + randomAlphabetic(4) + System.currentTimeMillis();
    }

    public static void login(String email, String password) {
        selectTab(withText(R.string.tab_user));

        // Input email & password.
        onView(withId(R.id.auth_input_loginEmail)).perform(replaceText((email)));
        onView(withId(R.id.auth_input_loginPassword)).perform(replaceText(password));

        // Click login button.
        onView(withId(R.id.auth_button_login)).perform(scrollTo(), click());
    }

    public static void loginAsRegisteredUser() {
        logout();

        User testUser = loadTestUser();
        login(testUser.getEmail(), testUser.getPassword());

        waitUntilDisplayed(R.id.auth_label_nickname);
    }

    public static void logout() {
        selectTab(withText(R.string.tab_user));
        try {
            onView(withId(R.id.auth_button_logout)).check(matches(isDisplayed())).perform(click());
        } catch (NoMatchingViewException | AssertionFailedError ignore) {
        }
    }

    public static void registerUser(String nickname, String email, String password) {
        selectTab(withText(R.string.tab_user));

        // Input email & password & nickname.
        onView(withId(R.id.auth_input_registerNickname)).perform(replaceText(nickname));
        onView(withId(R.id.auth_input_registerEmail)).perform(replaceText(email));
        onView(withId(R.id.auth_input_registerPassword)).perform(replaceText(password));

        // Click register button.
        onView(withId(R.id.auth_button_register)).perform(scrollTo(), click());
    }
}

