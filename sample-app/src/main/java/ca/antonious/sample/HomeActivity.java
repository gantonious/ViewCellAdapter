package ca.antonious.sample;

import android.content.Intent;
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
import java.util.Comparator;
import java.util.List;

import ca.antonious.sample.models.Sample;
import ca.antonious.sample.models.Task;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.sample.viewcells.SampleViewCell;
import ca.antonious.sample.viewcells.TaskViewCell;
import ca.antonious.viewcelladapter.Function;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCellAdapter viewCellAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
    }

    private List<Sample> getSamples() {
        List<Sample> samples = new ArrayList<>();
        samples.add(new Sample("Homogeneous Section Sample", HomogeneousSectionSample.class));

        return samples;
    }

    private void setUpRecyclerView() {
        viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        HomogeneousSection<Sample, SampleViewCell> samplesSection =
                new HomogeneousSection<>(Sample.class, SampleViewCell.class);

        samplesSection.addAll(getSamples());

        viewCellAdapter.add(samplesSection);

        viewCellAdapter.addListener(new SampleViewCell.OnFeatureClickListener() {
            @Override
            public void onFeatureClicked(Sample sample) {
                startActivity(new Intent(HomeActivity.this, sample.getShowcaseActivityClass()));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
