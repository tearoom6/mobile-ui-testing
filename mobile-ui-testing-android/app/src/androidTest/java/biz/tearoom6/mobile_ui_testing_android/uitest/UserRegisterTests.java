package biz.tearoom6.mobile_ui_testing_android.uitest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.uitest.helper.MainActivityTestBase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.AssetHelper.loadTestUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.logout;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.registerUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.temporaryMailAddress;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.temporaryNickname;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.temporaryPassword;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.waitUntilDisplayed;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.waitUntilTextDisplayed;

@RunWith(AndroidJUnit4.class)
public class UserRegisterTests extends MainActivityTestBase {

    @Before
    public void setup() {
        logout();
        onView(withId(R.id.auth_button_openRegister)).perform(click());
    }

    @After
    public void teardown() {
    }

    @Test
    public void registerNewUser() {
        final String nickname = temporaryNickname();
        final String email    = temporaryMailAddress();
        final String password = temporaryPassword();

        // Input email & password & nickname.
        registerUser(nickname, email, password);

        waitUntilDisplayed(R.id.auth_label_nickname);

        // Check registered nickname & email is displayed.
        assertThat(
                "Registered nickname is displayed.",
                activity.findViewById(R.id.auth_label_nickname),
                withText(nickname)
        );
        assertThat(
                "Registered email is displayed.",
                activity.findViewById(R.id.auth_label_email),
                withText(email)
        );

        // Check logout button is displayed.
        assertThat(
                "Logout button is displayed.",
                activity.findViewById(R.id.auth_button_logout),
                isDisplayed()
        );
    }

    @Test
    public void registerUserWithEmpty() {
        // Input empty email & password & nickname.
        registerUser("", "", "");

        // Check error message is displayed properly in each form.
        String expectMessage = messageHelper.getLocalizedMessage(R.string.error_required);
        assertThat(
                "Error message is displayed properly.",
                activity.findViewById(R.id.auth_input_registerNickname),
                hasErrorText(expectMessage)
        );
        assertThat(
                "Error message is displayed properly.",
                activity.findViewById(R.id.auth_input_registerEmail),
                hasErrorText(expectMessage)
        );
        assertThat(
                "Error message is displayed properly.",
                activity.findViewById(R.id.auth_input_registerPassword),
                hasErrorText(expectMessage)
        );
    }

    @Test
    public void registerUserWithAlreadyRegisteredEmail() {
        // Input already registered email.
        registerUser(temporaryNickname(), loadTestUser().getEmail(), temporaryPassword());

        waitUntilTextDisplayed(R.id.auth_label_registerErrors);

        // Check error message is displayed properly.
        String expectMessage = messageHelper.getLocalizedMessage(R.string.error_used_email);
        assertThat(
                "Error message is displayed properly.",
                activity.findViewById(R.id.auth_label_registerErrors),
                withText(expectMessage)
        );
    }
}

