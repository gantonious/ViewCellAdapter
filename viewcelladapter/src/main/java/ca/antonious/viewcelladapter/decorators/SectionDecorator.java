package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.internal.SectionObserver;
import ca.antonious.viewcelladapter.sections.AbstractSection;

/**
 * Created by George on 2016-12-17.
 */

public abstract class SectionDecorator extends AbstractSection implements SectionObserver {
    private AbstractSection decoratedSection;

    public SectionDecorator(AbstractSection decoratedSection) {
        this.decoratedSection = decoratedSection;
        this.decoratedSection.addObserver(this);
    }

    public AbstractSection getDecoratedSection() {
        return decoratedSection;
    }

    @Override
    public void onDataChanged() {
        notifyDataChanged();
    }
}
