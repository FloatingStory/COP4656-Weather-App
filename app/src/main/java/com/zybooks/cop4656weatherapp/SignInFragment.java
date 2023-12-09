package com.zybooks.cop4656weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SignInFragment extends Fragment {
    private NavController navController;
    private Button submitButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        //get activity of this fragment
        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when submit button is clicked, validate data(user and location), move to weather it data is valid
                //else return toast
                navController.navigate(R.id.action_signin_to_weatherdisplay);
                Toast.makeText(requireActivity(), "error stuff", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

//    public void signInClick(View view){
//        Toast.makeText(requireActivity(), "hi", Toast.LENGTH_SHORT).show();
////        navController.navigate(R.id.action_signin_to_weatherdisplay);
//    }
}