package com.zybooks.cop4656weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class WeatherFragment extends Fragment {
    String username;
    String location;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_weather, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            //get data from sign in
            username = bundle.getString("user");
            location = bundle.getString("location");
            Toast.makeText(requireActivity(), username+" "+location, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(requireActivity(), "Error: Issue with passing data", Toast.LENGTH_SHORT).show();
        }
        return parentView;
    }

//    public void loadWeatherConditionIcon(){
//
//    }

//    public void loadWeatherInfo(){}

}