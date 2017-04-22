package ca.antonious.viewcelladapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ViewCellAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<AbstractSection> sections;
    private ListenerCollection listenerCollection;
    private Map<Integer, Function<View, BaseViewHolder>> viewHolderFactories;

    public ViewCellAdapter() {
        this.setHasStableIds(true);
        this.sections = new ArrayList<>();
        this.listenerCollection = new ListenerCollection();
        this.viewHolderFactories = new HashMap<>();
    }

    public void add(AbstractSection section) {
        this.sections.add(section);
    }

    public void addAll(Collection<? extends AbstractSection> sections) {
        this.sections.addAll(sections);
    }

    public void setAll(Collection<? extends AbstractSection> sections) {
        CollectionUtils.setAll(this.sections, sections);
    }

    public void prependAll(Collection<? extends AbstractSection> sections) {
        CollectionUtils.prependAll(this.sections, sections);
    }

    public ViewCellAdapter addSection(AbstractSection section) {
        add(section);
        return this;
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
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return viewHolderFactories.get(viewType).apply(view);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AbstractViewCell viewCell = ViewCellUtils.getViewCell(sections, position);

        ListenerBinderHelper.bindListenersTo(viewCell, holder, listenerCollection);
        viewCell.bindViewCell(holder);
    }

    @Override
    public int getItemCount() {
        return ViewCellUtils.getTotalCount(sections);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getItemViewType(int position) {
        AbstractViewCell viewCell = ViewCellUtils.getViewCell(sections, position);

        int itemId = viewCell.getLayoutId();
        Function<View, BaseViewHolder> viewHolderFactory = viewCell.getViewHolderFactory();

        viewHolderFactories.put(itemId, viewHolderFactory);

        return itemId;
    }

    @Override
    public long getItemId(int position) {
        return ViewCellUtils.getViewCell(sections, position).getItemId();
    }

    public AbstractViewCell get(int position) {
        return ViewCellUtils.getViewCell(sections, position);
    }

    public void remove(int position) {
        int sectionIndex = ViewCellUtils.getSectionIndex(sections, position);
        int viewCellIndex = ViewCellUtils.getViewCellIndex(sections, position);

        sections.get(sectionIndex).remove(viewCellIndex);
    }
}
