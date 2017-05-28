package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-05.
 */

public class FilteredHomogeneousSectionSample extends BaseActivity {
    private static int ITEM_COUNT = 100;

    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> sampleModelSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeSearchView();
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

    private void initializeSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                updateSearchFilter(newText);
                return true;
            }
        });
    }

    private void updateSearchFilter(final String searchTerm) {
        // update filter predicate when the search term changes
        sampleModelSection.setFilterFunction(new Function<SampleModel, Boolean>() {
            @Override
            public Boolean apply(SampleModel input) {
                return input.getName().toLowerCase().startsWith(searchTerm.toLowerCase());
            }
        });
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtered_sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.showSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
