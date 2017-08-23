package ca.antonious.viewcelladapter.sections;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by george on 2017-08-23.
 */

public class SingletonSection extends AbstractSection {
    private AbstractViewCell viewCell;

    public SingletonSection(AbstractViewCell viewCell) {
        this.viewCell = viewCell;
    }

    @Override
    public AbstractViewCell get(int position) {
        return viewCell;
    }

    @Override
    public int getInternalItemCount() {
        return 1;
    }
}
