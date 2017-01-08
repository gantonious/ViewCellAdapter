package ca.antonious.sample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by George on 2017-01-01.
 */

public class BaseActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    protected MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setHint("Search...");
        searchView.setHintTextColor(0xC0C0C0);
    }

    protected void showSnackbar(String message) {
        showSnackbar(message, Snackbar.LENGTH_SHORT);
    }

    protected void showSnackbar(String message, int duration) {
        Snackbar.make(recyclerView, message, duration).show();
    }

    protected SampleApplication getSampleApplication() {
        return (SampleApplication) getApplication();
    }
}
