package ca.antonious.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ca.antonious.sample.models.Task;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.sample.viewcells.TaskViewCell;
import ca.antonious.viewcelladapter.Func;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCellAdapter viewCellAdapter;

    private HomogeneousSection<Task, TaskViewCell> todaySection;
    private HomogeneousSection<Task, TaskViewCell> allSection;

    int new_item_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
        //populateSection2();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i =0; i< 100; i++) {
                    Task task = new Task("New Task", new_item_id);
                    new_item_id++;
                    todaySection.add(task);
                }
                viewCellAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpRecyclerView() {
        viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        Func<Task, TaskViewCell> taskViewCellFactory = new Func<Task, TaskViewCell>() {
            @Override
            public TaskViewCell call(Task input) {
                return new TaskViewCell(input);
            }
        };

        todaySection = new HomogeneousSection<>(taskViewCellFactory)
                .setFilterFunction(new Func<Task, Boolean>() {
                    @Override
                    public Boolean call(Task input) {
                        return input.timesCompleted > 5;
                    }
                })
                .setModelComparator(new Comparator<Task>() {
                    @Override
                    public int compare(Task task1, Task task2) {
                        return Integer.compare(task1.timesCompleted, task2.timesCompleted);
                    }
                });

        allSection = new HomogeneousSection<>(taskViewCellFactory);

        HeaderSectionDecorator todayWithHeader = new HeaderSectionDecorator(todaySection, new HeaderViewCell("Today's Tasks"));
        todayWithHeader.setShowHeaderIfEmpty(false);

        HeaderSectionDecorator allWithHeader = new HeaderSectionDecorator(allSection, new HeaderViewCell("All tasks"));
        allWithHeader.setShowHeaderIfEmpty(false);

        CompositeSection compositeSection = new CompositeSection()
                .addSection(todayWithHeader)
                .addSection(allWithHeader);

        EmptySectionDecorator allWithEmpty = new EmptySectionDecorator(compositeSection, new EmptyViewCell("EMPTY"));

        viewCellAdapter.add(todaySection);
        viewCellAdapter.addListener(new TaskViewCell.OnTaskClickListener() {
            @Override
            public void onTaskClicked(Task task) {
                String message = String.format("%s, Completed %d time(s)", task.name, task.timesCompleted);
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        viewCellAdapter.addListener(new TaskViewCell.OnTaskCompletedListener() {
            @Override
            public void onTaskCompleted(Task task) {
                String message = String.format("You completed %s!", task.name);
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void populateSection2() {
        List<Task> tasks = Arrays.asList(new Task("Write an app", 2),
                                         new Task("Buy a cat", 0));

        allSection.addAll(tasks);
        viewCellAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
