package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;

/**
 * Created by George on 2017-04-22.
 */

public class EmptySectionBuilder extends SectionBuilder<EmptySectionDecorator> {
    public EmptySectionBuilder(EmptySectionDecorator section) {
        super(section);
    }
}
