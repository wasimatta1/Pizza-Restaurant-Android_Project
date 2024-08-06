package com.example.advancepizza.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.obects.PizzaMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.*;
public class FilterFragment extends Fragment {
        private Spinner spinnerPrice, spinnerSize, spinnerCategory;
        private Button buttonSearch , buttonCancel;


    // Create dummy lists or get your actual lists
    ArrayAdapter<CharSequence> priceAdapter;
    ArrayAdapter<CharSequence> sizeAdapter;
    ArrayAdapter<CharSequence> categoryAdapter;




    //price list:
    List<PizzaMenu> priceList;
    //size list:
    List<PizzaMenu>  sizeList ;
    // category list:
    List<PizzaMenu> categoryList ;
    boolean isFramAdmin =false;




    public FilterFragment() {
    }

    public FilterFragment(boolean isFramAdmin) {
        this.isFramAdmin = isFramAdmin;
    }

    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_filter, container, false);

            spinnerPrice = view.findViewById(R.id.spinner_price);
            spinnerSize = view.findViewById(R.id.spinner_size);
            spinnerCategory = view.findViewById(R.id.spinner_category);
            buttonSearch = view.findViewById(R.id.button_search);
            buttonCancel = view.findViewById(R.id.cancel_Search);

            // Set up spinners with arrays of options (for demonstration)
            priceAdapter = ArrayAdapter.createFromResource(getContext(), R.array.price_options, android.R.layout.simple_spinner_item);
            priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPrice.setAdapter(priceAdapter);

            sizeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.size_options, android.R.layout.simple_spinner_item);
            sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSize.setAdapter(sizeAdapter);

           categoryAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category_options, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryAdapter);

            //price list:
            priceList = new ArrayList<>();
            //size list :
            sizeList = new ArrayList<>();
            //category list:
            categoryList = new ArrayList<>();
            MainActivity.outputList.clear();


            // Set up search button click listener
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the search action
                    String selectedPrice = spinnerPrice.getSelectedItem().toString();
                    String selectedSize = spinnerSize.getSelectedItem().toString();
                    String selectedCategory = spinnerCategory.getSelectedItem().toString();

                    System.out.println(MainActivity.outputList.size());
                    System.out.println(MainActivity.allList.size());
                    // Perform search based on selected criteria
                    searchPizzas(selectedPrice, selectedSize, selectedCategory );
                    MainActivity.isFromFilter= true;
                    if(MainActivity.outputList.isEmpty()){

                        if(isFramAdmin){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new NotFoundFragment(true)).commit();

                        }else{
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotFoundFragment()).commit();
                        }
                    }
                    else{
                        if(isFramAdmin){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new PizzaMenuFragment(true)).commit();

                        }else{
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PizzaMenuFragment()).commit();
                        }
                    }




                }
            });
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //handle cancel
                    MainActivity.outputList.clear();
                    MainActivity.allList.clear();
                    MainActivity.isFromFilter= false;
                    if(isFramAdmin){
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new PizzaMenuFragment(true)).commit();

                    }else{
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PizzaMenuFragment()).commit();
                    }
                }
            });

            return view;
        }

        private  void searchPizzas(String price, String size, String category) {
            // Implement the search logic here
            // For example, filter the list of pizzas and display the results

            //search from the all list that come from pizza menu fragment:
            List<PizzaMenu> allList = new ArrayList<>();
             allList.addAll(MainActivity.allList);
            MainActivity.allList.clear();

            //search by price:

            boolean isAllSelected = false;
            for(int i = 0 ; i<allList.size() ; i++){

                for(int j = 0 ; j<5 ; j++){
                    String selectedPrice;
                    selectedPrice= priceAdapter.getItem(j).toString();

                    if( j == 0 && price.equals(selectedPrice)){
                        //under $10
                        double priceOfSelectedItem;

                        priceOfSelectedItem = Double.parseDouble(allList.get(i).getPrice());

                        if(priceOfSelectedItem < 10){
                            priceList.add(allList.get(i));
                        }

                    }

                    else if( j == 1  && price.equals(selectedPrice)){
                        double priceOfSelectedItem = Double.parseDouble(allList.get(i).getPrice());

                        if(priceOfSelectedItem  >= 10 && priceOfSelectedItem<= 20){
                            priceList.add(allList.get(i));
                        }
                    }

                    else if( j == 2 && price.equals(selectedPrice)  ){
                        double priceOfSelectedItem = Double.parseDouble(allList.get(i).getPrice());

                        if(priceOfSelectedItem  >= 20 && priceOfSelectedItem<= 30){
                            priceList.add(allList.get(i));
                        }
                    }

                    else if(j == 3 && price.equals(selectedPrice)){
                        double priceOfSelectedItem = Double.parseDouble(allList.get(i).getPrice());

                        if(priceOfSelectedItem  >= 30 ){
                            priceList.add(allList.get(i));
                        }
                    }else if(price.equalsIgnoreCase("all")){
                        isAllSelected = true;

                        priceList.addAll(allList);
                        break;
                    }
                }
                if(isAllSelected){
                    break;
                }

            }

            // filter for size:
            isAllSelected = false;

            for(int i = 0 ; i<priceList.size() ; i++){

                for(int j = 0 ; j< 4 ; j++){
                    String selectedSize= sizeAdapter.getItem(j).toString();

                    if(j == 0 && size.equals(selectedSize) ){
                        //small size
                        if(priceList.get(i).getSize().equalsIgnoreCase("Small")) {
                            sizeList.add(priceList.get(i));
                        }
                    }
                    else if(j == 1 && size.equals(selectedSize)){
                        //medium size
                        if(priceList.get(i).getSize().equalsIgnoreCase("Medium")){
                            sizeList.add(priceList.get(i));
                        }

                    }
                    else if(j == 2 && size.equals(selectedSize)){
                        //large size
                        if(priceList.get(i).getSize().equalsIgnoreCase("Large")){
                            sizeList.add(priceList.get(i));
                        }
                    }else  if(size.equalsIgnoreCase("all")){
                        isAllSelected = true;
                        sizeList.addAll(priceList);
                        break;
                    }
                }
                if(isAllSelected){
                    break;
                }
            }

            // filter for category
            isAllSelected = false;
            for(int i = 0 ; i<sizeList.size() ; i++){
                for(int j = 0 ; j<5 ; j++){
                    //add based on category
                    String selectedCategory = categoryAdapter.getItem(j).toString();

                    if(j == 0 && category.equalsIgnoreCase(selectedCategory)){
                        if(sizeList.get(i).getCategory().equalsIgnoreCase("Chicken")){
                            categoryList.add(sizeList.get(i));
                        }
                    }
                    else if(j == 1 && category.equalsIgnoreCase(selectedCategory)){
                        if(sizeList.get(i).getCategory().equalsIgnoreCase("Beef")){
                            categoryList.add(sizeList.get(i));
                        }
                    }
                    else if(j == 2 && category.equalsIgnoreCase(selectedCategory)){
                        if(sizeList.get(i).getCategory().equalsIgnoreCase("Veggies")){
                            categoryList.add(sizeList.get(i));
                        }
                    } else if(j == 3 && category.equalsIgnoreCase(selectedCategory)){
                        if(sizeList.get(i).getCategory().equalsIgnoreCase("Others")){
                            categoryList.add(sizeList.get(i));
                        }
                    }else  if(category.equalsIgnoreCase("all")){
                        isAllSelected = true;
                        categoryList.addAll(sizeList);
                        break;
                    }

                }
                if(isAllSelected){
                    break;
                }

            }


          MainActivity.outputList.addAll(categoryList);

            for(int i = 0 ; i<MainActivity.outputList.size() ; i++){
                for(int j = 1 ; j<MainActivity.outputList.size() ; j++){
                    if(MainActivity.outputList.get(i).getPizzaType() == MainActivity.outputList.get(j).getPizzaType()){
                        MainActivity.outputList.remove(MainActivity.outputList.get(j));
                        break;
                    }
                }
            }
            //MainActivity.makeListUniqueByPizzaType();

            priceList.clear();
            sizeList.clear();
            categoryList.clear();

        }



}