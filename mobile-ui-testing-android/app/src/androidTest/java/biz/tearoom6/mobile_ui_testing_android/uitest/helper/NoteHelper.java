package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import biz.tearoom6.mobile_ui_testing_android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.selectTab;
import static org.apache.commons.lang3.RandomStringUtils.random;

public class NoteHelper {

    public static String randomTitle() {
        return "Title:" + random(5, 'あ', 'ん', false, false) + System.currentTimeMillis();
    }

    public static String randomContent() {
        return "Content:" + random(5, 'あ', 'ん', false, false) + System.currentTimeMillis();
    }

    public static void createNote(String title, String content) {
        selectTab(withText(R.string.tab_note));

        // Click create button.
        onView(withId(R.id.notes_button_create)).perform(click());

        // Input title & content.
        onView(withId(R.id.note_input_title)).perform(replaceText(title));
        onView(withId(R.id.note_input_content)).perform(replaceText(content));

        // Click save button.
        onView(withId(R.id.note_button_save)).perform(click());
    }
}
