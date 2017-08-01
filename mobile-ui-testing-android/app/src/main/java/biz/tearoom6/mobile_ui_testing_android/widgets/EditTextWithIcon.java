package biz.tearoom6.mobile_ui_testing_android.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;

public class EditTextWithIcon extends android.support.v7.widget.AppCompatEditText {

    public EditTextWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }

    public static class GenericTextWatcher implements TextWatcher {

        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                ViewParent parent = view.getParent().getParent();
                if (parent instanceof TextInputLayout) {
                    ((TextInputLayout) parent).setError(null);
                }
                ((EditText) view).setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // do nothing
        }
    }
}