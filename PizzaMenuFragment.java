package com.example.advancepizza.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.widget.SearchView;
public class PizzaMenuFragment extends Fragment  {

    LinearLayout menuLayout ;

    List<PizzaMenu> pizzaMenuList ;
    List<PizzaMenu> pizzaMenuListNotDistinct;
    //list of chicken category
    List<PizzaMenu> chickenPizzaList = new ArrayList<PizzaMenu>();

    //list of beef  categeory list

    List<PizzaMenu> beefPizzaList = new ArrayList<PizzaMenu>();

    //list of vigges category list

    List<PizzaMenu> viggesPizzaList = new ArrayList<PizzaMenu>();

    //list of others category list

    List<PizzaMenu> othersPizzaList = new ArrayList<PizzaMenu>();


    DataBaseHelper dataBaseHelper;

    int counter =1;

    private EditText searchName;

    private boolean isFromAdmin= false;
    Button button_all ;
    Button button_chickenList;
    Button button_beefList ;
    Button button_veggiesList;
    Button button_otherList;
    ImageButton filter;
    public static PizzaMenuFragment newInstance() {
        PizzaMenuFragment fragment = new PizzaMenuFragment();
        return fragment;
    }

    public PizzaMenuFragment() {
    }
    public PizzaMenuFragment(boolean isFromAdmin) {
        this.isFromAdmin = isFromAdmin;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pizza_menu, container, false);

        dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);

        menuLayout = view.findViewById(R.id.menuLayout);
        pizzaMenuList = dataBaseHelper.getAllPizzaMenueDistinctType();
        pizzaMenuListNotDistinct = dataBaseHelper.getAllPizzaMenu();

        if (MainActivity.allList.isEmpty()) {
            MainActivity.allList.addAll(pizzaMenuListNotDistinct);
        }


         button_all = (Button) view.findViewById(R.id.button_allList);
         button_chickenList = (Button) view.findViewById(R.id.button_chickenList);
         button_beefList = (Button) view.findViewById(R.id.button_beefList);
         button_veggiesList = (Button) view.findViewById(R.id.button_veggiesList);
         button_otherList = (Button) view.findViewById(R.id.button_othersList);
        ImageButton filter = (ImageButton) view.findViewById(R.id.imageButton);
        searchName = view.findViewById(R.id.search_view_byName);

        if (!MainActivity.outputList.isEmpty() && MainActivity.isFromFilter) {
            List<PizzaMenu> newList = new ArrayList<>(MainActivity.outputList);
            MainActivity.isFromFilter = false;
            displayMenu(newList);

        } else {
            displayMenu(pizzaMenuList);
        }
        //button for list all types of pizza
        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMenu(pizzaMenuList);
                toggleBackgroundOfButtonsAndSetThisButtonSelected(button_all);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFromAdmin) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new FilterFragment(true)).commit();

                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FilterFragment()).commit();

                }


            }
        });
        //button for list chicken category of pizza
        button_chickenList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chickenPizzaList.clear();
                for (PizzaMenu pizzaMenu : pizzaMenuList) {
                    if (pizzaMenu.getCategory().equals("chicken")) {

                        chickenPizzaList.add(pizzaMenu);
                    }
                }
                displayMenu(chickenPizzaList);
                toggleBackgroundOfButtonsAndSetThisButtonSelected(button_chickenList);

            }
        });
        //display beef list if button pressed
        button_beefList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beefPizzaList.clear();
                for (PizzaMenu pizzaMenu : pizzaMenuList) {
                    if (pizzaMenu.getCategory().equals("beef")) {
                        beefPizzaList.add(pizzaMenu);
                    }
                }
                displayMenu(beefPizzaList);
                toggleBackgroundOfButtonsAndSetThisButtonSelected(button_beefList);

            }
        });

        //display veggies list if button pressed
        button_veggiesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viggesPizzaList.clear();
                for (PizzaMenu pizzaMenu : pizzaMenuList) {
                    if (pizzaMenu.getCategory().equals("veggies")) {
                        viggesPizzaList.add(pizzaMenu);
                    }
                }
                displayMenu(viggesPizzaList);
                toggleBackgroundOfButtonsAndSetThisButtonSelected(button_veggiesList);

            }
        });

        //display others list if button pressed
        button_otherList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othersPizzaList.clear();
                for (PizzaMenu pizzaMenu : pizzaMenuList) {
                    if (pizzaMenu.getCategory().equals("others")) {
                        othersPizzaList.add(pizzaMenu);
                    }
                }
                displayMenu(othersPizzaList);
                toggleBackgroundOfButtonsAndSetThisButtonSelected(button_otherList);

            }
        });


        // search of pizza by it's name
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<PizzaMenu> searchList = new ArrayList<>();
                String  temp = searchName.getText().toString().toLowerCase();

                for (PizzaMenu pizza : pizzaMenuList) {
                    String  temp2 = pizza.getPizzaType().toString().toLowerCase();
                    if (temp2.contains(temp)) {
                        searchList.add(pizza);
                    }
                }

                displayMenu(searchList); // Implement this method to display the filtered list


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        return view;
    }



    public void displayMenu(List<PizzaMenu> pizzaMenuList){
        menuLayout.removeAllViews();

        int row = 2;
        LinearLayout linearLayout = new LinearLayout(requireContext());

        for(PizzaMenu pizzaMenu : pizzaMenuList){

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

            PizzaFragment fragment1 =  new PizzaFragment(pizzaMenu,false,isFromAdmin);

            // Begin a FragmentTransaction
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // Add fragment1 to the LinearLayout
            transaction.add(linearLayout.getId(), fragment1);


            // Commit the transaction
            transaction.commit();
        }

    }

    private void toggleBackgroundOfButtonsAndSetThisButtonSelected(Button selectedButton){
        // set all buttons to default white background, and set the text color to black
        button_otherList.setBackgroundResource(R.drawable.button_background_2);
        button_otherList.setTextColor(getResources().getColor(R.color.black));
        button_veggiesList.setBackgroundResource(R.drawable.button_background_2);
        button_veggiesList.setTextColor(getResources().getColor(R.color.black));
        button_beefList.setBackgroundResource(R.drawable.button_background_2);
        button_beefList.setTextColor(getResources().getColor(R.color.black));
        button_chickenList.setBackgroundResource(R.drawable.button_background_2);
        button_chickenList.setTextColor(getResources().getColor(R.color.black));
        button_all.setBackgroundResource(R.drawable.button_background_2);
        button_all.setTextColor(getResources().getColor(R.color.black));

        // set the selected button to orange background and the text color to white

        selectedButton.setBackgroundResource(R.drawable.button_background);
        selectedButton.setTextColor(getResources().getColor(R.color.white));
    }
}