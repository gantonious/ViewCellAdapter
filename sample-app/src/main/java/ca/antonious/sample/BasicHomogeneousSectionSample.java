package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-01.
 */

public class BasicHomogeneousSectionSample extends BaseActivity {
    private static int ITEM_COUNT = 100;

    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> sampleModelSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateSectionWithBaseData();
    }

    private ViewCellAdapter buildAdapter() {
        sampleModelSection = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);

        return ViewCellAdapter.create()
            .section(sampleModelSection)
            .listener(new SampleModelViewCell.OnSampleModelClickListener() {
                @Override
                public void onSampleModelClick(SampleModel sampleModel) {
                    String snackMessage = String.format(Locale.getDefault(), "%s was clicked!", sampleModel.getName());
                    showSnackbar(snackMessage);
                }
            })
            .build();
    }

    private void populateSectionWithBaseData() {
        for (int i = 0; i < ITEM_COUNT; i++) {
            addSampleModel();
        }
    }

    private SampleModel generateRandomSampleModel() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), sampleModelSection.getItemCount());
        return new SampleModel(sampleModelName);
    }

    private void addSampleModel() {
        sampleModelSection.prependAll(Arrays.asList(generateRandomSampleModel()));
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
        getMenuInflater().inflate(R.menu.basic_homogeneous_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
