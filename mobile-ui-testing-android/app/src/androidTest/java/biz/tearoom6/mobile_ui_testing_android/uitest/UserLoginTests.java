package biz.tearoom6.mobile_ui_testing_android.uitest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.uitest.helper.MainActivityTestBase;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.AssetHelper.loadTestUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.login;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.loginAsRegisteredUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.logout;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.waitUntilTextDisplayed;

@RunWith(AndroidJUnit4.class)
public class UserLoginTests extends MainActivityTestBase {

    @Before
    public void setup() {
        logout();
    }

    @Test
    public void loginRegisteredAccount() {
        loginAsRegisteredUser();

        // Check registered nickname & email is displayed.
        assertThat(
                "Registered nickname is displayed",
                activity.findViewById(R.id.auth_label_nickname),
                withText(loadTestUser().getNickname())
        );
        assertThat(
                "Registered email is displayed",
                activity.findViewById(R.id.auth_label_email),
                withText(loadTestUser().getEmail())
        );

        // Check logout button is displayed.
        assertThat(
                "Logout button is displayed",
                activity.findViewById(R.id.auth_button_logout),
                isDisplayed()
        );
    }

    @Test
    public void loginWithInvalidPassword() {
        // Login as registered user with invalid password.
        login(loadTestUser().getEmail(), "invalid_password");

        waitUntilTextDisplayed(R.id.auth_label_loginErrors);

        // Check error message is displayed properly.
        String expectMessage = messageHelper.getLocalizedMessage(R.string.failed_login);
        assertThat(
                "Error message is displayed properly.",
                activity.findViewById(R.id.auth_label_loginErrors),
                withText(expectMessage)
        );
    }
}
