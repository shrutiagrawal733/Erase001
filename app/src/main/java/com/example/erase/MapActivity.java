package com.example.erase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {

    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    LatLng myLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                    makeList();
                                }

                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void makeList()
    {
        final ArrayList<Place> place = new ArrayList<>();

        place.add(new Place("Green Waves Environmental Solutions", new LatLng(17.740490, 83.303220)));
        place.add(new Place("Earth E-waste Management Pvt. Ltd.", new LatLng(23.188360, 77.438640)));
        place.add(new Place("Global E-waste Management Systems", new LatLng(15.269749228727635, 74.00318192603034)));
        place.add(new Place("Green Vortex Waste Management", new LatLng(28.60269846857762, 77.02855962297384)));
        place.add(new Place("Shivalik Solid Waste Management Ltd.", new LatLng(30.689944019228264, 76.83738527891056)));
        place.add(new Place("EcoCentric Management Pvt. Ltd.", new LatLng(19.13511194364509, 72.83710710426045)));
        place.add(new Place("Resource E-Waste Solution Pvt. Ltd.", new LatLng(28.63804333705887, 77.30911927034516)));
        place.add(new Place("Greeniva Recycler Pvt. Ltd.", new LatLng(28.666506602797984, 77.27798926816965)));
        place.add(new Place("Green Era Recyclers", new LatLng(11.04644734361225, 76.91904736790123)));
        place.add(new Place("Greenspace Eco Management Pvt. Ltd.", new LatLng(27.62017572882529, 76.61988682828132)));
        place.add(new Place("Erecon Recycling", new LatLng(19.866533541643236, 75.41133875450596)));
        place.add(new Place("Mahalaxmi E-Recyclers Pvt. Ltd.", new LatLng(18.497420678792118, 73.91517312380284)));

        Collections.sort(place, new SortPlaces(myLocation));

        ArrayList<String> place_name = new ArrayList<>();
        for (Place x : place) {
            place_name.add(x.name);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_place, R.id.textView, place_name);
        ListView listView = findViewById(R.id.place_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LatLng latLng = place.get(i).latLng;
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitude,longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            myLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            makeList();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }


}