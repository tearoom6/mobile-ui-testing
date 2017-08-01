package biz.tearoom6.mobile_ui_testing_android.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import biz.tearoom6.mobile_ui_testing_android.fragments.AuthenticationFragment_;
import biz.tearoom6.mobile_ui_testing_android.fragments.NotesFragment_;
import biz.tearoom6.mobile_ui_testing_android.models.MainPage;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Activity mActivity;

    public MainFragmentPagerAdapter(Activity activity, FragmentManager fragmentManager) {
        super(fragmentManager);
        mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (MainPage.valueOfIndex(position)) {
            case USER:
                return new AuthenticationFragment_(); // Use EFragment of AndroidAnnotations.
            case NOTE:
                return new NotesFragment_(); // Use EFragment of AndroidAnnotations.
        }
        throw new IndexOutOfBoundsException("Out of bounds fragment requested（position = " + position + "）");
    }

    @Override
    public int getCount() {
        return MainPage.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (MainPage.values().length < position) {
            throw new IndexOutOfBoundsException("Out of bounds fragment requested（position = " + position + "）");
        }
        return mActivity.getResources().getString(MainPage.values()[position].getPageTitleResId());
    }

}
