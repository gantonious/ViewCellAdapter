package ca.antonious.viewcelladapter.sections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.utils.CollectionUtils;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2016-12-19.
 */

public class HomogeneousSection<TModel, TViewCell extends GenericViewCell<?, TModel>> extends AbstractSection {
    private List<TViewCell> viewCells;
    private List<TViewCell> viewCellsToRender;
    
    private Comparator<? super TModel> modelComparator;
    private Function<? super TModel, ? extends Boolean> filterFunction;
    private Function<? super TModel, ? extends TViewCell> viewCellFactory;

    public HomogeneousSection(Class<? extends TModel> modelClass, Class<? extends TViewCell> viewCellClass) {
        this(new ReflectionBasedViewCellFactory<>(modelClass, viewCellClass));
    }

    public HomogeneousSection(Function<? super TModel, ? extends TViewCell> viewCellFactory) {
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

    public List<TModel> getAllSelectedModels() {
        List<TModel> selectedModels = new ArrayList<>();

        for (TViewCell viewCell: viewCellsToRender) {
            if (viewCell.isSelected()) {
                selectedModels.add(viewCell.getModel());
            }
        }

        return selectedModels;
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
        this.viewCells.add(viewCellFactory.apply(model));
        invalidateData();
    }

    public void addAll(Collection<? extends TModel> models) {
        this.viewCells.addAll(convertToViewCells(models));
        invalidateData();
    }

    public void setAll(Collection<? extends TModel> models) {
        CollectionUtils.setAll(this.viewCells, convertToViewCells(models));
        invalidateData();
    }

    public void prependAll(Collection<? extends TModel> models) {
        CollectionUtils.prependAll(this.viewCells, convertToViewCells(models));
        invalidateData();
    }

    public void clear() {
        viewCells.clear();
        invalidateData();
    }

    private List<TViewCell> convertToViewCells(Collection<? extends TModel> models) {
        List<TViewCell> output = new ArrayList<>();

        for (TModel model: models) {
            output.add(viewCellFactory.apply(model));
        }

        return output;
    }

    private void invalidateData() {
        prepareViewCellsToRender();
        notifyDataChanged();
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
        invalidateData();

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

    public HomogeneousSection<TModel, TViewCell> setFilterFunction(Function<? super TModel, ? extends Boolean> filterFunction) {
        this.filterFunction = filterFunction;
        invalidateData();

        return this;
    }

    public boolean isFilteringEnabled() {
        return filterFunction != null;
    }

    public boolean shouldDisplayViewCell(TViewCell viewCell) {
        return filterFunction.apply(viewCell.getModel());
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

        invalidateData();
    }

    @Override
    public int getItemCount() {
        return viewCellsToRender.size();
    }

    public static class ReflectionBasedViewCellFactory<TModel, TViewCell> implements Function<TModel, TViewCell> {
        private Class<? extends TModel> modelClass;
        private Class<? extends TViewCell> viewCellClass;

        public ReflectionBasedViewCellFactory(Class<? extends TModel> modelClass, Class<? extends TViewCell> viewCellClass) {
            this.modelClass = modelClass;
            this.viewCellClass = viewCellClass;
        }

        @Override
        public TViewCell apply(TModel model) {
            try {
                Constructor<? extends TViewCell> viewCellConstructor = viewCellClass.getConstructor(modelClass);
                return viewCellConstructor.newInstance(model);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
