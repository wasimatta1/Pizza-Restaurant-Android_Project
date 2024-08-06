package com.example.advancepizza.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;
import com.example.advancepizza.obects.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderItemFragment extends Fragment {

    Order order ;

    PizzaMenu pizzaMenu;

    ImageView pizzaImage;
    ImageButton removeButton;
    TextView pizzaTypeView;
    TextView dateView;

    TextView  pizzaPriceView;

    TextView pizzaQuantityView;
    LinearLayout quantityLinear;
    LinearLayout nameLinearLayout;
    LinearLayout removeLinearLayout;
    LinearLayout  parentLinear;
    DataBaseHelper dataBaseHelper;
    Button detailsButton;
    boolean checkComeFrom ;
    boolean checkComeFromAdmin ;
    boolean checkComeFromDetails =false;
    public OrderItemFragment(Order order,boolean checkComeFrom) {
        this.order = order;
        this.checkComeFrom=checkComeFrom;
        checkComeFromAdmin=false;
    }
    public OrderItemFragment(Order order,boolean checkComeFrom,boolean checkComeFromAdmin,boolean checkComeFromDetails) {
        this.order = order;
        this.checkComeFrom=checkComeFrom;
        this.checkComeFromAdmin= checkComeFromAdmin;
        this.checkComeFromDetails=checkComeFromDetails;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_item, container, false);

        dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);
        pizzaMenu = dataBaseHelper.getPizzaMenuById(order.getPizzaId());
        pizzaImage = view.findViewById(R.id.PizzaImage);
        pizzaTypeView = view.findViewById(R.id.PizzaType);
        pizzaPriceView = view.findViewById(R.id.price);
        pizzaQuantityView = view.findViewById(R.id.quantity);
        dateView = view.findViewById(R.id.dateView);
        quantityLinear = view.findViewById(R.id.quantityLinear);
        nameLinearLayout= view.findViewById(R.id.namesLinear);
        removeButton= view.findViewById(R.id.RemoveFromCard);
        removeLinearLayout= view.findViewById(R.id.removeLinear);
        parentLinear= view.findViewById(R.id.parentLinear);

        pizzaImage.setImageResource(MainActivity.getPizzaImage(pizzaMenu.getPizzaType()));
        pizzaQuantityView.setText(String.valueOf(order.getQuantity()));

            if (checkComeFrom) {
                pizzaTypeView.setText(pizzaMenu.getPizzaType() + " (" + pizzaMenu.getSize() + ")");


                List<SpecialOffers> offers = new ArrayList<>();
                offers =dataBaseHelper.getOffersByPizzaType(pizzaMenu.getPizzaType());

                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalTime OrderTime = LocalTime.now();


                if(checkComeFromDetails){

                    LocalDateTime dateTimeVar = LocalDateTime.parse(order.getDateOfOrder(), dateTimeFormatter);

                    OrderTime=dateTimeVar.toLocalTime();
                    now = dateTimeVar.toLocalDate();
                   // now = LocalDate.parse(order.getDateOfOrder(), formatter);
                    removeLinearLayout.removeAllViews();
                    parentLinear.removeView(removeLinearLayout);
                }

                if(offers.size()>0) {
                    for(SpecialOffers specialOffers: offers){

                        LocalDateTime dateTimeVar1 = LocalDateTime.parse(specialOffers.getStartOfer(), dateTimeFormatter);
                        LocalTime OfferTime = LocalTime.now();
                        OfferTime=dateTimeVar1.toLocalTime();
                        LocalDate startTime=dateTimeVar1.toLocalDate();
                      //  LocalDate startTime = LocalDate.parse(specialOffers.getStartOfer(), formatter);

                        LocalDate endTime = LocalDate.parse(specialOffers.getEndOfer(), formatter);
                        PizzaMenu pizzaMenu1 = dataBaseHelper.getPizzaMenuById(specialOffers.getPizzaID());

                        if(pizzaMenu1.getSize().equals(pizzaMenu.getSize())&& (now.isAfter(startTime)||now.isEqual(startTime)) && now.isBefore(endTime)) {
                            if(checkComeFromDetails && now.isEqual(startTime) && OrderTime.isBefore(OfferTime)){
                                pizzaPriceView.setText(pizzaMenu.getPrice());
                            }else {
                                setPizzaPrice(pizzaPriceView,pizzaMenu.getPrice(),specialOffers.getPrice());
                            }
                            break;
                        }else {
                            pizzaPriceView.setText(pizzaMenu.getPrice());
                        }
                    }
                }else {
                    pizzaPriceView.setText(pizzaMenu.getPrice());
                }

            } else {
                removeLinearLayout.removeAllViews();
                parentLinear.removeView(removeLinearLayout);
                if(checkComeFromAdmin){

                    User user = dataBaseHelper.getUserByOrderNameId(order.getOrderNameId());
                    TextView name =new TextView(getContext());
                    name.setText(user.getFirstName()+" "+user.getLastName());

                    name.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    name.setTextSize(18); // 18sp
                    name.setTypeface(null, Typeface.BOLD); // Bold text style
                    name.setTextColor(Color.BLACK); // Text color

                    nameLinearLayout.addView(name,0);


                }
                pizzaTypeView.setText("The Order Id : " + order.getOrderNameId());
                pizzaPriceView.setText("Total Price " + (order.getTotalPrice() + 20));
                dateView.setText(order.getDateOfOrder());
                pizzaQuantityView.setVisibility(View.INVISIBLE);
                quantityLinear.setBackgroundColor(Color.TRANSPARENT);

                detailsButton = new Button(requireContext());
                detailsButton.setId(View.generateViewId());
                detailsButton.setText("Details");
                detailsButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                detailsButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_background));

                quantityLinear.addView(detailsButton);

                // Set the OnClickListener right after the button is created
                detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardFragment cardFragment = new CardFragment(true, order.getOrderNameId());
                        if(checkComeFromAdmin){
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container_admin, cardFragment).commit();
                        }else {
                            CardFragment cardFragment1 = new CardFragment(true, order.getOrderNameId(),true);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, cardFragment1).commit();
                        }

                    }
                });
            }

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.cardOrder.remove(order);
                CardFragment cardFragment = new CardFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cardFragment).commit();
            }
        });



        return view;
    }
    private void setPizzaPrice(TextView textView, String oldPrice, String newPrice) {
        // Create SpannableString for the old price with a strikethrough effect
        SpannableString oldPriceSpannable = new SpannableString(oldPrice);
        oldPriceSpannable.setSpan(new StrikethroughSpan(), 0, oldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Create SpannableString for the new price with a different color
        SpannableString newPriceSpannable = new SpannableString(newPrice);
        newPriceSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, newPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Combine both SpannableStrings into one
        SpannableStringBuilder priceBuilder = new SpannableStringBuilder();
        priceBuilder.append(oldPriceSpannable);
        priceBuilder.append("  "); // Add space between old and new price
        priceBuilder.append(newPriceSpannable);

        // Set the combined SpannableStringBuilder to the TextView
        textView.setText(priceBuilder);

    }
}