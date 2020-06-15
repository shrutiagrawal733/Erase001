package com.example.erase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Place extends AppCompatActivity {
    public String name;
    public LatLng latLng;

    public Place(String s, LatLng latLng) {
        this.name = s;
        this.latLng = latLng;
    }
    public Place(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent i = getIntent();
        final Double lat, lon;
        lat = Double.valueOf(i.getStringExtra("lat"));
        lon = Double.valueOf(i.getStringExtra("long"));

        LatLng myLocation = new LatLng(lat, lon);
        final ArrayList<Place> place = new ArrayList<>();
        place.add(new Place("Place 1", new LatLng(90.0, 90.0)));
        place.add(new Place("Place 2", new LatLng(93.0, 93.0)));
        place.add(new Place("Place 3", new LatLng(83.0, 92.0)));
        place.add(new Place("Place 4", new LatLng(93.0, 91.0)));

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
}

