package com.example.advancepizza.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import android.util.Log;

public class AddSpecialOffersFragment extends Fragment {

    private ImageView pizzaImage;
    private TextView textView_pizzaPrice;
    private TextView textView_pizzaDescription;
    private TextView textView_pizzaName;
    private Button startOfferPeriod;
    private Button endOfferPeriod;
    private EditText editText_offerPrice;
    private RadioGroup radioGroup_pizzaSize;
    private RadioButton radioButtonSmallSize;
    private RadioButton radioButtonMedSize;
    private RadioButton radioButtonLargeSize;

    private Button addOfferButton;
    private Button backButton;

    private DataBaseHelper dataBaseHelper;
    private PizzaMenu pizzaMenu;

    private String selectedSize;
    private double price;
    boolean isSelectStart =false;
    boolean isSelectEnd =false;
    private DatePickerDialog.OnDateSetListener startDateSetListener;

    private DatePickerDialog.OnDateSetListener endDateSetListener;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    String beginTime;
    String endTime;
    RadioButton buttonSmallSizePizza ;
    RadioButton buttonMediumSizePizza ;
    RadioButton buttonLargeSizePizza ;
    private static final String TAG = "MainActivity";

    public AddSpecialOffersFragment() {
    }

    public AddSpecialOffersFragment(PizzaMenu pizzaMenu) {
        this.pizzaMenu = pizzaMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_special_offers, container, false);
        dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);

        initializeViews(view);
        populatePizzaDetails();

        startOfferPeriod.setOnClickListener(v -> selectStartTime());
        endOfferPeriod.setOnClickListener(v -> selectEndTime());
        addOfferButton.setOnClickListener(v -> addSpecialOffer());
        backButton.setOnClickListener(v -> navigateBack());



        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String DDStr =String.valueOf(day);
                String MMStr =String.valueOf(month);

                if(day<10){
                    DDStr ="0"+String.valueOf(day);
                }
                if(month<10){
                    MMStr ="0"+String.valueOf(month);
                }

                beginTime = year+"-"+MMStr+ "-" + DDStr;
                startOfferPeriod.setText(beginTime);
                isSelectStart=true;
            }
        };
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;


                String DDStr =String.valueOf(day);
                String MMStr =String.valueOf(month);

                if(day<10){
                    DDStr ="0"+String.valueOf(day);
                }
                if(month<10){
                    MMStr ="0"+String.valueOf(month);
                }
                endTime = year+"-"+MMStr+ "-" + DDStr;
                endOfferPeriod.setText(endTime);
                isSelectEnd=true;
            }
        };

        return view;
    }
    private void selectStartTime(){

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, startDateSetListener, year,month,day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void selectEndTime(){
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, endDateSetListener, year,month,day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initializeViews(View view) {
        pizzaImage = view.findViewById(R.id.imageView_PizzaDescriptionImage);
        textView_pizzaPrice = view.findViewById(R.id.textView_pizzaPrice);
        textView_pizzaDescription = view.findViewById(R.id.textView_pizzaDescription);
        textView_pizzaName = view.findViewById(R.id.textView_pizzaName);
        startOfferPeriod = view.findViewById(R.id.editText_startOfferPeriod);
        endOfferPeriod = view.findViewById(R.id.editText_endOfferPeriod);
        editText_offerPrice = view.findViewById(R.id.editText_offerPrice);
        radioGroup_pizzaSize = view.findViewById(R.id.radioGroup_pizzaSize);
        addOfferButton = view.findViewById(R.id.button_addOffer);
        backButton = view.findViewById(R.id.button_cancelDescriptionPage);

        buttonSmallSizePizza = (RadioButton) view.findViewById(R.id.radioButton_smallSize);
        buttonMediumSizePizza =  (RadioButton) view.findViewById(R.id.radioButton_mediumSize);
        buttonLargeSizePizza = (RadioButton) view.findViewById(R.id.radioButton_largeSize);

        toggleBackgroundOfButtonsAndSetThisButtonSelected(buttonSmallSizePizza);

        radioGroup_pizzaSize.setOnCheckedChangeListener((group, checkedId) -> updatePizzaSize(checkedId));
    }
    private void populatePizzaDetails() {
        pizzaImage.setImageResource(MainActivity.getPizzaImage(pizzaMenu.getPizzaType()));
        textView_pizzaPrice.setText("$" + pizzaMenu.getPrice());
        textView_pizzaDescription.setText(pizzaMenu.getDescription());
        textView_pizzaName.setText(pizzaMenu.getPizzaType()+" (Small)");

        selectedSize = "Small";  // Default size
        price = dataBaseHelper.getPriceOfPizza(pizzaMenu.getPizzaType(), selectedSize);

    }

    private void updatePizzaSize(int checkedId) {
        RadioButton radioButton = buttonSmallSizePizza;
        if(checkedId ==R.id.radioButton_smallSize){
            radioButton=buttonSmallSizePizza;
            selectedSize = "Small";
        }else if(checkedId ==R.id.radioButton_mediumSize) {
            radioButton=buttonMediumSizePizza;
            selectedSize = "Medium";
         }
        else if(checkedId ==R.id.radioButton_largeSize) {
            radioButton=buttonLargeSizePizza;
            selectedSize = "Large";
        }

        PizzaMenu pizzaMenu1 = dataBaseHelper.getPizzaMenuByNameAndSize(pizzaMenu.getPizzaType(), selectedSize);
        if(pizzaMenu1 != null){
            toggleBackgroundOfButtonsAndSetThisButtonSelected(radioButton);
            pizzaMenu=pizzaMenu1;
        }else {
            showToast("There No More of Size Large");
        }

        textView_pizzaName.setText(pizzaMenu.getPizzaType() + " (" + pizzaMenu.getSize() + ")");
        textView_pizzaPrice.setText("$" + pizzaMenu.getPrice());
    }

    private void addSpecialOffer() {
        String offerPriceStr = editText_offerPrice.getText().toString();



        if ((!isSelectStart || !isSelectEnd) || offerPriceStr.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter both offer period and price", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            double offerPrice = Double.parseDouble(offerPriceStr);
            // Price is a valid number
        } catch (NumberFormatException e) {
            // Price is not a valid number
            Toast.makeText(getActivity(), "Please enter a valid price", Toast.LENGTH_LONG).show();
            return;
        }
        LocalDate currentDate = LocalDate.now();
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startTime = LocalDate.parse(beginTime, formatter);
        LocalDate EndTime = LocalDate.parse(endTime, formatter);

        if (startTime.isBefore(EndTime)) {
            if(startTime.isBefore(currentDate)){
                showToast("Start datetime must be after or in current Date.");
                return;
            }else {
                if(EndTime.isBefore(currentDate)){
                    showToast("End datetime must be after or in current Date.");
                    return;
                }
            }
        } else {
            showToast("Start datetime must be before end datetime.");
            return;

        }

        LocalTime currentTime = LocalTime.now();
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Convert time to string
        String timeString = currentTime.format(timeFormatter);


        SpecialOffers specialOffers = new SpecialOffers(pizzaMenu.getPizzaId(),offerPriceStr,beginTime+" "+timeString,endTime);

        // Save offer to the database (pseudo code)
        boolean isOfferAdded = dataBaseHelper.addSpecialOffer(specialOffers);

        if (isOfferAdded) {
            Toast.makeText(getActivity(), "Special offer added successfully", Toast.LENGTH_LONG).show();
            navigateBack();
        } else {
            Toast.makeText(getActivity(), "Failed to add special offer", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateBack() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_admin, new PizzaMenuFragment(true))
                .commit();
    }
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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


}
