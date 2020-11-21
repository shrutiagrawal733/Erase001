package com.example.erase;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    public String name;
    public LatLng latLng;

    public Place(String s, LatLng latLng) {
        this.name = s;
        this.latLng = latLng;
    }

}
