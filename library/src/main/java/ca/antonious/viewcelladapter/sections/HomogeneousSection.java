package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCellFactory;

/**
 * Created by George on 2016-12-19.
 */

public class HomogeneousSection<TModel, TViewCell extends GenericViewCell<?, TModel>> extends AbstractSection {
    private GenericViewCellFactory<TModel, TViewCell> viewCellFactory;
    private List<TViewCell> viewCells;

    public HomogeneousSection(GenericViewCellFactory<TModel, TViewCell> viewCellFactory) {
        this.viewCellFactory = viewCellFactory;
        this.viewCells = new ArrayList<>();
    }

    public void add(TModel model) {
        this.viewCells.add(viewCellFactory.createViewCell(model));
    }

    public void addAll(Collection<? extends TModel> models) {
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
    }

    public void setAll(Collection<? extends TModel> models) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCellFactory.createAllViewCells(models));
    }

    public void prependAll(Collection<? extends TModel> models) {
        List<TViewCell> newList = new ArrayList<>();
        newList.addAll(viewCellFactory.createAllViewCells(models));
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
