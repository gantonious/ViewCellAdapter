package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import ca.antonious.sample.models.Task;
import ca.antonious.sample.viewcells.TaskViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-01.
 */

public class BasicHomogeneousSectionSample extends BaseActivity {
    private HomogeneousSection<Task, TaskViewCell> tasksSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setAdapter(buildAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        tasksSection = new HomogeneousSection<>(Task.class, TaskViewCell.class);
        viewCellAdapter.add(tasksSection);

        return viewCellAdapter;
    }

    private void addTask() {

    }

    private void removeLastTask() {

    }

}
