package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 2016-12-17.
 */

public class CompositeSection extends AbstractSection {
    private List<AbstractSection> sections;

    public CompositeSection() {
        this.sections = new ArrayList<>();
    }

    @Override
    public AbstractViewCell get(int position) {
        return ViewCellUtils.getViewCell(sections, position);
    }

    @Override
    public void remove(int position) {
        int sectionIndex = ViewCellUtils.getSectionIndex(sections, position);
        int viewCellIndex = ViewCellUtils.getViewCellIndex(sections, position);

        sections.get(sectionIndex).remove(viewCellIndex);
    }

    @Override
    public int getItemCount() {
        return ViewCellUtils.getTotalCount(sections);
    }

    public CompositeSection addSection(AbstractSection section) {
        sections.add(section);
        return this;
    }

    public CompositeSection removeSection(AbstractSection section) {
        sections.remove(section);
        return this;
    }
}
