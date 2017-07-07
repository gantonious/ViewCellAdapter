package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.antonious.viewcelladapter.utils.CollectionUtils;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-11-17.
 */

public class Section extends AbstractSection {
    protected List<AbstractViewCell> viewCells;

    public Section() {
        this.viewCells = new ArrayList<>();
    }

    public List<AbstractViewCell> getAll() {
        return new ArrayList<>(viewCells);
    }

    public List<AbstractViewCell> getAllSelected() {
        List<AbstractViewCell> selectedViewCells = new ArrayList<>();

        for (AbstractViewCell viewCell: viewCellIterator()) {
            if (viewCell.isSelected()) {
                selectedViewCells.add(viewCell);
            }
        }

        return selectedViewCells;
    }

    public void add(AbstractViewCell viewCell) {
        this.viewCells.add(viewCell);
        invalidateData();
    }

    public void addAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.addAll(viewCells);
        invalidateData();
    }

    public void setAll(Collection<? extends AbstractViewCell> viewCells) {
        CollectionUtils.setAll(this.viewCells, viewCells);
        invalidateData();
    }

    public void prependAll(Collection<? extends AbstractViewCell> viewCells) {
        CollectionUtils.prependAll(this.viewCells, viewCells);
        invalidateData();
    }

    public void remove(int position) {
        viewCells.remove(position);
        invalidateData();
    }

    public void clear() {
        viewCells.clear();
        invalidateData();
    }

    @Override
    public AbstractViewCell get(int position) {
        return viewCells.get(position);
    }

    @Override
    public int getItemCount() {
        return viewCells.size();
    }

    private void invalidateData() {
        notifyDataChanged();
    }
}