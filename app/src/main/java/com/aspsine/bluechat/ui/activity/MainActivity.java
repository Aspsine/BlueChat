package com.aspsine.bluechat.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.fragment.ListFragment;


public class MainActivity extends ActionBarActivity implements ListFragment.OnRecyclerViewItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, ListFragment.newInstance("0", "1"), ListFragment.class.getSimpleName())
                    .commit();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerVieItemClick(int position) {
        Toast.makeText(this, "id=" + position, Toast.LENGTH_SHORT).show();
    }
}
