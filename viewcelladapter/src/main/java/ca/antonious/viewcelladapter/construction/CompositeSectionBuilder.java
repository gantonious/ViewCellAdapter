package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.CompositeSection;

/**
 * Created by George on 2017-04-22.
 */

public class CompositeSectionBuilder extends SectionBuilder<CompositeSection> {
    public CompositeSectionBuilder(CompositeSection section) {
        super(section);
    }

    public CompositeSectionBuilder section(AbstractSection section) {
        getSection().addSection(section);
        return this;
    }
}
