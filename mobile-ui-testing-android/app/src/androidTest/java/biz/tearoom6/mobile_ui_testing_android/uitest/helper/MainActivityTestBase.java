package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;

import biz.tearoom6.mobile_ui_testing_android.MainActivity;

public class MainActivityTestBase {

    protected MainActivity activity;
    protected MessageHelper messageHelper;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void baseSetup() {
        this.activity = activityRule.getActivity();
        this.messageHelper = new MessageHelper(activityRule.getActivity().getResources());
        ViewHelper.mActivity = activity;
    }
}
