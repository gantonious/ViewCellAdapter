package ca.antonious.viewcelladapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-11-15.
 */

public class ViewCellAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<AbstractSection> sections;
    private ListenerCollection listenerCollection;
    private Map<Integer, Class<? extends BaseViewHolder>> layoutTypes;

    public ViewCellAdapter() {
        this.sections = new ArrayList<>();
        this.listenerCollection = new ListenerCollection();
        this.layoutTypes = new HashMap<>();
    }

    public void add(AbstractSection section) {
        this.sections.add(section);
    }

    public void addAll(Collection<? extends AbstractSection> sections) {
        this.sections.addAll(sections);
    }

    public void setAll(Collection<? extends AbstractSection> sections) {
        this.sections.clear();
        this.sections.addAll(sections);
    }

    public void prependAll(Collection<? extends AbstractSection> sections) {
        List<AbstractSection> newList = new ArrayList<>();
        newList.addAll(sections);
        newList.addAll(this.sections);

        this.sections.clear();
        this.sections.addAll(newList);
    }

    public void addListener(Object listener) {
        listenerCollection.addListener(listener);
    }

    public void removeListener(Object listener) {
        listenerCollection.removeListener(listener);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

            Class<? extends BaseViewHolder> layoutViewHolder = layoutTypes.get(viewType);
            Constructor<? extends BaseViewHolder> viewHolderConstructor = layoutViewHolder.getConstructor(View.class);

            return viewHolderConstructor.newInstance(view);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AbstractViewCell viewCell = ViewCellUtils.getViewCell(sections, position);

        BindListenersHelper.bindListenersTo(viewCell, holder, listenerCollection);
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
        Class<? extends BaseViewHolder> viewHolderClass = viewCell.getViewHolderClass();

        layoutTypes.put(itemId, viewHolderClass);

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
