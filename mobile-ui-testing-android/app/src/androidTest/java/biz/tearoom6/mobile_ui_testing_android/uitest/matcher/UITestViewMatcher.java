package biz.tearoom6.mobile_ui_testing_android.uitest.matcher;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.core.Is.is;

public class UITestViewMatcher {

    /**
     * Create Matcher from View.
     * @param v View
     * @return a TypeSafeMatcher
     */
    public static Matcher<View> toMatcher(final View v) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return item == v;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(v.toString());
            }
        };
    }

    /**
     * Create Matcher which judges String equality with error message which {@link TextInputLayout} contains.
     * @param stringMatcher
     * @return Matcher
     */
    public static Matcher<View> hasErrorTextInLayout(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            @Override
            protected boolean matchesSafely(TextInputLayout view) {
                return stringMatcher.matches(view.getError().toString());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: ");
                stringMatcher.describeTo(description);
            }
        };
    }

    /**
     * Create Matcher which judges view has the expected error message.
     * @param expectedError Expected message
     * @return Matcher
     */
    public static Matcher<View> hasErrorTextInLayout(final String expectedError) {
        return hasErrorTextInLayout(is(expectedError));
    }
}
