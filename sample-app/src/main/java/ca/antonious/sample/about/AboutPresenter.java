package ca.antonious.sample.about;

import java.util.List;

import ca.antonious.sample.BasePresenter;

/**
 * Created by George on 2017-01-08.
 */

public class AboutPresenter extends BasePresenter<IAboutView> {
    private IApplicationInfoProvider applicationInfoProvider;

    public AboutPresenter(IApplicationInfoProvider applicationInfoProvider) {
        this.applicationInfoProvider = applicationInfoProvider;
    }

    public void loadApplicationInformation() {
        dispatchDisplayLibraries(applicationInfoProvider.getLibraries());
    }

    private void dispatchDisplayLibraries(List<Library> libraries) {
        if (isViewAttached()) {
            getView().displayLicences(libraries);
        }
    }
}
