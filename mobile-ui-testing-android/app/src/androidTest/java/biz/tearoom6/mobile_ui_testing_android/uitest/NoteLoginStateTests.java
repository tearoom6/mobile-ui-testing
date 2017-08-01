package biz.tearoom6.mobile_ui_testing_android.uitest;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import biz.tearoom6.mobile_ui_testing_android.R;
import biz.tearoom6.mobile_ui_testing_android.uitest.helper.MainActivityTestBase;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ListViewHelper.findFirstChild;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.NoteHelper.createNote;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.NoteHelper.randomContent;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.NoteHelper.randomTitle;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.UserHelper.loginAsRegisteredUser;
import static biz.tearoom6.mobile_ui_testing_android.uitest.helper.ViewHelper.selectTab;
import static biz.tearoom6.mobile_ui_testing_android.uitest.matcher.UITestViewMatcher.hasErrorTextInLayout;

@RunWith(AndroidJUnit4.class)
public class NoteLoginStateTests extends MainActivityTestBase {

    @Before
    public void setUp() {
        loginAsRegisteredUser();
        selectTab(withText(R.string.tab_note));
    }

    @Test
    public void createNewNote() {
        // Input random title & content.
        String title   = randomTitle();
        String content = randomContent();
        createNote(title, content);

        // Check specified title is registered.
        ListView notes = (ListView) activity.findViewById(R.id.notes_list_title);
        assertThat(
                title + " is displayed in the ListView.",
                findFirstChild(notes),
                withText(title)
        );
    }

    @Test
    public void createNoteWithEmptyTitle() {
        // Input random title with empty content.
        String title = randomTitle();
        createNote(title, "");

        // Check specified title is registered.
        ListView notes = (ListView) activity.findViewById(R.id.notes_list_title);
        assertThat(
                title + " is displayed in the ListView.",
                findFirstChild(notes),
                withText(title)
        );
    }

    @Test
    public void createNoteWithEmptyContent() {
        // Input random content with empty title.
        createNote("", randomContent());

        // Check error message is displayed properly.
        String expectMessage = messageHelper.getLocalizedMessage(R.string.error_required);
        View noteTitle = activity.findViewById(R.id.note_inputLayout_title);
        assertThat(
                "Error message is displayed properly.",
                noteTitle,
                hasErrorTextInLayout(expectMessage)
        );
    }
}
