package com.example.advancepizza.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    LinearLayout menuLayout ;

    List<PizzaMenu> pizzaMenuList ;

    DataBaseHelper dataBaseHelper;
    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorites, container, false);

        dataBaseHelper =new DataBaseHelper(getActivity(), MainActivity.DBName,null,1);

        menuLayout = view.findViewById(R.id.favLayout);

        pizzaMenuList=dataBaseHelper.getAllPizzaMenu();

        int counter =10000;
        int row = 2;
        LinearLayout linearLayout = new LinearLayout(requireContext());

        for(PizzaMenu pizzaMenu : pizzaMenuList){

            if(dataBaseHelper.isPizzaInFavorites(MainActivity.user.getEmail(),pizzaMenu.getPizzaId())){
                if(row == 2){
                    linearLayout = new LinearLayout(requireContext());
                    linearLayout.setId(counter);
                    counter++;
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    menuLayout.addView(linearLayout);

                    row=0;
                }
                row++;

                PizzaFragment fragment1 =  new PizzaFragment(pizzaMenu,true);

                // Begin a FragmentTransaction
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                // Add fragment1 to the LinearLayout
                transaction.add(linearLayout.getId(), fragment1);


                // Commit the transaction
                transaction.commit();
            }



        }
        return  view;
    }
}