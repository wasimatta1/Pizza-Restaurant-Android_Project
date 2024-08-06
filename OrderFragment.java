package com.example.advancepizza.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.User;

import java.util.List;

public class OrderFragment extends Fragment {
    Order order ;
    List<Order> ordersHistory;
    List<Integer> ordersId;


    LinearLayout linearLayout;

    DataBaseHelper dataBaseHelper;

    boolean checkAdmin;
    public OrderFragment(Order order) {
        this.order = order;
        checkAdmin=false;
    }

    public OrderFragment() {
        checkAdmin=false;
    }
    public OrderFragment(boolean checkAdmin) {
        this.checkAdmin= checkAdmin;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
       dataBaseHelper =new DataBaseHelper(getActivity(), MainActivity.DBName,null,1);
       linearLayout = view.findViewById(R.id.orderHistoryLayout);

        if(!checkAdmin){
            DisplayForCustomer();

        }else {
            DisplayForAdmin();
        }



        return view;
    }
    public void DisplayForCustomer(){

        ordersId=dataBaseHelper.getAllOrderNameIdsByEmail(MainActivity.user.getEmail());
        // Begin a FragmentTransaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        for (int orderId : ordersId){
            ordersHistory =  dataBaseHelper.getAllOrdersByOrderNameId(orderId);
            Double totalPrice=0.0;
            for (Order order1 : ordersHistory){

                totalPrice+=order1.getTotalPrice();

            }
            for (Order order1 : ordersHistory){
                order1.setTotalPrice(totalPrice);
                // Add fragment1 to the LinearLayout
                transaction.add(linearLayout.getId(), new OrderItemFragment(order1,false));
                break;
            }
        }
        // Commit the transaction
        transaction.commit();

    }
    public void DisplayForAdmin(){
        ordersId=dataBaseHelper.getAllOrderIds();
        // Begin a FragmentTransaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (int orderId : ordersId){
            ordersHistory =  dataBaseHelper.getAllOrdersByOrderNameId(orderId);
            Double totalPrice=0.0;
            for (Order order1 : ordersHistory){

                totalPrice+=order1.getTotalPrice();

            }
            for (Order order1 : ordersHistory){
                order1.setTotalPrice(Double.parseDouble(String.format("%.2f", totalPrice)));
                // Add fragment1 to the LinearLayout

                transaction.add(linearLayout.getId(), new OrderItemFragment(order1,false,true,false));
                break;
            }

        }
        // Commit the transaction
        transaction.commit();
    }

}