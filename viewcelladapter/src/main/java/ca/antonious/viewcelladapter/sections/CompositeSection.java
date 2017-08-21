package ca.antonious.viewcelladapter.sections;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.viewcelladapter.internal.SectionObserver;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.utils.ViewCellUtils;

/**
 * Created by George on 2016-12-17.
 */

public class CompositeSection extends AbstractSection implements SectionObserver {
    private List<AbstractSection> sections;

    public CompositeSection() {
        this.sections = new ArrayList<>();
    }

    @Override
    public AbstractViewCell get(int position) {
        return ViewCellUtils.getViewCell(sections, position);
    }

    @Override
    public int getInternalItemCount() {
        return ViewCellUtils.getTotalCount(sections);
    }

    public CompositeSection addSection(AbstractSection section) {
        section.addObserver(this);
        sections.add(section);
        return this;
    }

    public CompositeSection removeSection(AbstractSection section) {
        section.removeObserver(this);
        sections.remove(section);
        return this;
    }

    @Override
    public void onDataChanged() {
        notifyDataChanged();
    }
}
