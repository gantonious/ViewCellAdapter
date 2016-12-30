package ca.antonious.viewcelladapter.viewcells;

/**
 * Created by George on 2016-12-30.
 */

public interface Selectable {
    boolean isSelected();
    void select();
    void deselect();
}
