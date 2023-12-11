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
                Log.d("Locality above", cityName);
                Address address1 = address.get(0);
                Log.d("Locality above", address1.toString());
                if(address1.getLocality() != null) {
                    String city = address1.getLocality();
                    Log.d("Locality", city);
                    city = city.toUpperCase();
                    cityName = cityName.toUpperCase();
                    Log.d("ADDRESS CITY", city);
                    Log.d("PASSED CITY", cityName);
                    if (city.equals(cityName)) {
                        return true;
                    } else {
                        return false;
                    }
//                //city name valid
//                return true;
                }
            } else {
                //no city name
                return false;
            }

        } catch (IOException e) {
            Log.d("ERROR city", cityName);
            return false;
        }
        return false;
    }
}
