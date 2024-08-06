package com.example.advancepizza.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.advancepizza.R;


public class NotFoundFragment extends Fragment {

    Button button_cancel;
    boolean isFramAdmin =false;

    public NotFoundFragment() {
        // Required empty public constructor
    }

    public NotFoundFragment(boolean isFramAdmin) {
        this.isFramAdmin = isFramAdmin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_found, container, false);

        button_cancel = view.findViewById(R.id.button_cancelNotFound);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFramAdmin){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new PizzaMenuFragment(true)).commit();

                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PizzaMenuFragment()).commit();
                }

            }
        });
        return  view;

    }
}