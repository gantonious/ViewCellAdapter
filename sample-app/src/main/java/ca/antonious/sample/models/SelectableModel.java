package ca.antonious.sample.models;

/**
 * Created by George on 2017-01-08.
 */

public class SelectableModel {
    private String name;

    public SelectableModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
