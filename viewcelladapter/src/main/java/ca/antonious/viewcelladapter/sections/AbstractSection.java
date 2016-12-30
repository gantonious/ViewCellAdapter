package ca.antonious.viewcelladapter.sections;

import java.util.Iterator;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection {
    public abstract AbstractViewCell get(int position);
    public abstract void remove(int position);
    public abstract int getItemCount();

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public Iterable<AbstractViewCell> viewCellIterator() {
        return new Iterable<AbstractViewCell>() {
            @Override
            public Iterator<AbstractViewCell> iterator() {
                return new AbstractSectionIterator(AbstractSection.this);
            }
        };
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
