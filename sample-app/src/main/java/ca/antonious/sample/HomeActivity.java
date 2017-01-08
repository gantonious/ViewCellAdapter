package ca.antonious.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.models.Sample;
import ca.antonious.sample.viewcells.SampleViewCell;
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
        ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
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

        return viewCellAdapter;
    }

    private List<Sample> getSamples() {
        List<Sample> samples = new ArrayList<>();
        samples.add(getBasicHomogeneousSample());
        samples.add(getSortingHomogeneousSample());
        samples.add(getFilteredHomogeneousSample());
        samples.add(getMultipleSectionsSample());
        samples.add(getComplexSample());
        samples.add(getSelecionSample());

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

    private Sample getSelecionSample() {
        return new Sample.Builder()
                .setTitle(getString(R.string.selection_example_title))
                .setDescription(getString(R.string.selection_example_description))
                .setShowcaseActivityClass(null)
                .build();
    }
}
