package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-17.
 */

public class HeaderSectionDecorator extends SectionDecorator {
    private AbstractViewCell headerViewCell;
    private boolean showHeaderIfEmpty = true;

    public HeaderSectionDecorator(AbstractSection decoratedSection, AbstractViewCell headerViewCell) {
        super(decoratedSection);
        this.headerViewCell = headerViewCell;
    }

    @Override
    public AbstractViewCell get(int position) {
        if (position == 0) {
            return headerViewCell;
        }
        return getDecoratedSection().get(position - 1);
    }

    @Override
    public int getInternalItemCount() {
        if (isSectionEmpty()) {
            return 0;
        }
        return getDecoratedSection().getItemCount() + 1;
    }

    public boolean shouldShowHeaderIfEmpty() {
        return showHeaderIfEmpty;
    }

    public void setShowHeaderIfEmpty(boolean showHeaderIfEmpty) {
        this.showHeaderIfEmpty = showHeaderIfEmpty;
        notifyDataChanged();
    }

    private boolean isSectionEmpty() {
        return getDecoratedSection().isEmpty() && !shouldShowHeaderIfEmpty();
    }
}
