package biz.tearoom6.mobile_ui_testing_android.uitest.helper.idling;

import android.support.test.espresso.IdlingResource;
import android.view.View;

public abstract class IdlingResourceBase<T extends View> implements IdlingResource {

    private IdlingResource.ResourceCallback resourceCallback;
    private T target;

    public IdlingResourceBase(T target) {
        this.target = target;
    }

    protected abstract boolean isReady(T target);

    @Override
    public String getName() {
        return this.getClass().getName() + ":" + target.toString();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = isReady(target);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}

