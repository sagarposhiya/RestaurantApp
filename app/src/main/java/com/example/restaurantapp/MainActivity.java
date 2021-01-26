package com.example.restaurantapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.API.APIClient;
import com.example.restaurantapp.API.ApiInterface;
import com.example.restaurantapp.adapters.RestaurantAdapter;
import com.example.restaurantapp.pojos.Restaurant;
import com.example.restaurantapp.pojos.ResturantList;
import com.example.restaurantapp.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @BindView(R.id.edit_search)
    EditText editTextSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.llData)
    LinearLayout llData;
    ApiInterface apiInterface;
    LocationManager locationManager;
    RestaurantAdapter adapter;
    String provider;
    double longitude;
    double latitude;

    List<Restaurant> restaurantList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkLocationPermission();
        getInit();
        getList("");
        setEvents();
    }

    private void setEvents() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyWord;
                if (s.length() == 0){
                    keyWord = "";
                } else {
                    keyWord = String.valueOf(s);
                }
                getList(keyWord);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSION_REQUEST);
        }

        statusCheck();
        getLocation();
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {
            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
            // Get the location from the given provider
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500, 0,  this);

            if (location != null)
                onLocationChanged(location);
            else
                location = locationManager.getLastKnownLocation(provider);
            if (location != null)
                onLocationChanged(location);
            else

                Toast.makeText(getBaseContext(), "Location can't be retrieved",
                        Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getBaseContext(), "No Provider Found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getInit() {
        apiInterface = APIClient.getClient().create(ApiInterface.class);
    }

    private void getList(String keyword) {
        String location = new DecimalFormat("##.####").format(latitude) + "," + new DecimalFormat("##.####").format(longitude);

        Call<ResturantList> call = apiInterface.getList(location,"1000","restaurant",":"+keyword, Constants.KEY);
        call.enqueue(new Callback<ResturantList>() {
            @Override
            public void onResponse(Call<ResturantList> call, Response<ResturantList> response) {
                if (response.isSuccessful()){
                    Log.e("RESPONSE","Successful");
                } else {
                    Log.e("RESPONSE","Not Successful");
                }
                ResturantList list = response.body();
                restaurantList = list.getRestaurantList();
                recyclerView.setHasFixedSize(true);
                adapter = new RestaurantAdapter(MainActivity.this,restaurantList);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);

                if (restaurantList.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    llData.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    llData.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResturantList> call, Throwable t) {
                Log.e("RESPONSE","Error :- " + t.getMessage());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("LOCATION","Longitude :- " + location.getLongitude() + "  Latutude :- " + location.getLatitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQUEST){

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                statusCheck();
                getLocation();
            }
        }
    }
}