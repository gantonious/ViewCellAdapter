package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2017-01-01.
 */

public class FooterSectionDecorator extends SectionDecorator {
    private AbstractViewCell footerViewCell;
    private boolean showFooterIfEmpty = true;

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
    public int getItemCount() {
        if (isSectionEmpty()) {
            return 0;
        }
        return getDecoratedSection().getItemCount() + 1;
    }

    public boolean shouldShowFooterIfEmpty() {
        return showFooterIfEmpty;
    }

    public void setShowFooterIfEmpty(boolean showFooterIfEmpty) {
        this.showFooterIfEmpty = showFooterIfEmpty;
        notifyDataChanged();
    }

    private boolean isSectionEmpty() {
        return getDecoratedSection().isEmpty() && !shouldShowFooterIfEmpty();
    }
}
