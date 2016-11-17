package ca.antonious.sample.models;

/**
 * Created by George on 2016-11-17.
 */

public class Task {
    public final String name;
    public final int timesCompleted;

    public Task(String name, int numCompletions) {
        this.name = name;
        this.timesCompleted = numCompletions;
    }
}
