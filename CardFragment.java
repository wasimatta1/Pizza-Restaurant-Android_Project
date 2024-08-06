package com.example.advancepizza.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CardFragment extends Fragment {

    PizzaMenu pizzaMenu;
    LinearLayout ListOrderLayout ;
    TextView dateView ;

    TextView TotalItem;

    TextView TotalPricesItem;

    TextView TotalPrice1;

    TextView TotalPrice;

    int totalItem;

    double TotalSubPrice;

    Button confirmOrder;
    LinearLayout parentLinear ;


    LinearLayout UserInfolinear;
    TextView userName;
    TextView userEmail;
    TextView userPhone;

    DataBaseHelper dataBaseHelper;

    boolean isFromDetails;

    int orderId;
    List<Order> ordersHistory;

    boolean isFromDetailCustomer =false;

    public CardFragment() {
        isFromDetails=false;
    }
    public CardFragment(boolean isFromDetails,int orderId) {
        this.isFromDetails=isFromDetails;
        this.orderId=orderId;
    }
    public CardFragment(boolean isFromDetails,int orderId,boolean isFromDetailCustomer) {
        this.isFromDetails=isFromDetails;
        this.orderId=orderId;
        this.isFromDetailCustomer=isFromDetailCustomer;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_card, container, false);

        dateView = view.findViewById(R.id.dateView);
        ListOrderLayout = view.findViewById(R.id.orders);
        TotalItem = view.findViewById(R.id.TotalNumberOItem);
        TotalPricesItem  = view.findViewById(R.id.TotalPricesItem);
        TotalPrice1  = view.findViewById(R.id.TotalPrice1);
        TotalPrice  = view.findViewById(R.id.TotalPrice);
        confirmOrder  = view.findViewById(R.id.OrderNow);
         UserInfolinear= view.findViewById(R.id.UserInfolinear);
         userName= view.findViewById(R.id.userName);
         userEmail= view.findViewById(R.id.userEmail);
         userPhone= view.findViewById(R.id.userPhone);
        parentLinear= view.findViewById(R.id.parentLinear);

        dataBaseHelper =new DataBaseHelper(getActivity(), MainActivity.DBName,null,1);


        totalItem=0;
        TotalSubPrice=0;


        LocalDate currentDate = LocalDate.now();
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Convert date to string
        String dateString = currentDate.format(formatter);

        LocalTime currentTime = LocalTime.now();
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Convert time to string
        String timeString = currentTime.format(timeFormatter);
        dateView.setText(dateString+" "+timeString);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if(isFromDetails){
            confirmOrder.setVisibility(View.INVISIBLE);
            ordersHistory =  dataBaseHelper.getAllOrdersByOrderNameId(orderId);
            for (Order order1 : ordersHistory){
                pizzaMenu = dataBaseHelper.getPizzaMenuById(order1.getPizzaId());
                transaction.add(ListOrderLayout.getId(), new OrderItemFragment(order1, true,false,true));
                totalItem += order1.getQuantity();
                TotalSubPrice += order1.getTotalPrice();
            }
            if(isFromDetailCustomer){
                parentLinear.removeView(UserInfolinear);
            }else {
                User user =dataBaseHelper.getUserByOrderNameId(ordersHistory.get(0).getOrderNameId());
                userPhone.setText("Phone : "+user.getPhone());
                userEmail.setText("Email : " +user.getEmail());
                userName.setText("Name : "+user.getFirstName()+" "+user.getLastName());
            }
        }else {
            parentLinear.removeView(UserInfolinear);
            for (Order order : MainActivity.cardOrder) {
                pizzaMenu = dataBaseHelper.getPizzaMenuById(order.getPizzaId());
                transaction.add(ListOrderLayout.getId(), new OrderItemFragment(order, true));
                totalItem += order.getQuantity();
                TotalSubPrice += order.getTotalPrice();
            }
        }
        // Commit the transaction
        transaction.commit();

        TotalItem.setText(String.valueOf(totalItem));
        TotalPricesItem.setText(String.valueOf(TotalSubPrice));
        TotalPrice1.setText(String.valueOf(TotalSubPrice+20));
        TotalPrice.setText(String.valueOf(TotalSubPrice+20));


        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Order order : MainActivity.cardOrder){
                    order.setDateOfOrder(dateString+" "+timeString);

                    dataBaseHelper.insertOrderName(MainActivity.user.getEmail(),order.getOrderNameId());
                   dataBaseHelper.insertOrder(order);
                }
                MainActivity.cardOrder.clear();
                Toast.makeText(getActivity(), "you're order was confirmed", Toast.LENGTH_LONG).show();
                PizzaMenuFragment pizzaMenuFragment = new PizzaMenuFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pizzaMenuFragment).commit();
            }
        });


        return view;
    }
}