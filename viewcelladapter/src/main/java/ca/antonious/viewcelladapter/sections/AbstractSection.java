package ca.antonious.viewcelladapter.sections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.antonious.viewcelladapter.internal.SectionObserver;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection {
    private Set<SectionObserver> sectionObservers;

    public AbstractSection() {
        sectionObservers = new HashSet<>();
    }

    public void addObserver(SectionObserver observer) {
        sectionObservers.add(observer);
    }

    public void removeObserver(SectionObserver observer) {
        sectionObservers.remove(observer);
    }

    public void notifyDataChanged() {
        for (SectionObserver observer: sectionObservers) {
            observer.onDataChanged();
        }
    }

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

    public abstract AbstractViewCell get(int position);
    public abstract int getItemCount();

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
