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
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
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

        return new ViewCellAdapter()
            .addSection(
                SectionBuilder.wrap(importantSection)
                    .wrapWithHeader(new HeaderViewCell("Important"))
                    .showHeaderIfEmpty()
                    .build()
            )
            .addSection(
                SectionBuilder.wrap(normalSection)
                    .wrapWithHeader(new HeaderViewCell("Normal"))
                    .hideHeaderIfEmpty()
                    .build()
            )
            .addListener(new SampleModelViewCell.OnSampleModelClickListener() {
                @Override
                public void onSampleModelClick(SampleModel sampleModel) {
                    String snackMessage = String.format(Locale.getDefault(), "%s was clicked!", sampleModel.getName());
                    showSnackbar(snackMessage);
                }
            });
    }

    private void addToImportantSection() {
        String sampleModelName = String.format(Locale.getDefault(), "IMPORTANT: %s Test %d", Utils.getRandomLetter(), importantSection.getItemCount());
        importantSection.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
        viewCellAdapter.notifyDataSetChanged();
    }

    private void addToNormalSection() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), normalSection.getItemCount());
        normalSection.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
        viewCellAdapter.notifyDataSetChanged();
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
