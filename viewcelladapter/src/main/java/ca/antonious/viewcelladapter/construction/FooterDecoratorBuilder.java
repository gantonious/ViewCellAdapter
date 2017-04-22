package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.decorators.FooterSectionDecorator;

/**
 * Created by George on 2017-04-22.
 */

public class FooterDecoratorBuilder extends SectionBuilder<FooterSectionDecorator> {
    public FooterDecoratorBuilder(FooterSectionDecorator section) {
        super(section);
    }

    public FooterDecoratorBuilder showFooterIfEmpty() {
        getSection().setShowFooterIfEmpty(true);
        return this;
    }

    public FooterDecoratorBuilder hideFooterIfEmpty() {
        getSection().setShowFooterIfEmpty(false);
        return this;
    }
}
