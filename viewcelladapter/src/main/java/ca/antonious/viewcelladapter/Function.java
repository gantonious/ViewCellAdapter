package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-12-19.
 */

public interface Function<T, R> {
    R apply(T input);
}
