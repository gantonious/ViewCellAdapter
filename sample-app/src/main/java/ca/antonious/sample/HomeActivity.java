package ca.antonious.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.about.AboutActivity;
import ca.antonious.sample.models.Sample;
import ca.antonious.sample.viewcells.SampleViewCell;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.ViewCellAdapter;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setAdapter(buildAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        HomogeneousSection<Sample, SampleViewCell> samplesSection =
                new HomogeneousSection<>(Sample.class, SampleViewCell.class);

        samplesSection.addAll(getSamples());

        return ViewCellAdapter.create()
            .section(
                SectionBuilder.wrap(samplesSection)
                    .sepearteWithDividers()
                    .build()
            )
            .listener(new SampleViewCell.OnSampleClickListener() {
                @Override
                public void onSampleClicked(Sample sample) {
                    startActivity(new Intent(HomeActivity.this, sample.getShowcaseActivityClass()));
                }
            })
            .build();
    }

    private List<Sample> getSamples() {
        List<Sample> samples = new ArrayList<>();
        samples.add(getBasicHomogeneousSample());
        samples.add(getSortingHomogeneousSample());
        samples.add(getFilteredHomogeneousSample());
        samples.add(getMultipleSectionsSample());
        samples.add(getComplexSample());
        samples.add(getSelectionSample());
        samples.add(getHeterogeneousSample());

        return samples;
    }

    private Sample getBasicHomogeneousSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.basic_homogeneous_example_title))
                .setDescription(getString(R.string.basic_homogeneous_example_description))
                .setShowcaseActivityClass(BasicHomogeneousSectionSample.class)
                .build();
    }

    private Sample getSortingHomogeneousSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.sorted_homogeneous_example_title))
                .setDescription(getString(R.string.sorted_homogeneous_example_description))
                .setShowcaseActivityClass(SortedHomogeneousSectionSample.class)
                .build();
    }

    private Sample getFilteredHomogeneousSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.filtered_homogeneous_example_title))
                .setDescription(getString(R.string.filtered_homogeneous_example_description))
                .setShowcaseActivityClass(FilteredHomogeneousSectionSample.class)
                .build();
    }

    private Sample getMultipleSectionsSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.multiple_sections_example_title))
                .setDescription(getString(R.string.multiple_sections_example_description))
                .setShowcaseActivityClass(MultipleSectionsSample.class)
                .build();
    }

    private Sample getComplexSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.complex_example_title))
                .setDescription(getString(R.string.complex_example_description))
                .setShowcaseActivityClass(ComplexDecoratorCompositionSample.class)
                .build();
    }

    private Sample getSelectionSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.selection_example_title))
                .setDescription(getString(R.string.selection_example_description))
                .setShowcaseActivityClass(SelectionSample.class)
                .build();
    }

    private Sample getHeterogeneousSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.heterogeneous_example_title))
                .setDescription(getString(R.string.heterogeneous_example_description))
                .setShowcaseActivityClass(HeterogeneousSample.class)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
