package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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

    private List<TViewCell> viewCells;
    private List<TViewCell> viewCellsToRender;

    public HomogeneousSection(GenericViewCellFactory<TModel, TViewCell> viewCellFactory) {
        this.viewCellFactory = viewCellFactory;
        this.viewCells = new ArrayList<>();
        this.viewCellsToRender = new ArrayList<>();
    }

    public List<TViewCell> getAllViewCells() {
        return new ArrayList<>(viewCellsToRender);
    }

    public List<TModel> getAllModels() {
        List<TModel> models = new ArrayList<>();

        for (TViewCell viewCell: viewCellsToRender) {
            models.add(viewCell.getModel());
        }

        return models;
    }

    public Iterable<TModel> modelIterator() {
        return new Iterable<TModel>() {
            @Override
            public Iterator<TModel> iterator() {
                return getAllModels().iterator();
            }
        };
    }

    public void add(TModel model) {
        this.viewCells.add(viewCellFactory.createViewCell(model));
        prepareViewCellsToRender();
    }

    public void addAll(Collection<? extends TModel> models) {
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
        prepareViewCellsToRender();
    }

    public void setAll(Collection<? extends TModel> models) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
        prepareViewCellsToRender();
    }

    public void prependAll(Collection<? extends TModel> models) {
        List<TViewCell> newList = new ArrayList<>();
        newList.addAll(viewCellFactory.createAllViewCells(models));
        newList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(newList);

        prepareViewCellsToRender();
    }

    public void clear() {
        viewCells.clear();
        prepareViewCellsToRender();
    }

    private void prepareViewCellsToRender() {
        if (isFilteringEnabled()) {
            filterViewCells();
        } else {
            viewCellsToRender.clear();
            viewCellsToRender.addAll(viewCells);
        }

        if (isSortingEnabled()) {
            sortViewCells();
        }
    }

    public HomogeneousSection<TModel, TViewCell> setModelComparator(Comparator<? super TModel> modelComparator) {
        this.modelComparator = modelComparator;
        prepareViewCellsToRender();

        return this;
    }

    public boolean isSortingEnabled() {
        return modelComparator != null;
    }

    private void sortViewCells() {
        Collections.sort(viewCellsToRender, new Comparator<TViewCell>() {
            @Override
            public int compare(TViewCell viewCell1, TViewCell viewCell2) {
                return modelComparator.compare(viewCell1.getModel(), viewCell2.getModel());
            }
        });
    }

    public HomogeneousSection<TModel, TViewCell>  setFilterFunction(Func<? super TModel, ? extends Boolean> filterFunction) {
        this.filterFunction = filterFunction;
        prepareViewCellsToRender();

        return this;
    }

    public boolean isFilteringEnabled() {
        return filterFunction != null;
    }

    public boolean shouldDisplayViewCell(TViewCell viewCell) {
        return filterFunction.call(viewCell.getModel());
    }

    private void filterViewCells() {
        viewCellsToRender.clear();

        for (TViewCell viewCell: viewCells) {
            if (shouldDisplayViewCell(viewCell)) {
                viewCellsToRender.add(viewCell);
            }
        }
    }

    @Override
    public AbstractViewCell get(int position) {
        return viewCellsToRender.get(position);
    }

    @Override
    public void remove(int position) {
        TViewCell viewCellToRemove = viewCellsToRender.get(position);
        viewCells.remove(viewCellToRemove);

        prepareViewCellsToRender();
    }

    @Override
    public int getItemCount() {
        return viewCellsToRender.size();
    }
}
