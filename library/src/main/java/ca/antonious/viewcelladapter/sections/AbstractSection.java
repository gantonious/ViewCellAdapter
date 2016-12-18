package ca.antonious.viewcelladapter.sections;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection {
    public abstract AbstractViewCell get(int position);
    public abstract void remove(int position);
    public abstract int getItemCount();

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
