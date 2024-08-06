package com.example.advancepizza.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.Activity.SignInActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DescriptionFragment extends Fragment {

    private ImageView pizzaImage;
    private TextView textView_pizzaPrice;
    private TextView totalPriceView;
    private TextView offerView;
    private TextView offersPeriod;
    private TextView textView_pizzaDescription;

    private TextView textView_pizzaName;
    DataBaseHelper dataBaseHelper;

    private ImageButton fav;

    private ImageButton decreese_order;

    private ImageButton increase_order;
    private Button order;
    private boolean isFAv;
    private String Size;
    private Double price;
    private Double totalPrice;

    private PizzaMenu pizzaMenu ;
    List<SpecialOffers> offers = new ArrayList<>();
    int pizzaFavId;

    private int currentCounter;
    private int previousCounter = 0;

    private Button backButton;

    private  SpecialOffers specialOffers;
    boolean inOffer=false;
    boolean inOfferSelcted=false;
    // list of type
    // tyope = get all type pizza from data by q
    //make actions for the three buttons
    Button buttonSmallSizePizza ;
    Button buttonMediumSizePizza ;
    Button buttonLargeSizePizza ;
    public DescriptionFragment(PizzaMenu pizzaMenu) {
        this.pizzaMenu=pizzaMenu;
        pizzaFavId=pizzaMenu.getPizzaId();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        dataBaseHelper =new DataBaseHelper(getActivity(), MainActivity.DBName,null,1);

        //initializing values

        pizzaImage = view.findViewById(R.id.imageView_PizzaDescriptionImage);
        textView_pizzaPrice = view.findViewById(R.id.textView_pizzaPrice);
        textView_pizzaDescription = view.findViewById(R.id.textView_pizzaDescription);
        textView_pizzaName = view.findViewById(R.id.textView_pizzaName);
        fav = view.findViewById(R.id.imageButton_FavariteButtonDescription);
        order= view.findViewById(R.id.button_add_to_cart);
        totalPriceView = view.findViewById(R.id.textView_total);
        offerView = view.findViewById(R.id.offers);
        offersPeriod=view.findViewById(R.id.offersPeriod);


        price = dataBaseHelper.getPriceOfPizza(pizzaMenu.getPizzaType().toString() , "Small");
        //fill image
        pizzaImage.setImageResource(MainActivity.getPizzaImage(pizzaMenu.getPizzaType()));
        //fill price
        textView_pizzaPrice.setText("$ " + String.valueOf(pizzaMenu.getPrice()));
        //fill description
        textView_pizzaDescription.setText(pizzaMenu.getDescription());
        //pizza name

        textView_pizzaName.setText(String.valueOf(pizzaMenu.getPizzaType()) + "( " +  pizzaMenu.getSize() + " ) ");

        //order amount  text:
        TextView amountOfPizza = (TextView) view.findViewById(R.id.editText_pizzaAmount);
        Button button_decreasePizzaAmount = (Button) view.findViewById(R.id.imageButton_decreaseAmount);
        Button button_increasePizzaAmount = (Button) view.findViewById(R.id.imageButton_increaseAmount);
        backButton = (Button)  view.findViewById(R.id.button_cancelDescriptionPage);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String sizeInOffer="Offer Include : ";

        offers =dataBaseHelper.getOffersByPizzaType(pizzaMenu.getPizzaType());

            for(SpecialOffers pizzaoffer : offers) {
                LocalDateTime dateTimeVar = LocalDateTime.parse(pizzaoffer.getStartOfer(), dateTimeFormatter);
                LocalDate startTime = dateTimeVar.toLocalDate();
                LocalDate endTime = LocalDate.parse(pizzaoffer.getEndOfer(), formatter);
                if((now.isAfter(startTime)||now.isEqual(startTime))&& now.isBefore(endTime)) {
                    sizeInOffer += dataBaseHelper.getPizzaMenuById(pizzaoffer.getPizzaID()).getSize() + " ";
                    inOffer = true;
                }
            }
        if(inOffer){
            offerView.setText(sizeInOffer);
            setNewPrice(textView_pizzaPrice,"Small");
        }

        button_increasePizzaAmount.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // increase by one if increase button clicked
                if(previousCounter <10){
                    currentCounter = previousCounter+1;
                    amountOfPizza.setText(String.valueOf(currentCounter));
                    previousCounter = currentCounter;
                    if(inOffer&& inOfferSelcted){
                        totalPrice=((double) currentCounter * Double.parseDouble(specialOffers.getPrice()));
                    }else {
                        totalPrice=((double) currentCounter * price);
                    }

                    totalPriceView.setText("Total: $" + totalPrice);
                }


            }
        });
        button_decreasePizzaAmount.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //decrease by one if decrease button clicked
                if(previousCounter > 0 ){
                    currentCounter = previousCounter-1;
                    amountOfPizza.setText(String.valueOf(currentCounter));
                    previousCounter = currentCounter;
                    if(inOffer && inOfferSelcted){
                        totalPrice=((double) currentCounter * Double.parseDouble(specialOffers.getPrice()));
                    }else {
                        totalPrice=((double) currentCounter * price);
                    }
                    totalPriceView.setText("Total: $" + totalPrice);
                }


            }
        });

        if(dataBaseHelper.isPizzaInFavorites(MainActivity.user.getEmail(),pizzaFavId)){
            TransitionDrawable transitionDrawable = (TransitionDrawable) fav.getDrawable();
            transitionDrawable.reverseTransition(0);
            isFAv=true;
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

            }
        });

        //make actions for the three buttons
        buttonSmallSizePizza = (Button) view.findViewById(R.id.button_smallSize);
        buttonMediumSizePizza =  (Button) view.findViewById(R.id.button_mediumSize);
        buttonLargeSizePizza = (Button) view.findViewById(R.id.button_largeSize);

        toggleBackgroundOfButtonsAndSetThisButtonSelected(buttonSmallSizePizza);

        Size="Small";
        buttonSmallSizePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show small size price if selected
                price = dataBaseHelper.getPriceOfPizza(pizzaMenu.getPizzaType().toString() , "Small");
                if(price != -1){
                    Size="Small";
                    textView_pizzaName.setText(pizzaMenu.getPizzaType() + "( " +  "Small" + " ) ");
                    textView_pizzaPrice.setText("$ " + String.valueOf(price ));
                    amountOfPizza.setText(String.valueOf(0));
                    totalPrice=0.0;
                    totalPriceView.setText("Total: $0");
                    setNewPrice(textView_pizzaPrice,"Small");
                    toggleBackgroundOfButtonsAndSetThisButtonSelected(buttonSmallSizePizza);

                }else {
                    showToast("There No More of Size Small");
                }

            }
        });
        buttonMediumSizePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show medium size price if selected
                price = dataBaseHelper.getPriceOfPizza(pizzaMenu.getPizzaType().toString() , "Medium");
                if(price != -1){
                    Size="Medium";
                    textView_pizzaName.setText(pizzaMenu.getPizzaType() + "( " +  "Medium" + " ) ");
                    textView_pizzaPrice.setText("$ " + String.valueOf(price ));
                    amountOfPizza.setText(String.valueOf(0));
                    totalPrice=0.0;
                    totalPriceView.setText("Total: $0");
                    setNewPrice(textView_pizzaPrice,"Medium");
                    toggleBackgroundOfButtonsAndSetThisButtonSelected(buttonMediumSizePizza);

                }else {
                    showToast("There No More of Size Medium");
                }
            }
        });

        buttonLargeSizePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show large size price if selected
                price = dataBaseHelper.getPriceOfPizza(pizzaMenu.getPizzaType().toString() , "Large");
                if(price != -1){
                    Size="Large";
                    textView_pizzaName.setText(pizzaMenu.getPizzaType() + "( " +  "Large" + " ) ");
                    textView_pizzaPrice.setText("$ " + String.valueOf(price ));
                    amountOfPizza.setText(String.valueOf(0));
                    totalPrice=0.0;
                    totalPriceView.setText("Total: $0");
                    setNewPrice(textView_pizzaPrice,"Large");
                    toggleBackgroundOfButtonsAndSetThisButtonSelected(buttonLargeSizePizza);
                }else {
                    showToast("There No More of Size Large");
                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity =Integer.parseInt(amountOfPizza.getText().toString());

                PizzaMenu pizzaMenu1  = dataBaseHelper.getPizzaMenuByNameAndSize(pizzaMenu.getPizzaType().toString() , Size);

                LocalDate currentDate = LocalDate.now();
                    // Define the date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Convert date to string
                String dateString = currentDate.format(formatter);

                if(quantity >0) {
                  int orderNameId =  dataBaseHelper.getLastOrderNameId();
                  if(orderNameId < 0){
                      orderNameId=0;
                  }
                    Order order1 = new Order(pizzaMenu1.getPizzaId(), orderNameId+1, quantity, totalPrice, Size, dateString);
                    boolean enable=true;
                    for(int i=0 ;i<MainActivity.cardOrder.size();i++){
                       PizzaMenu pizzaMenu2 =dataBaseHelper.getPizzaMenuById(MainActivity.cardOrder.get(i).getPizzaId());
                       if(pizzaMenu2.getPizzaType().equals(pizzaMenu1.getPizzaType()) && pizzaMenu2.getSize().equals(pizzaMenu1.getSize())){
                           MainActivity.cardOrder.get(i).setQuantity(MainActivity.cardOrder.get(i).getQuantity()+quantity);
                           MainActivity.cardOrder.get(i).setTotalPrice( MainActivity.cardOrder.get(i).getTotalPrice()+totalPrice);
                           enable =false;
                       }
                    }
                    if(enable)
                        MainActivity.cardOrder.add(order1);
                    Toast.makeText(getActivity(), "Added to card ", Toast.LENGTH_LONG).show();

                    PizzaMenuFragment pizzaMenuFragment = new PizzaMenuFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pizzaMenuFragment).commit();
                }else {
                    Toast.makeText(getActivity(), "You don't get any item", Toast.LENGTH_LONG).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to pizza Menu list page
                MainActivity.allList.clear();
                PizzaMenuFragment pizzaMenuFragment = new PizzaMenuFragment();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pizzaMenuFragment).commit();
            }
        });



        return view;
    }
    public  void  updateDataBase( boolean checkFav){
        if(checkFav){

            dataBaseHelper.addFavoriteMenu(MainActivity.user.getEmail(),pizzaFavId);
            Toast.makeText(getActivity(),"Pizza "+ pizzaMenu.getPizzaType() + " added to Favorite", Toast.LENGTH_LONG).show();

        }else {
            dataBaseHelper.removeFavoriteMenu(MainActivity.user.getEmail(),pizzaFavId);
            Toast.makeText(getActivity(),"Pizza "+ pizzaMenu.getPizzaType() + " removed to Favorite", Toast.LENGTH_LONG).show();

        }
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

    public void setNewPrice(TextView textView,String size){
        LocalDate now = LocalDate.now();

        for( SpecialOffers specialOffers :offers){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTimeVar = LocalDateTime.parse(specialOffers.getStartOfer(), dateTimeFormatter);
            LocalDate startTime = dateTimeVar.toLocalDate();
            LocalDate endTime = LocalDate.parse(specialOffers.getEndOfer(), formatter);

            PizzaMenu pizzaMenu1 = dataBaseHelper.getPizzaMenuById(specialOffers.getPizzaID());

            if(pizzaMenu1.getSize().equals(size) && (now.isAfter(startTime)||now.isEqual(startTime)) && now.isBefore(endTime)){
                this.specialOffers=specialOffers;
                setPizzaPrice(textView_pizzaPrice,pizzaMenu1.getPrice(),specialOffers.getPrice());
                offersPeriod.setText(startTime + " to " + specialOffers.getEndOfer());
                inOfferSelcted=true;
                break;
            }else {
                if(now.isBefore(startTime))
                    offersPeriod.setText("The offer begin at :"+specialOffers.getStartOfer());
                else
                    offersPeriod.setText("");
                inOfferSelcted=false;
            }

        }



    }

    private void toggleBackgroundOfButtonsAndSetThisButtonSelected(Button selectedButton){
        // set all buttons to default white background, and set the text color to black
        buttonSmallSizePizza.setBackgroundResource(R.drawable.baseline_circle_24);
        buttonSmallSizePizza.setTextColor(getResources().getColor(R.color.black));
        buttonMediumSizePizza.setBackgroundResource(R.drawable.baseline_circle_24);
        buttonMediumSizePizza.setTextColor(getResources().getColor(R.color.black));
        buttonLargeSizePizza.setBackgroundResource(R.drawable.baseline_circle_24);
        buttonLargeSizePizza.setTextColor(getResources().getColor(R.color.black));
        // set the selected button to orange background and the text color to white
        selectedButton.setBackgroundResource(R.drawable.c2);
        selectedButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}