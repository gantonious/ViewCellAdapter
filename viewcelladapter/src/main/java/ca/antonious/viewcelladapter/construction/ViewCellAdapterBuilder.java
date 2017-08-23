package ca.antonious.viewcelladapter.construction;

import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.SingletonSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2017-04-23.
 */

public class ViewCellAdapterBuilder {
    private ViewCellAdapter viewCellAdapter;

    public ViewCellAdapterBuilder() {
        this.viewCellAdapter = new ViewCellAdapter();
    }

    public ViewCellAdapterBuilder enableUpdateOnDataChanges() {
        viewCellAdapter.setShouldUpdateOnSectionChanges(true);
        return this;
    }

    public ViewCellAdapterBuilder disableUpdateOnDataChanges() {
        viewCellAdapter.setShouldUpdateOnSectionChanges(false);
        return this;
    }

    public ViewCellAdapterBuilder singletonSection(AbstractViewCell viewCell) {
        viewCellAdapter.add(new SingletonSection(viewCell));
        return this;
    }

    public ViewCellAdapterBuilder section(AbstractSection section) {
        viewCellAdapter.add(section);
        return this;
    }

    public ViewCellAdapterBuilder section(SectionBuilder sectionBuilder) {
        viewCellAdapter.add(sectionBuilder.build());
        return this;
    }

    public ViewCellAdapterBuilder listener(Object listener) {
        viewCellAdapter.addListener(listener);
        return this;
    }

    public ViewCellAdapter build() {
        return viewCellAdapter;
    }
}
