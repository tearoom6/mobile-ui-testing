package biz.tearoom6.mobile_ui_testing_android.uitest.helper.idling;

import android.view.View;

public class IdlingResourceVisible extends IdlingResourceBase<View> {

    public IdlingResourceVisible(View target) {
        super(target);
    }

    @Override
    protected boolean isReady(View target) {
        return target.isShown();
    }
}
