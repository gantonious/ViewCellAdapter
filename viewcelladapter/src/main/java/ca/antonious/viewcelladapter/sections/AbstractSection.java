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
    private boolean isVisible;
    private Set<SectionObserver> sectionObservers;

    public AbstractSection() {
        this.isVisible = true;
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

    public final int getItemCount() {
        if (isVisible) {
            return getInternalItemCount();
        }
        return 0;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setVisibile(boolean isVisible) {
        if (this.isVisible != isVisible) {
            this.isVisible = isVisible;
            notifyDataChanged();
        }
    }

    public abstract AbstractViewCell get(int position);
    public abstract int getInternalItemCount();

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
