package ca.antonious.sample.models;

import android.app.Activity;

/**
 * Created by George on 2017-01-01.
 */

public class Sample {
    private String title;
    private String description;
    private Class<? extends Activity> showcaseActivityClass;

    public Sample(String title, String description, Class<? extends Activity> showcaseActivityClass) {
        this.title = title;
        this.description = description;
        this.showcaseActivityClass = showcaseActivityClass;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends Activity> getShowcaseActivityClass() {
        return showcaseActivityClass;
    }

    public static class Builder {
        private String title;
        private String description;
        private Class<? extends Activity> showcaseActivityClass;

        public Sample build() {
            return new Sample(title, description, showcaseActivityClass);
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setShowcaseActivityClass(Class<? extends Activity> showcaseActivityClass) {
            this.showcaseActivityClass = showcaseActivityClass;
            return this;
        }
    }
}
