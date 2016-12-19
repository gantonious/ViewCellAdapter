package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.antonious.viewcelladapter.Func;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCellFactory;

/**
 * Created by George on 2016-12-19.
 */

public class HomogeneousSection<TModel, TViewCell extends GenericViewCell<?, TModel>> extends AbstractSection {
    private GenericViewCellFactory<TModel, TViewCell> viewCellFactory;

    private Func<? super TModel, ? extends Boolean> filterFunction;
    private Comparator<? super TModel> modelComparator;

    private List<TViewCell> allViewCells;
    private List<TViewCell> filteredViewCells;

    public HomogeneousSection(GenericViewCellFactory<TModel, TViewCell> viewCellFactory) {
        this.viewCellFactory = viewCellFactory;
        this.allViewCells = new ArrayList<>();
        this.filteredViewCells = new ArrayList<>();
    }

    public void add(TModel model) {
        this.allViewCells.add(viewCellFactory.createViewCell(model));
        prepareViewCellsToDisplay();
    }

    public void addAll(Collection<? extends TModel> models) {
        this.allViewCells.addAll(viewCellFactory.createAllViewCells(models));
        prepareViewCellsToDisplay();
    }

    public void setAll(Collection<? extends TModel> models) {
        this.allViewCells.clear();
        this.allViewCells.addAll(viewCellFactory.createAllViewCells(models));
        prepareViewCellsToDisplay();
    }

    public void prependAll(Collection<? extends TModel> models) {
        List<TViewCell> newList = new ArrayList<>();
        newList.addAll(viewCellFactory.createAllViewCells(models));
        newList.addAll(this.allViewCells);

        this.allViewCells.clear();
        this.allViewCells.addAll(newList);

        prepareViewCellsToDisplay();
    }

    public void clear() {
        allViewCells.clear();
        prepareViewCellsToDisplay();
    }

    public void prepareViewCellsToDisplay() {
        sortViewCells();
        filterViewCells();
    }

    public HomogeneousSection<TModel, TViewCell> setModelComparator(Comparator<? super TModel> modelComparator) {
        this.modelComparator = modelComparator;
        prepareViewCellsToDisplay();

        return this;
    }

    public boolean isSortingEnabled() {
        return modelComparator != null;
    }

    private void sortViewCells() {
        if (isSortingEnabled()) {
            Collections.sort(allViewCells, new Comparator<TViewCell>() {
                @Override
                public int compare(TViewCell viewCell1, TViewCell viewCell2) {
                    return modelComparator.compare(viewCell1.getModel(), viewCell2.getModel());
                }
            });
        }
    }

    public HomogeneousSection<TModel, TViewCell>  setFilterFunction(Func<? super TModel, ? extends Boolean> filterFunction) {
        this.filterFunction = filterFunction;
        prepareViewCellsToDisplay();

        return this;
    }

    public boolean isFilteringEnabled() {
        return filterFunction != null;
    }

    public boolean shouldDisplayViewCell(TViewCell viewCell) {
        return !isFilteringEnabled() ||
                filterFunction.call(viewCell.getModel());
    }

    private void filterViewCells() {
        filteredViewCells.clear();

        for (TViewCell viewCell: allViewCells) {
            if (shouldDisplayViewCell(viewCell)) {
                filteredViewCells.add(viewCell);
            }
        }
    }

    @Override
    public AbstractViewCell get(int position) {
        return filteredViewCells.get(position);
    }

    @Override
    public void remove(int position) {
        allViewCells.remove(position);
        prepareViewCellsToDisplay();
    }

    @Override
    public int getItemCount() {
        return filteredViewCells.size();
    }
}
