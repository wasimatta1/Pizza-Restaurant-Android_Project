package com.example.advancepizza.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.Activity.SignInActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PizzaFragment extends Fragment {

    private int pizzaId;
    private TextView name;
    private ImageButton fav;
    private ImageButton addOrder;
    private ImageView pizzaImage;
    ImageView discount;

    private boolean isFAv;
    DataBaseHelper dataBaseHelper;
    private PizzaMenu pizzaMenu;
    private  boolean isFromFav;
    private  boolean  isFromAdmin=false;

    public PizzaFragment(PizzaMenu pizzaMenu,boolean check) {
        this.pizzaMenu = pizzaMenu;
        isFromFav=check;
    }

    public PizzaFragment(PizzaMenu pizzaMenu, boolean isFromFav, boolean isFromAdmin) {
        this.pizzaMenu = pizzaMenu;
        this.isFromFav = isFromFav;
        this.isFromAdmin = isFromAdmin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pizza, container, false);
         dataBaseHelper =new DataBaseHelper(getActivity(), MainActivity.DBName,null,1);

        name = view.findViewById(R.id.pizzaName);
        fav = view.findViewById(R.id.FavButton);
        addOrder =view.findViewById(R.id.orderButton);
        pizzaImage = view.findViewById(R.id.Pizzaimage);
        discount= view.findViewById(R.id.imageDiscount);

        // set data
        pizzaId = pizzaMenu.getPizzaId();
        name.setText(pizzaMenu.getPizzaType());

        if(isFromAdmin){
            fav.setVisibility(View.INVISIBLE);
            addOrder.setImageResource(MainActivity.getPizzaImage("nav"));
        }

        List<SpecialOffers> offers = new ArrayList<>();
        offers =dataBaseHelper.getOffersByPizzaType(pizzaMenu.getPizzaType());

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            if(offers.size()>0) {
                for(SpecialOffers specialOffers: offers){
                    LocalDateTime dateTimeVar = LocalDateTime.parse(specialOffers.getStartOfer(), dateTimeFormatter);
                    LocalDate startTime = dateTimeVar.toLocalDate();
                    LocalDate endTime = LocalDate.parse(specialOffers.getEndOfer(), formatter);

                    if((now.isAfter(startTime) ||now.isEqual(startTime)) && now.isBefore(endTime)) {
                        discount.setVisibility(View.VISIBLE);
                        break;
                    }
                }

            }
        pizzaImage.setImageResource(MainActivity.getPizzaImage(pizzaMenu.getPizzaType()));


        if(dataBaseHelper.isPizzaInFavorites(MainActivity.user.getEmail(),pizzaId)){
            isFAv=true;
            TransitionDrawable transitionDrawable = (TransitionDrawable) fav.getDrawable();
            transitionDrawable.reverseTransition(0);
        }else {
            isFAv=false;
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) fav.getDrawable();
                transitionDrawable.reverseTransition(100);

                    isFAv = !isFAv;
                    updateDataBase(isFAv);
                if(isFromFav){
                    FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
                }

            }
        });

        // set text on click listner



        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromAdmin){
                    AddSpecialOffersFragment addSpecialOffersFragment = new AddSpecialOffersFragment(pizzaMenu);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, addSpecialOffersFragment).commit();

                }else{
                DescriptionFragment descriptionFragment = new DescriptionFragment(pizzaMenu);
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, descriptionFragment).commit();
              }
            }
        });
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromAdmin){
                    AddSpecialOffersFragment addSpecialOffersFragment = new AddSpecialOffersFragment(pizzaMenu);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, addSpecialOffersFragment).commit();

                }else{
                    DescriptionFragment descriptionFragment = new DescriptionFragment(pizzaMenu);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, descriptionFragment).commit();
                }
            }
        });
        pizzaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromAdmin){
                    AddSpecialOffersFragment addSpecialOffersFragment = new AddSpecialOffersFragment(pizzaMenu);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, addSpecialOffersFragment).commit();

                }else{
                    DescriptionFragment descriptionFragment = new DescriptionFragment(pizzaMenu);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, descriptionFragment).commit();
                }
            }
        });


        return view;
    }

    public  void  updateDataBase( boolean checkFav){
        if(checkFav){

        dataBaseHelper.addFavoriteMenu(MainActivity.user.getEmail(),pizzaId);
            Toast.makeText(getActivity(),"Pizza "+ pizzaMenu.getPizzaType() + " added to Favorite", Toast.LENGTH_LONG).show();

        }else {
            dataBaseHelper.removeFavoriteMenu(MainActivity.user.getEmail(),pizzaId);
            Toast.makeText(getActivity(),"Pizza "+ pizzaMenu.getPizzaType() + " removed to Favorite", Toast.LENGTH_LONG).show();

        }
    }


}