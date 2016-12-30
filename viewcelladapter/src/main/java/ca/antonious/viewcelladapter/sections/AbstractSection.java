package ca.antonious.viewcelladapter.sections;

import java.util.Iterator;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection implements Iterable<AbstractViewCell> {
    public abstract AbstractViewCell get(int position);
    public abstract void remove(int position);
    public abstract int getItemCount();

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @Override
    public Iterator<AbstractViewCell> iterator() {
        return new AbstractSectionIterator(this);
    }

    public static class AbstractSectionIterator implements Iterator<AbstractViewCell> {
        private int currentIndex;
        private AbstractSection section;

        public AbstractSectionIterator(AbstractSection section) {
            this.currentIndex = 0;
            this.section = section;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < section.getItemCount();
        }

        @Override
        public AbstractViewCell next() {
            return section.get(currentIndex++);
        }
    }
}
