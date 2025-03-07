package com.example.health_pal;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

// Should receive search query, search your data, and display search results

public class SearchableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calorie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Commented out for now, will use with open food facts api

        // Inflate the options menu from XML.
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration.
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity.
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Don't iconify the widget. Expand it by default.

        return true;
    }
}
