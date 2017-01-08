package ca.antonious.sample.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.BaseActivity;
import ca.antonious.sample.R;
import ca.antonious.sample.viewcells.HeaderViewCell;
import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;

/**
 * Created by George on 2017-01-08.
 */

public class AboutActivity extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private HomogeneousSection<Library, LibraryViewCell> librariesSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        librariesSection.setAll(getLibraries());
    }

    private ViewCellAdapter buildAdapter() {
        viewCellAdapter = new ViewCellAdapter();
        viewCellAdapter.setHasStableIds(true);

        librariesSection = new HomogeneousSection<>(Library.class, LibraryViewCell.class);

        HeaderViewCell librariesHeader = new HeaderViewCell("Libraries Used");
        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(librariesSection, librariesHeader);
        headerSectionDecorator.setShowHeaderIfEmpty(false);

        viewCellAdapter.add(headerSectionDecorator);

        viewCellAdapter.addListener(new LibraryViewCell.OnLibraryClickListener() {
            @Override
            public void onLibraryClick(Library library) {
                openLibraryLink(library);
            }
        });

        return viewCellAdapter;
    }

    private void openLibraryLink(Library library) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(library.getSourceControlUrl()));
        startActivity(browserIntent);
    }

    public List<Library> getLibraries() {
        List<Library> libraries = new ArrayList<>();

        for (String library: getLibraryStringArray()) {
            libraries.add(deserializeLibrary(library));
        }

        return libraries;
    }

    private String[] getLibraryStringArray() {
        try {
            return getResources().getStringArray(R.array.libraries);
        } catch (Exception e) {
            return new String[]{};
        }
    }

    private Library deserializeLibrary(String serializedLibrary) {
        String[] libraryTokens = serializedLibrary.split("======");
        return new Library(libraryTokens[0].trim(), libraryTokens[1].trim());
    }
}
