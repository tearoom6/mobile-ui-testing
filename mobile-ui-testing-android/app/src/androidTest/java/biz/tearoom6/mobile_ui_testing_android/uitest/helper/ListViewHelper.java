package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListViewHelper {

    /**
     * Find first child {@link View} of {@link ListView}.
     * @param listView List view
     * @return View
     */
    public static View findFirstChild(ListView listView) {
        return LinearLayout.class.cast(listView.getChildAt(0)).getChildAt(0);
    }
}
