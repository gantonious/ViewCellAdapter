package ca.antonious.sample.about;

/**
 * Created by George on 2017-01-08.
 */

public class Library {
    private String title;
    private String sourceControlUrl;

    public Library(String title, String sourceControlUrl) {
        this.title = title;
        this.sourceControlUrl = sourceControlUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceControlUrl() {
        return sourceControlUrl;
    }
}
