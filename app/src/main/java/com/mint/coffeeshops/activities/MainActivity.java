package com.mint.coffeeshops.activities;

import android.app.SearchManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mint.coffeeshops.R;
import com.mint.coffeeshops.adapters.CoffeeShopRecyclerAdapter;
import com.mint.coffeeshops.controller.MainController;
import com.mint.coffeeshops.controller.MainControllerImpl;
import com.mint.coffeeshops.model.CoffeeShop;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    private List<CoffeeShop> coffeeShops;
    private RecyclerView.Adapter coffeeShopRecyclerAdapter;
    private MainController mainController;

    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coffeeShops = new ArrayList<>();
        mainController = new MainControllerImpl();

        buildGoogleApiClient();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.venues_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(linearLayoutManager);
        coffeeShopRecyclerAdapter = new CoffeeShopRecyclerAdapter(coffeeShops, MainActivity.this);
        mRecyclerView.setAdapter(coffeeShopRecyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String queryString) {

                mainController.getVenuesData(queryString, coffeeShops, coffeeShopRecyclerAdapter, MainActivity.this);

                searchView.clearFocus();
                searchMenuItem.collapseActionView();
                searchView.setQuery("", false);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                return false;
            }
        });
        return true;
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            mainController.getVenuesData(mLastLocation.getLatitude(), mLastLocation.getLongitude(), coffeeShops, coffeeShopRecyclerAdapter, MainActivity.this); //initialize the list in an arbitrary location
        } else {
            Toast.makeText(this, R.string.app_name + " " + mLastLocation.getLatitude() + "  " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}