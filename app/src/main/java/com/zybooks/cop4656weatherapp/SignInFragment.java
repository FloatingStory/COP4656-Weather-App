package com.zybooks.cop4656weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {
    private NavController navController;
    private Button submitButton;
    private EditText username;
    private EditText location;
    SharedPreferences namedSharedPref;

    private String usernameText;
    private String locationText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        //get activity of this fragment
        submitButton = view.findViewById(R.id.submit_button);
        username = view.findViewById(R.id.username_input);
        location = view.findViewById(R.id.location_input);

        //create/get a shared preference(make is so key is the username and value is the location)
        namedSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input from sign in
                usernameText = username.getText().toString();
                locationText = location.getText().toString();
                //when submit button is clicked, validate data(user and location), move to weather it data is valid
                //else return toast
                if(namedSharedPref.contains(usernameText)){
                    //if username already exists in shared preferences
                }
                else{
                    //get location and see if it is a valid one, toast error is username is empty or location is invalid
                    Toast.makeText(requireActivity(), usernameText, Toast.LENGTH_SHORT).show();
                }
                navController.navigate(R.id.action_signin_to_weatherdisplay);   //move from sign in to weather display
            }
        });

        return view;
    }
}