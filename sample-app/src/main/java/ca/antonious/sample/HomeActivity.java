package ca.antonious.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.models.Sample;
import ca.antonious.sample.viewcells.SampleViewCell;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.ViewCellAdapter;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCellAdapter viewCellAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        HomogeneousSection<Sample, SampleViewCell> samplesSection =
                new HomogeneousSection<>(Sample.class, SampleViewCell.class);

        samplesSection.addAll(getSamples());

        viewCellAdapter.add(samplesSection);

        viewCellAdapter.addListener(new SampleViewCell.OnSampleClickListener() {
            @Override
            public void onSampleClicked(Sample sample) {
                startActivity(new Intent(HomeActivity.this, sample.getShowcaseActivityClass()));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Sample> getSamples() {
        List<Sample> samples = new ArrayList<>();
        samples.add(getBasicHomogeneousExample());

        return samples;
    }

    private Sample getBasicHomogeneousExample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.basic_homogeneous_example_title))
                .setDescription(getString(R.string.basic_homogeneous_example_description))
                .setShowcaseActivityClass(HomogeneousSectionSample.class)
                .build();
    }
}
