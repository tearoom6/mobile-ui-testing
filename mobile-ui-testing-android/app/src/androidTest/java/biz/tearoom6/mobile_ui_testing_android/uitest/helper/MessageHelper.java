package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.content.res.Resources;

public class MessageHelper {

    private Resources resources;

    public MessageHelper(Resources resources) {
        this.resources = resources;
    }

    public String getLocalizedMessage(int id) {
        return resources.getString(id);
    }
}