package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.builtins.MaterialDividerViewCell;

/**
 * Created by George on 2017-04-26.
 */

public class ItemDividerSectionDecorator extends SectionDecorator {
    public ItemDividerSectionDecorator(AbstractSection decoratedSection) {
        super(decoratedSection);
    }

    @Override
    public AbstractViewCell get(int position) {
        if (position % 2 != 0) {
            AbstractViewCell viewCellBeforeDivider = getInnerViewCell(position - 1);
            return new MaterialDividerViewCell(viewCellBeforeDivider.getItemId());
        }
        return getInnerViewCell(position);
    }

    @Override
    public void remove(int position) {
        if (position % 2 == 0) {
            getDecoratedSection().remove(getInnerPosition(position));
        }
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
