package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SelectableModel;
import ca.antonious.sample.models.SelectableModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-08.
 */

public class SelectionSample extends BaseActivity {
    private static int ITEM_COUNT = 100;

    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SelectableModel, SelectableModelViewCell> mainSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateSectionWithBaseData();
    }

    private ViewCellAdapter buildAdapter() {
        ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        // create section
        mainSection = new HomogeneousSection<>(SelectableModel.class, SelectableModelViewCell.class);

        // add section to the adapter
        viewCellAdapter.add(mainSection);

        return viewCellAdapter;
    }

    private void populateSectionWithBaseData() {
        for (int i = 0; i < ITEM_COUNT; i++) {
            addSampleModel();
        }
    }

    private SelectableModel generateRandomSampleModel() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), mainSection.getItemCount());
        return new SelectableModel(sampleModelName);
    }

    private void addSampleModel() {
        mainSection.prependAll(Arrays.asList(generateRandomSampleModel()));
        viewCellAdapter.notifyDataSetChanged();
    }

    private void showCurrentSelections() {
        String selectionMessage = "Selected: " + TextUtils.join(", ", mainSection.getAllSelectedModels());
        showSnackbar(selectionMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selection_sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_selections:
                showCurrentSelections();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
