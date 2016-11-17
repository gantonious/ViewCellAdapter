package ca.antonious.sample.models;

/**
 * Created by George on 2016-11-17.
 */

public class Workout {
    private String name;

    public Workout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
