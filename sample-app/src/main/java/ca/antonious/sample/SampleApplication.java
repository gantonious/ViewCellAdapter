package ca.antonious.sample;

import android.app.Application;

import ca.antonious.sample.about.AboutPresenter;
import ca.antonious.sample.about.ApplicationInfoProvider;
import ca.antonious.sample.about.IApplicationInfoProvider;

/**
 * Created by George on 2017-01-08.
 */

public class SampleApplication extends Application {
    public IApplicationInfoProvider getApplicationInfoProvider() {
        return new ApplicationInfoProvider(getApplicationContext());
    }

    public AboutPresenter getAboutPresenter() {
        return new AboutPresenter(getApplicationInfoProvider());
    }
}
