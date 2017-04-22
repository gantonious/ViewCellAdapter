package ca.antonious.viewcelladapter.construction;

import java.util.Comparator;

import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-04-22.
 */

public class HomogeneousSectionBuilder<TModel, TViewCell extends GenericViewCell<?, TModel>> extends SectionBuilder<HomogeneousSection<TModel, TViewCell>> {
    public HomogeneousSectionBuilder(HomogeneousSection<TModel, TViewCell> section) {
        super(section);
    }

    public HomogeneousSectionBuilder<TModel, TViewCell> withFilter(Function<? super TModel, ? extends Boolean> filterFunction) {
        getSection().setFilterFunction(filterFunction);
        return this;
    }

    public HomogeneousSectionBuilder<TModel, TViewCell> withComparator(Comparator<TModel> modelComparator) {
        getSection().setModelComparator(modelComparator);
        return this;
    }
}
