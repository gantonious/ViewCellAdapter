package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;

/**
 * Created by George on 2017-04-22.
 */

public class HeaderDecoratorBuilder extends SectionBuilder<HeaderSectionDecorator> {
    public HeaderDecoratorBuilder(HeaderSectionDecorator section) {
        super(section);
    }

    public HeaderDecoratorBuilder ifEmptyShowHeader() {
        getSection().setShowHeaderIfEmpty(true);
        return this;
    }

    public HeaderDecoratorBuilder ifEmptyHideHeader() {
        getSection().setShowHeaderIfEmpty(false);
        return this;
    }
}
