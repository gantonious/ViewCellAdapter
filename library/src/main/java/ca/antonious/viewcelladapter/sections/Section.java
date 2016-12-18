package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-11-17.
 */

public class Section extends AbstractSection {
    protected List<AbstractViewCell> viewCells;

    public Section() {
        this.viewCells = new ArrayList<>();
    }

    public void add(AbstractViewCell viewCell) {
        this.viewCells.add(viewCell);
    }

    public void addAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.addAll(viewCells);
    }

    public void setAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCells);
    }

    public void prependAll(Collection<? extends AbstractViewCell> viewCells) {
        List<AbstractViewCell> newList = new ArrayList<>();
        newList.addAll(viewCells);
        newList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(newList);
    }

    public void clear() {
        viewCells.clear();
    }

    @Override
    public AbstractViewCell get(int position) {
        return viewCells.get(position);
    }

    @Override
    public void remove(int position) {
        viewCells.remove(position);
    }

    @Override
    public int getItemCount() {
        return viewCells.size();
    }
}