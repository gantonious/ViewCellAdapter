package ca.antonious.viewcelladapter.construction;

import android.support.v7.widget.RecyclerView;

import ca.antonious.viewcelladapter.decorators.ItemDividerSectionDecorator;
import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.decorators.FooterSectionDecorator;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
import ca.antonious.viewcelladapter.decorators.SectionDecorator;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.AdapterWrapperSection;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;
import ca.antonious.viewcelladapter.viewcells.builtins.MaterialDividerViewCell;

/**
 * Created by George on 2017-04-22.
 */

public class SectionBuilder<TSection extends AbstractSection> {
    public TSection section;

    public SectionBuilder(TSection section) {
        this.section = section;
    }

    public TSection getSection() {
        return section;
    }

    public TSection build() {
        return getSection();
    }

    public static <TSection extends AbstractSection> SectionBuilder<TSection> wrap(TSection section) {
        return new SectionBuilder<>(section);
    }
    
    public static <TModel, TViewCell extends GenericViewCell<?, TModel>> HomogeneousSectionBuilder<TModel, TViewCell> wrap(HomogeneousSection<TModel, TViewCell> section) {
        return new HomogeneousSectionBuilder<>(section);
    }

    public static <TViewHolder extends RecyclerView.ViewHolder> SectionBuilder<AdapterWrapperSection<TViewHolder>> wrap(RecyclerView.Adapter<TViewHolder> adapter) {
        return new SectionBuilder<>(new AdapterWrapperSection<>(adapter));
    }

    public static CompositeSectionBuilder createCompositeSection() {
        return new CompositeSectionBuilder(new CompositeSection());
    }

    public HeaderDecoratorBuilder header(AbstractViewCell headerViewCell) {
        HeaderSectionDecorator decorator = new HeaderSectionDecorator(getSection(), headerViewCell);
        return new HeaderDecoratorBuilder(decorator);
    }

    public FooterDecoratorBuilder footer(AbstractViewCell footerViewCell) {
        FooterSectionDecorator decorator = new FooterSectionDecorator(getSection(), footerViewCell);
        return new FooterDecoratorBuilder(decorator);
    }

    public EmptySectionBuilder showIfEmpty(AbstractViewCell emptyViewCell) {
        EmptySectionDecorator decorator = new EmptySectionDecorator(getSection(), emptyViewCell);
        return new EmptySectionBuilder(decorator);
    }

    public ItemDividerBuilder separateWithDividers() {
        return separateWithDividers(new Function<AbstractViewCell, AbstractViewCell>() {
            @Override
            public AbstractViewCell apply(AbstractViewCell viewCellBeforeDivider) {
                return new MaterialDividerViewCell(viewCellBeforeDivider.getItemId());
            }
        });
    }

    public ItemDividerBuilder separateWithDividers(Function<? super AbstractViewCell, ? extends AbstractViewCell> viewCellFactory) {
        ItemDividerSectionDecorator decorator = new ItemDividerSectionDecorator(getSection(), viewCellFactory);
        return new ItemDividerBuilder(decorator);
    }

    public <TDecorator extends SectionDecorator> SectionBuilder<TDecorator> decorateWith(Function<? super AbstractSection, ? extends TDecorator> decoratorFactory) {
        return new SectionBuilder<>(decoratorFactory.apply(getSection()));
    }

}
