package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-17.
 */

public class EmptySectionDecorator extends SectionDecorator {
    private AbstractViewCell emptyViewCell;

    public EmptySectionDecorator(AbstractSection decoratedSection, AbstractViewCell emptyViewCell) {
        super(decoratedSection);
        this.emptyViewCell = emptyViewCell;
    }

    @Override
    public AbstractViewCell get(int position) {
        if (getDecoratedSection().isEmpty()) {
            return emptyViewCell;
        }
        return getDecoratedSection().get(position);
    }

    @Override
    public int getInternalItemCount() {
        if (getDecoratedSection().isEmpty()) {
            return 1;
        }
        return getDecoratedSection().getItemCount();
    }
}
