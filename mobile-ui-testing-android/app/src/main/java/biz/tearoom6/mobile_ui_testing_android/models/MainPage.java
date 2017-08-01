package biz.tearoom6.mobile_ui_testing_android.models;

import biz.tearoom6.mobile_ui_testing_android.R;

public enum MainPage {

    USER(0, R.string.tab_user),
    NOTE(1, R.string.tab_note),
    ;

    private final int index;
    private final int pageTitleResId;

    MainPage(int index, int pageTitleResId) {
        this.index = index;
        this.pageTitleResId = pageTitleResId;
    }

    public int getIndex() {
        return index;
    }

    public int getPageTitleResId() {
        return pageTitleResId;
    }

    public static MainPage valueOfIndex(int index) {
        for (MainPage page: MainPage.values()) {
            if (page.getIndex() == index) {
                return page;
            }
        }
        return null;
    }

}
