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
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.logout;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.selectTab;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class NoteLogoutStateTests extends MainActivityTestBase {

    @Before
    public void setUp() {
        logout();
        selectTab(withText(R.string.tab_note));
    }

    @Test
    public void notLogin() {
        // Check not login status is displayed.
        assertThat(
                "Unauthorized label is displayed.",
                activity.findViewById(R.id.notes_label_unauthorized),
                isDisplayed()
        );

        // Check create button is not displayed.
        assertThat(
                "Create button is not displayed which should appear after login.",
                activity.findViewById(R.id.notes_button_create),
                not(isDisplayed())
        );
    }
}
