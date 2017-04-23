package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Comparator;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-07.
 */

public class SortedHomogeneousSectionSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> sampleModelSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        // create section
        sampleModelSection = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);

        return ViewCellAdapter.create()
            .section(
                SectionBuilder.wrap(sampleModelSection)
                    .withComparator(new Comparator<SampleModel>() {
                        @Override
                        public int compare(SampleModel model1, SampleModel model2) {
                            return model1.getName().compareTo(model2.getName());
                        }
                    })
                    .wrapWithEmptyView(new EmptyViewCell("Press the add button to add items!"))
                    .build()
            )
            .listener(new SampleModelViewCell.OnSampleModelClickListener() {
                @Override
                public void onSampleModelClick(SampleModel sampleModel) {
                    String snackMessage = String.format(Locale.getDefault(), "%s was clicked!", sampleModel.getName());
                    showSnackbar(snackMessage);
                }
            })
            .build();
    }

    private SampleModel generateRandomSampleModel() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), sampleModelSection.getItemCount());
        return new SampleModel(sampleModelName);
    }


    private void addSampleModel() {
        sampleModelSection.add(generateRandomSampleModel());
        viewCellAdapter.notifyDataSetChanged();
    }

    private void removeSampleModel() {
        if (!sampleModelSection.isEmpty()) {
            sampleModelSection.remove(0);
            viewCellAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sorted_sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addSampleModel();
                return true;
            case R.id.action_remove:
                removeSampleModel();
                return true;
            case R.id.action_clear:
                sampleModelSection.clear();
                viewCellAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
