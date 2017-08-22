package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2017-01-01.
 */

public class FooterSectionDecorator extends SectionDecorator {
    private AbstractViewCell footerViewCell;
    private boolean showFooterIfEmpty = true;
    private boolean isFooterVisible = true;

    public FooterSectionDecorator(AbstractSection decoratedSection, AbstractViewCell footerViewCell) {
        super(decoratedSection);
        this.footerViewCell = footerViewCell;
    }

    @Override
    public AbstractViewCell get(int position) {
        if (position == getDecoratedSection().getItemCount()) {
            return footerViewCell;
        }
        return getDecoratedSection().get(position);
    }

    @Override
    public int getInternalItemCount() {
        if (isSectionEmpty()) {
            return 0;
        } else if (isFooterVisible) {
            return getDecoratedSection().getItemCount() + 1;
        }
        return getDecoratedSection().getItemCount();
    }

    public void setShowFooterIfEmpty(boolean showFooterIfEmpty) {
        this.showFooterIfEmpty = showFooterIfEmpty;
        notifyDataChanged();
    }

    public void setIsFooterVisible(boolean isFooterVisible) {
        this.isFooterVisible = isFooterVisible;
        notifyDataChanged();
    }

    private boolean isSectionEmpty() {
        return getDecoratedSection().isEmpty() && !showFooterIfEmpty;
    }
}
