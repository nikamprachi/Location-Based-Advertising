package com.example.project.location_advetise;

import android.location.Location;

public class Shop_List {
    String name; //or address
    Double latitude;
    Double longitude;
    String shops;
    Float distance;

    public Shop_List(String _name, double _latitude, double _longitude, String _shops, float _distance) {
        name = _name;
        latitude = _latitude;
        longitude = _longitude;
        shops = _shops;
        distance = _distance;
    }


//    void Station(String _name, Double _latitude, Double _longitude, Integer _shops, Float _distance) {
//
//    }

    void updateDistance(Location _from_location) {
        distance = _from_location.distanceTo(location()) / 1000; // distance in meters convert to km
    }

    Location location() {
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

}
