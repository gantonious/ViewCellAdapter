package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.models.SelectableModel;
import ca.antonious.sample.viewcells.SelectableModelViewCell;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.sections.Section;

/**
 * Created by George on 2017-01-08.
 */

public class HeterogeneousSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private Section mainSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        // create section
        mainSection = new Section();

        return ViewCellAdapter.create()
            .section(
                SectionBuilder.wrap(mainSection)
                    .showIfEmpty(new EmptyViewCell("Add items at the top"))
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

    private void addSampleModel() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), mainSection.getItemCount());
        SampleModelViewCell viewCell = new SampleModelViewCell(new SampleModel(sampleModelName));

        mainSection.prependAll(Arrays.asList(viewCell));
    }

    private void addSelectableModel() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), mainSection.getItemCount());
        SelectableModelViewCell viewCell = new SelectableModelViewCell(new SelectableModel(sampleModelName));

        mainSection.prependAll(Arrays.asList(viewCell));
    }

    private void showCurrentSelections() {
        String selectionMessage = "Selected: " + TextUtils.join(", ", mainSection.getAllSelected());
        showSnackbar(selectionMessage);
    }


    private void removeFirstViewCell() {
        if (!mainSection.isEmpty()) {
            mainSection.remove(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.heterogeneous_sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_sample_model:
                addSampleModel();
                return true;
            case R.id.action_add_selectable_model:
                addSelectableModel();
                return true;
            case R.id.action_show_selections:
                showCurrentSelections();
                return true;
            case R.id.action_remove:
                removeFirstViewCell();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
