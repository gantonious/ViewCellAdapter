package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection {
    public abstract AbstractViewCell get(int position);
    public abstract void remove(int position);
    public abstract int getItemCount();
}
