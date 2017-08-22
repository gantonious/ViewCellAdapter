package ca.antonious.viewcelladapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.antonious.viewcelladapter.construction.ViewCellAdapterBuilder;
import ca.antonious.viewcelladapter.internal.SectionObserver;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.utils.CollectionUtils;
import ca.antonious.viewcelladapter.utils.ViewCellUtils;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.eventhandling.ListenerBinderHelper;
import ca.antonious.viewcelladapter.viewcells.eventhandling.ListenerCollection;

/**
 * Created by George on 2016-11-15.
 */

public class ViewCellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionObserver {
    private List<AbstractSection> sections;
    private ListenerCollection listenerCollection;
    private Map<Integer, AbstractViewCell> viewCellTypes;
    private boolean shouldUpdateOnSectionChanges;

    public ViewCellAdapter() {
        this.setHasStableIds(true);
        this.sections = new ArrayList<>();
        this.listenerCollection = new ListenerCollection();
        this.viewCellTypes = new HashMap<>();
        this.shouldUpdateOnSectionChanges = true;
    }

    public static ViewCellAdapterBuilder create() {
        return new ViewCellAdapterBuilder();
    }

    public void setShouldUpdateOnSectionChanges(boolean shouldUpdateOnSectionChanges) {
        this.shouldUpdateOnSectionChanges = shouldUpdateOnSectionChanges;
    }

    public void add(AbstractSection section) {
        this.sections.add(section);
        section.addObserver(this);
    }

    public void addAll(Collection<? extends AbstractSection> sections) {
        this.sections.addAll(sections);
        observe(sections);
    }

    public void setAll(Collection<? extends AbstractSection> sections) {
        CollectionUtils.setAll(this.sections, sections);
        observe(sections);
    }

    public void prependAll(Collection<? extends AbstractSection> sections) {
        CollectionUtils.prependAll(this.sections, sections);
        observe(sections);
    }

    public ViewCellAdapter addSection(AbstractSection section) {
        add(section);
        section.addObserver(this);
        return this;
    }

    private void observe(Collection<? extends AbstractSection> sections) {
        for (AbstractSection section: sections) {
            section.addObserver(this);
        }
    }

    @Override
    public void onDataChanged() {
        if (shouldUpdateOnSectionChanges) {
            notifyDataSetChanged();
        }
    }

    public ViewCellAdapter addListener(Object listener) {
        listenerCollection.addListener(listener);
        return this;
    }

    public ViewCellAdapter removeListener(Object listener) {
        listenerCollection.removeListener(listener);
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewCellTypes.get(viewType).createViewHolder(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AbstractViewCell viewCell = ViewCellUtils.getViewCell(sections, position);

        viewCell.unbindViewCell(holder);
        viewCell.bindViewCell(holder);
        ListenerBinderHelper.bindListenersTo(viewCell, holder, listenerCollection);
    }

    @Override
    public int getItemCount() {
        return ViewCellUtils.getTotalCount(sections);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType(int position) {
        AbstractViewCell viewCell = ViewCellUtils.getViewCell(sections, position);

        int viewType = viewCell.getViewType();
        viewCellTypes.put(viewType, viewCell);

        return viewType;
    }

    @Override
    public long getItemId(int position) {
        return ViewCellUtils.getViewCell(sections, position).getItemId();
    }

    public AbstractViewCell get(int position) {
        return ViewCellUtils.getViewCell(sections, position);
    }
}
