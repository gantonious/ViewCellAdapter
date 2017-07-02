package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-07.
 */

public class MultipleSectionsSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> importantSection;
    private HomogeneousSection<SampleModel, SampleModelViewCell> normalSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        // create sections
        importantSection = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);
        normalSection = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);

        return ViewCellAdapter.create()
            .section(
                SectionBuilder.wrap(importantSection)
                    .header(new HeaderViewCell("Important"))
                    .showHeaderIfEmpty()
                    .build()
            )
            .section(
                SectionBuilder.wrap(normalSection)
                    .header(new HeaderViewCell("Normal"))
                    .hideHeaderIfEmpty()
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

    private void addToImportantSection() {
        String sampleModelName = String.format(Locale.getDefault(), "IMPORTANT: %s Test %d", Utils.getRandomLetter(), importantSection.getItemCount());
        importantSection.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
    }

    private void addToNormalSection() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), normalSection.getItemCount());
        normalSection.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.multiple_sections_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_important:
                addToImportantSection();
                return true;
            case R.id.action_add_normal:
                addToNormalSection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
