package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-12-19.
 */

public interface Func<T, R> {
    R call(T input);
}
