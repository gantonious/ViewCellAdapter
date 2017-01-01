package ca.antonious.sample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Comparator;

import ca.antonious.sample.models.Task;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.sample.viewcells.TaskViewCell;
import ca.antonious.viewcelladapter.Function;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

public class HomogeneousSectionSample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homogeneous_section_sample);
    }

    private void setUpRecyclerView() {
//        viewCellAdapter = new ViewCellAdapter();
//        viewCellAdapter.setHasStableIds(true);
//
//        Function<Task, TaskViewCell> taskViewCellFactory = new Function<Task, TaskViewCell>() {
//            @Override
//            public TaskViewCell apply(Task input) {
//                return new TaskViewCell(input);
//            }
//        };
//
//        todaySection = new HomogeneousSection<>(taskViewCellFactory)
//                .setFilterFunction(new Function<Task, Boolean>() {
//                    @Override
//                    public Boolean apply(Task input) {
//                        return input.timesCompleted > 5;
//                    }
//                })
//                .setModelComparator(new Comparator<Task>() {
//                    @Override
//                    public int compare(Task task1, Task task2) {
//                        return Integer.compare(task1.timesCompleted, task2.timesCompleted);
//                    }
//                });
//
//        allSection = new HomogeneousSection<>(taskViewCellFactory);
//
//        HeaderSectionDecorator todayWithHeader = new HeaderSectionDecorator(todaySection, new HeaderViewCell("Today's Tasks"));
//        todayWithHeader.setShowHeaderIfEmpty(false);
//
//        HeaderSectionDecorator allWithHeader = new HeaderSectionDecorator(allSection, new HeaderViewCell("All tasks"));
//        allWithHeader.setShowHeaderIfEmpty(false);
//
//        CompositeSection compositeSection = new CompositeSection()
//                .addSection(todayWithHeader)
//                .addSection(allWithHeader);
//
//        EmptySectionDecorator allWithEmpty = new EmptySectionDecorator(compositeSection, new EmptyViewCell("EMPTY"));
//
//        viewCellAdapter.add(allWithEmpty);
//        viewCellAdapter.addListener(new TaskViewCell.OnTaskClickListener() {
//            @Override
//            public void onTaskClicked(Task task) {
//                String message = String.format("%s, Completed %d time(s)", task.name, task.timesCompleted);
//                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
//                        .show();
//            }
//        });
//
//        viewCellAdapter.addListener(new TaskViewCell.OnTaskCompletedListener() {
//            @Override
//            public void onTaskCompleted(Task task) {
//                String message = String.format("You completed %s!", task.name);
//                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
//                        .show();
//            }
//        });
//
//        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
//        recyclerView.setAdapter(viewCellAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

}
