package com.example.advancepizza.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;

import org.w3c.dom.Text;


public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    public static final String ARG_SECTION_NUMBER = "section_number";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button viewMenu = (Button) view.findViewById(R.id.button_backFromHomeFragment);
        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to pizza menu list:
                //back to pizza Menu list page
                MainActivity.allList.clear();
                PizzaMenuFragment pizzaMenuFragment = new PizzaMenuFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pizzaMenuFragment).commit();
            }
        });
        return view;
    }


}