package com.zybooks.cop4656weatherapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {
    String username;
    String mCity;
    String mCountry;

    private final String url = "https://api.openweathermap.org/data/2.5/weather?lat=";
    private final String apiKey ="f6c78d88533411d54d5d5a49c06dd258";
    private final String locUrl = "https://api.openweathermap.org/geo/1.0/direct?q=";
    DecimalFormat df = new DecimalFormat("#.##");
    TextView tempTv;
    TextView descriptionTv;
    private double latitude;
    private double longitude;
    View parentView;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_weather, container, false);

        context = parentView.getContext();


        Bundle bundle = getArguments();
        if (bundle != null) {
            //get data from sign in
            username = bundle.getString("user");
            mCity = bundle.getString("city");
            mCountry = bundle.getString("country");
            Toast.makeText(requireActivity(), username+" "+mCity, Toast.LENGTH_SHORT).show();
            tempTv = parentView.findViewById(R.id.currentTemperatureId);
            descriptionTv = parentView.findViewById(R.id.currentWeatherConditionId);
            getWeatherDetails(parentView);

        }
        else{
            Toast.makeText(requireActivity(), "Error: Issue with passing data", Toast.LENGTH_SHORT).show();
        }
        return parentView;
    }

    public void getWeatherDetails(View view){
        String tempUrl = "";
        String city = mCity;
        String country = mCountry;


        //get latitude and longitude to use for data
        StringRequest latRequest = new StringRequest(Request.Method.GET, locUrl+mCity +"&limit=1&appid="+apiKey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONArray JSONresponse = new JSONArray(response);

                    latitude = JSONresponse.getJSONObject(0).getDouble("lat");
                    longitude= JSONresponse.getJSONObject(0).getDouble("lon");
                    //Log.d("response", Double.toString(latitude));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();

            }
        });
        //Append url with latitude and longitude
        if(!city.equals("")){
            tempUrl = url + latitude +"&lon=" + longitude + "&appid=" + apiKey;
        }else{
            tempUrl = url + "?q" + city +"&appid=" + apiKey;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                String output = "";
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description =jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    temp = temp * 1.8 + 32;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonClouds.getString("all");
                    JSONObject jsonWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String cityName = jsonResponse.getString("name");
                    output += "Current weather of " + cityName
                            + "\n Feels like: " + df.format(feelsLike) + " degF"
                            + "\n Humidity: " + humidity + "%"
                            + "\n Description: " + description
                            + "\n Wind Speed: " + wind + "m/s (meters per second)"
                            + "\n Cloudiness: " + clouds + "%"
                            + "\n Pressure: " + pressure + "bpa";
                    tempTv.setText(df.format(temp));
                    descriptionTv.setText(output);
                    Log.d("HEYO","change added");

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(latRequest);
        requestQueue.add(stringRequest);
    }

//    public void loadWeatherInfo(){}

}