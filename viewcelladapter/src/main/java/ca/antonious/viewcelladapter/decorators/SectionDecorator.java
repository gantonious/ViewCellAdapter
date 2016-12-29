package ca.antonious.viewcelladapter.decorators;

import ca.antonious.viewcelladapter.sections.AbstractSection;

/**
 * Created by George on 2016-12-17.
 */

public abstract class SectionDecorator extends AbstractSection {
    private AbstractSection decoratedSection;

    public SectionDecorator(AbstractSection decoratedSection) {
        this.decoratedSection = decoratedSection;
    }

    public AbstractSection getDecoratedSection() {
        return decoratedSection;
    }
}
