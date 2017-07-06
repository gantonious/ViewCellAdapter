package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2017-04-26.
 */

public class ItemDividerSectionDecorator extends SectionDecorator {
    private Function<? super AbstractViewCell, ? extends AbstractViewCell> dividerViewCellFactory;

    public ItemDividerSectionDecorator(AbstractSection decoratedSection,
                                       Function<? super AbstractViewCell, ? extends AbstractViewCell> dividerViewCellFactory) {
        super(decoratedSection);
        this.dividerViewCellFactory = dividerViewCellFactory;
    }

    @Override
    public AbstractViewCell get(int position) {
        if (position % 2 != 0) {
            AbstractViewCell viewCellBeforeDivider = getInnerViewCell(position - 1);
            return dividerViewCellFactory.apply(viewCellBeforeDivider);
        }
        return getInnerViewCell(position);
    }

    @Override
    public int getItemCount() {
        return getDecoratedSection().getItemCount() + getTotalDividers();
    }

    private AbstractViewCell getInnerViewCell(int position) {
        return getDecoratedSection().get(getInnerPosition(position));
    }

    private int getInnerPosition(int position) {
        return position / 2;
    }

    private int getTotalDividers() {
        return Math.max(getDecoratedSection().getItemCount() - 1, 0);
    }
}
