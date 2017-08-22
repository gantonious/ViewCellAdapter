package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-17.
 */

public class HeaderSectionDecorator extends SectionDecorator {
    private AbstractViewCell headerViewCell;
    private boolean showHeaderIfEmpty = true;
    private boolean isHeaderVisible = true;

    public HeaderSectionDecorator(AbstractSection decoratedSection, AbstractViewCell headerViewCell) {
        super(decoratedSection);
        this.headerViewCell = headerViewCell;
    }

    @Override
    public AbstractViewCell get(int position) {
        if (position == 0 && isHeaderVisible) {
            return headerViewCell;
        } else if (isHeaderVisible) {
            return getDecoratedSection().get(position - 1);
        }
        return getDecoratedSection().get(position);
    }

    @Override
    public int getInternalItemCount() {
        if (isSectionEmpty()) {
            return 0;
        } else if (isHeaderVisible) {
            return getDecoratedSection().getItemCount() + 1;
        }
        return getDecoratedSection().getItemCount();
    }

    public void setShowHeaderIfEmpty(boolean showHeaderIfEmpty) {
        this.showHeaderIfEmpty = showHeaderIfEmpty;
        notifyDataChanged();
    }

    public void setIsHeaderVisible(boolean isHeaderVisible) {
        this.isHeaderVisible = isHeaderVisible;
        notifyDataChanged();
    }

    private boolean isSectionEmpty() {
        return getDecoratedSection().isEmpty() && !showHeaderIfEmpty;
    }
}
