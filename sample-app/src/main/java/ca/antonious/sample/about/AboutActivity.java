package ca.antonious.sample.about;

import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.sample.BaseActivity;
import ca.antonious.sample.R;

/**
 * Created by George on 2017-01-08.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public List<Library> getLibraries() {
        List<Library> libraries = new ArrayList<>();

        for (String libraryJson: getLibraryStringArray()) {
            libraries.add(deserializeLibrary(libraryJson));
        }

        return libraries;
    }

    private String[] getLibraryStringArray() {
        try {
            return getResources().getStringArray(R.array.licenses);
        } catch (Exception e) {
            return new String[]{};
        }
    }

    private Library deserializeLibrary(String libraryJson) {
        return new Gson().fromJson(libraryJson, Library.class);
    }
}
