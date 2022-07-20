package com.zybooks.thebanddatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
public class ListActivity extends AppCompatActivity
implements ListFragment.OnBandSelectedListener{

    public static final String KEY_BAND_ID = "bandId";
    private int mBandId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mBandId = -1;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if(fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_BAND_ID) != 0
                && getResources().getBoolean(R.bool.twoPanes)) {
            mBandId = savedInstanceState.getInt(KEY_BAND_ID);
            Fragment bandFragment = DetailsFragment.newInstance(mBandId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bandFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mBandId != -1) {
            savedInstanceState.putInt(KEY_BAND_ID, mBandId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                return true;

            case R.id.action_logout:
                // Logout selected
                return true;

            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBandSelected(int bandId){

        mBandId = bandId;

        if (findViewById(R.id.details_fragment_container) == null) {
            // No detail fragment, so must be running on phone
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_BAND_ID, bandId);
            startActivity(intent);
        } else {
            // Running on tablet, so replace previous fragment (if one exists) with a new fragment
            Fragment bandFragment = DetailsFragment.newInstance(bandId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bandFragment)
                    .commit();
        }
    }


}