package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-08.
 */

public class ComplexDecoratorCompositionSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> section1;
    private HomogeneousSection<SampleModel, SampleModelViewCell> section2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        ViewCellAdapter viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        // create sections
        section1 = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);
        section2 = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);

        // decorate section 1 with a header
        HeaderViewCell section1Header = new HeaderViewCell("Section 1");
        HeaderSectionDecorator section1WithHeader = new HeaderSectionDecorator(section1, section1Header);
        section1WithHeader.setShowHeaderIfEmpty(false);

        // decorate section 2 with a header
        HeaderViewCell section2Header = new HeaderViewCell("Section 2");
        HeaderSectionDecorator section2WithHeader = new HeaderSectionDecorator(section2, section2Header);
        section2WithHeader.setShowHeaderIfEmpty(false);

        // combine decorated sections into single composite section
        CompositeSection compositeSection = new CompositeSection()
                .addSection(section1WithHeader)
                .addSection(section2WithHeader);

        // decorate composite section with an empty view
        EmptyViewCell emptyView = new EmptyViewCell("Add items at the top");
        EmptySectionDecorator compositeSectionWithEmptyView = new EmptySectionDecorator(compositeSection, emptyView);

        // add decorated composite section
        viewCellAdapter.add(compositeSectionWithEmptyView);

        // register on sample model clicked listener
        viewCellAdapter.addListener(new SampleModelViewCell.OnSampleModelClickListener() {
            @Override
            public void onSampleModelClick(SampleModel sampleModel) {
                String snackMessage = String.format(Locale.getDefault(), "%s was clicked!", sampleModel.getName());
                showSnackbar(snackMessage);
            }
        });

        return viewCellAdapter;
    }

    private void prependSampleModelToSection1() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), section1.getItemCount());
        section1.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
        viewCellAdapter.notifyDataSetChanged();
    }

    private void clearSection1() {
        section1.clear();
        viewCellAdapter.notifyDataSetChanged();
    }

    private void prependSampleModelToSection2() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), section2.getItemCount());
        section2.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
        viewCellAdapter.notifyDataSetChanged();
    }

    private void clearSection2() {
        section2.clear();
        viewCellAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complex_section_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_section1:
                prependSampleModelToSection1();
                return true;
            case R.id.action_add_section2:
                prependSampleModelToSection2();
                return true;
            case R.id.action_clear_section1:
                clearSection1();
                return true;
            case R.id.action_clear_section2:
                clearSection2();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
