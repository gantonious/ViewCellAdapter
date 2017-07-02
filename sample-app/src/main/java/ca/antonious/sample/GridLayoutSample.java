package ca.antonious.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Locale;

import ca.antonious.sample.models.SampleModel;
import ca.antonious.sample.viewcells.EmptyViewCell;
import ca.antonious.sample.viewcells.SampleModelViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.extensions.ViewCellGridLayoutManager;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.builtins.MaterialLabelViewCell;

/**
 * Created by George on 2017-05-14.
 */

public class GridLayoutSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<SampleModel, SampleModelViewCell> section1;
    private HomogeneousSection<SampleModel, SampleModelViewCell> section2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(ViewCellGridLayoutManager.create(this, viewCellAdapter, 3));
    }
 
    private ViewCellAdapter buildAdapter() {
        // create sections
        section1 = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);
        section2 = new HomogeneousSection<>(SampleModel.class, SampleModelViewCell.class);

        return ViewCellAdapter.create()
                .section(
                    SectionBuilder.createCompositeSection()
                        .section(
                            SectionBuilder.wrap(section1)
                                    .header(buildHeader("Section 1"))
                                    .hideHeaderIfEmpty()
                                    .build()
                        )
                        .section(
                            SectionBuilder.wrap(section2)
                                    .header(buildHeader("Section 2"))
                                    .hideHeaderIfEmpty()
                                    .build()
                        )
                        .showIfEmpty(new EmptyViewCell("Add items at the top"))
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

    private AbstractViewCell buildHeader(String headerLabel) {
        return MaterialLabelViewCell.create()
                .label(headerLabel)
                .textColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .build();
    }

    private void prependSampleModelToSection1() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), section1.getItemCount());
        section1.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
    }

    private void clearSection1() {
        section1.clear();
    }

    private void prependSampleModelToSection2() {
        String sampleModelName = String.format(Locale.getDefault(), "%s Test %d", Utils.getRandomLetter(), section2.getItemCount());
        section2.prependAll(Arrays.asList(new SampleModel(sampleModelName)));
    }

    private void clearSection2() {
        section2.clear();
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
