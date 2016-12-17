package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.AbstractSection;
import ca.antonious.viewcelladapter.AbstractViewCell;
import ca.antonious.viewcelladapter.decorators.SectionDecorator;

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
    public void remove(int position) {
        getDecoratedSection().remove(position - 1);
    }

    @Override
    public int getItemCount() {
        if (isSectionEmpty()) {
            return 0;
        }
        return getDecoratedSection().getItemCount();
    }

    public boolean shouldShowHeaderIfEmpty() {
        return showHeaderIfEmpty;
    }

    public void setShowHeaderIfEmpty(boolean showHeaderIfEmpty) {
        this.showHeaderIfEmpty = showHeaderIfEmpty;
    }

    private boolean isSectionEmpty() {
        int count = getDecoratedSection().getItemCount();
        return count == 0 || (count == 1 && !showHeaderIfEmpty);
    }
}
