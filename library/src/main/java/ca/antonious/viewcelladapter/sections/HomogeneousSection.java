package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCellFactory;

/**
 * Created by George on 2016-12-19.
 */

public class HomogeneousSection<TModel, TViewCell extends GenericViewCell<?, TModel>> extends AbstractSection {
    private GenericViewCellFactory<TModel, TViewCell> viewCellFactory;
    private Comparator<? super TModel> modelComparator;
    private List<TViewCell> viewCells;

    public HomogeneousSection(GenericViewCellFactory<TModel, TViewCell> viewCellFactory) {
        this.viewCellFactory = viewCellFactory;
        this.viewCells = new ArrayList<>();
    }

    public void add(TModel model) {
        this.viewCells.add(viewCellFactory.createViewCell(model));
        sortViewCells();
    }

    public void addAll(Collection<? extends TModel> models) {
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
        sortViewCells();
    }

    public void setAll(Collection<? extends TModel> models) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
        sortViewCells();
    }

    public void prependAll(Collection<? extends TModel> models) {
        List<TViewCell> newList = new ArrayList<>();
        newList.addAll(viewCellFactory.createAllViewCells(models));
        newList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(newList);

        sortViewCells();
    }

    public void clear() {
        viewCells.clear();
    }

    public void setModelComparator(Comparator<? super TModel> modelComparator) {
        this.modelComparator = modelComparator;
        sortViewCells();
    }

    public boolean isSortingEnabled() {
        return modelComparator != null;
    }

    private void sortViewCells() {
        if (isSortingEnabled()) {
            Collections.sort(viewCells, new Comparator<TViewCell>() {
                @Override
                public int compare(TViewCell viewCell1, TViewCell viewCell2) {
                    return modelComparator.compare(viewCell1.getModel(), viewCell2.getModel());
                }
            });
        }
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
