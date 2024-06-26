package com.zybooks.cop4656weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {
    private NavController navController;
    private Button submitButton;
    private EditText username;
    private EditText cityEt;
    private TextView userLabel;
    private TextView cityLabel;
    SharedPreferences userSharedPref;
    SharedPreferences.Editor editor;

    private String usernameText;
    private String mCity;
    private boolean isValidCity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment
        submitButton = view.findViewById(R.id.submit_button);
        username = view.findViewById(R.id.username_input);
        cityEt = view.findViewById(R.id.city_input);
        userLabel = view.findViewById(R.id.username);
        cityLabel = view.findViewById(R.id.location);
        isValidCity = true;

        //create/get a shared preference(make is so key is the username and value is the location)
        userSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input from sign in
                usernameText = username.getText().toString();
                mCity = cityEt.getText().toString();
                //when submit button is clicked, validate data(user and location), move to weather it data is valid
                //else return toast

                cityLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                userLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));

                if(userSharedPref.contains(usernameText)){
                    //if username already exists in shared preferences
                    //might be able to do thread here to get values from api
                    //if user input of city is empty check shared preference for saved location(persisted data)
                    if(mCity.isEmpty()) {
                        //get city from shared preference file
                        mCity = userSharedPref.getString(usernameText, "");
                        Bundle bundle = new Bundle();
                        bundle.putString("user", usernameText);
                        bundle.putString("city", mCity);

                        navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_signin_to_weatherdisplay,bundle);
                    }
                    else{
                        //if user input of city is not empty, change persisted data of this user's location to the new one
                        isValidCity = CityValidation.isCityValid(getContext(), mCity);
                        Log.d("CITYSTUFF", String.valueOf(isValidCity));
                        if(isValidCity) {
                            editor = userSharedPref.edit();
                            editor.putString(usernameText, mCity);
                            editor.apply();

                            Bundle bundle = new Bundle();
                            bundle.putString("user", usernameText);
                            bundle.putString("city", mCity);

                            navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_signin_to_weatherdisplay,bundle);
                        }
                        else{
                            //if city is not valid
                            cityLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                            Toast.makeText(requireActivity(),"Error: Invalid City",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{ //if first time using username
                    //get location and see if it is a valid one, toast error is username is empty or location is invalid
                    if(!usernameText.isEmpty()){   //if username if not empty
                        isValidCity = CityValidation.isCityValid(getContext(), mCity);
                        if(isValidCity) {          //if city is valid
                            editor = userSharedPref.edit();
                            editor.putString(usernameText, mCity);
                            editor.apply();

                            //pass user and location data to weather fragment
                            Bundle bundle = new Bundle();
                            bundle.putString("user", usernameText);
                            bundle.putString("city", mCity);

                            navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_signin_to_weatherdisplay, bundle);
                        }
                        else{                   //if city is not valid
                            cityLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                            Toast.makeText(requireActivity(),"Error: Invalid City",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        //error in user input
                        String errorMessage = "Error: Empty username";
                        userLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                        isValidCity = CityValidation.isCityValid(getContext(), mCity);
                        if(mCity.isEmpty() || !isValidCity){
                            errorMessage += "\nError: Invalid City";
                            cityLabel.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                        }
                        Toast.makeText(requireActivity(),errorMessage,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}