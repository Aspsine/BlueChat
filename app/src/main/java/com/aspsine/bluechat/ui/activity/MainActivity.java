package com.aspsine.bluechat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aspsine.bluechat.R;
import com.aspsine.bluechat.ui.fragment.ListFragment;


public class MainActivity extends ActionBarActivity
        implements ListFragment.OnItemClickListener, ListFragment.OnItemLongClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarToolbar);
        setSupportActionBar(toolbar);
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
    public void onItemClick(int position, View view) {
        if(view.getId() == R.id.ivAvatar){
            Toast.makeText(this, "id= " + position + " avatar onClick", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "id= " + position + " onClick", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(this, ChatActivity.class));
    }

    @Override
    public void onItemLongClick(int position, View view) {
        Toast.makeText(this, "id= " + position + " onLongClick", Toast.LENGTH_SHORT).show();
    }
}
