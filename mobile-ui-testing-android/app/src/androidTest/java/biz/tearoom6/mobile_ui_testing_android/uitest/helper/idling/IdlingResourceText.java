package biz.tearoom6.mobile_ui_testing_android.uitest.helper.idling;

import android.widget.TextView;

public class IdlingResourceText extends IdlingResourceBase<TextView> {

    public IdlingResourceText(TextView target) {
        super(target);
    }

    @Override
    protected boolean isReady(TextView target) {
        return target.getText().toString().length() > 0;
    }
}
