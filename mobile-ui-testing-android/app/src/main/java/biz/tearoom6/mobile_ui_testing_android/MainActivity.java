package biz.tearoom6.mobile_ui_testing_android;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import biz.tearoom6.mobile_ui_testing_android.adapters.MainFragmentPagerAdapter;
import biz.tearoom6.mobile_ui_testing_android.models.MainPage;

public class MainActivity extends FragmentActivity {

    private FragmentPagerAdapter mFragmentPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViewPager();
    }

    private void createViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);

        mFragmentPagerAdapter = new MainFragmentPagerAdapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentPagerAdapter.getCount());

        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(ContextCompat.getColor(this, R.color.cyan));
        pagerTabStrip.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
    }

    public void changePage(MainPage page) {
        mViewPager.setCurrentItem(page.getIndex());
        mFragmentPagerAdapter.notifyDataSetChanged();
    }
}
