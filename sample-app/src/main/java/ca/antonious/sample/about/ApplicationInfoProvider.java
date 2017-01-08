package ca.antonious.sample.about;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.R;

/**
 * Created by George on 2017-01-08.
 */

public class ApplicationInfoProvider implements IApplicationInfoProvider {
    private Context context;

    public ApplicationInfoProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<Library> getLibraries() {
        List<Library> libraries = new ArrayList<>();

        for (String libraryJson: getLibraryStringArray()) {
            libraries.add(deserializeLibrary(libraryJson));
        }

        return libraries;
    }

    private String[] getLibraryStringArray() {
        try {
            return context.getResources().getStringArray(R.array.licenses);
        } catch (Exception e) {
            return new String[]{};
        }
    }

    private Library deserializeLibrary(String libraryJson) {
        return new Gson().fromJson(libraryJson, Library.class);
    }
}
