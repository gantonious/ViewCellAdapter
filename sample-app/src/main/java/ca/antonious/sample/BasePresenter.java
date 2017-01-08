package ca.antonious.sample;

/**
 * Created by George on 2017-01-08.
 */

public abstract class BasePresenter<TView> {
    private TView view;

    public boolean isViewAttached() {
        return view != null;
    }

    public TView getView() {
        return view;
    }

    public void attachView(TView view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }
}
