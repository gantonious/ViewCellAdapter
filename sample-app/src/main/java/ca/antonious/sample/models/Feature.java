package ca.antonious.sample.models;

import android.app.Activity;

/**
 * Created by George on 2017-01-01.
 */

public class Feature {
    private String title;
    private Activity showcaseActivity;

    public Feature(String title, Activity showcaseActivity) {
        this.title = title;
        this.showcaseActivity = showcaseActivity;
    }

    public String getTitle() {
        return title;
    }

    public Activity getShowcaseActivity() {
        return showcaseActivity;
    }
}
