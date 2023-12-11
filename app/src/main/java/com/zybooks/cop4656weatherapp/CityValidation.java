package com.zybooks.cop4656weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class CityValidation {
    public static boolean isCityValid(Context context, String cityName) {
        Geocoder geocoder = new Geocoder(context);

        try {
            //use getFromLocationName to block thread and get results
            List<Address> address = geocoder.getFromLocationName(cityName, 1);
            //cityName: human-readable address or place to geocode, will return locations that correspond to the give cityName
            //if there is at least one address returned, then the city is valid
            Log.d("address gotten", cityName+": "+address.toString());
            //check what is returned
            if (address != null && address.size() > 0) {
                //city name valid
                return true;
            } else {
                //no city name
                return false;
            }

        } catch (IOException e) {
            Log.d("ERROR city", cityName);
            return false;
        }
    }
}
