package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;

/**
 * Created by George on 2017-04-22.
 */

public class HeaderDecoratorBuilder extends SectionBuilder<HeaderSectionDecorator> {
    public HeaderDecoratorBuilder(HeaderSectionDecorator section) {
        super(section);
    }

    public HeaderDecoratorBuilder showHeaderIfEmpty() {
        getSection().setShowHeaderIfEmpty(true);
        return this;
    }

    public HeaderDecoratorBuilder hideHeaderIfEmpty() {
        getSection().setShowHeaderIfEmpty(false);
        return this;
    }
}
