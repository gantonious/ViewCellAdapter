package ca.antonious.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by George on 2017-01-01.
 */

public class BaseActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
