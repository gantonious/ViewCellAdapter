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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.antonious.sample.models.Task;
import ca.antonious.sample.viewcells.TaskViewCell;
import ca.antonious.viewcelladapter.SectionViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCellAdapter viewCellAdapter;

    private SectionViewCell section1;
    private SectionViewCell section2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();

        populateSection2();
        populateSection1();
    }

    private void setUpRecyclerView() {
        viewCellAdapter = new ViewCellAdapter();
        section1 = new SectionViewCell();
        section2 = new SectionViewCell();
        viewCellAdapter.add(section1);
        viewCellAdapter.add(section2);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void populateSection1() {
        List<Task> tasks = Arrays.asList(new Task("Make lib", 0));

        section1.addAll(getTaskViewCells(tasks));
        viewCellAdapter.notifyDataSetChanged();
    }

    private void populateSection2() {
        List<Task> tasks = Arrays.asList(new Task("Make lib better", 2),
                                         new Task("Buy a cat", 0));

        section2.addAll(getTaskViewCells(tasks));
        viewCellAdapter.notifyDataSetChanged();
    }

    private List<TaskViewCell> getTaskViewCells(List<? extends Task> tasks) {
        List<TaskViewCell> viewCells = new ArrayList<>();

        for (Task task: tasks) {
            viewCells.add(new TaskViewCell(task));
        }

        return viewCells;
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
