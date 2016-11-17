package ca.antonious.sample.models;

/**
 * Created by George on 2016-11-17.
 */

public class Task {
    private String name;
    private int numCompletions;

    public Task(String name, int numCompletions) {
        this.name = name;
        this.numCompletions = numCompletions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCompletions() {
        return numCompletions;
    }

    public void setNumCompletions(int numCompletions) {
        this.numCompletions = numCompletions;
    }
}
