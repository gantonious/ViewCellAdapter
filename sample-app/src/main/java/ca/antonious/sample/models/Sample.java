package ca.antonious.sample.models;

import android.app.Activity;

/**
 * Created by George on 2017-01-01.
 */

public class Sample {
    private String title;
    private Class<? extends Activity> showcaseActivityClass;

    public Sample(String title, Class<? extends Activity> showcaseActivityClass) {
        this.title = title;
        this.showcaseActivityClass = showcaseActivityClass;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends Activity> getShowcaseActivityClass() {
        return showcaseActivityClass;
    }
}
