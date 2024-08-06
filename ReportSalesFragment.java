package com.example.advancepizza.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public  class ReportSalesFragment extends Fragment {

    private DataBaseHelper dataBaseHelper;
    private Spinner spinnerPizzaType;
    private TextView tvNumberOfOrders, tvIncomePerType, tvTotalIncome;
    Double totalIncomeOftTpe=0.0;
    int totalOrderOftTpe=0;
    Double totalIncome=0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_sales, container, false);

        // Initialize the database helper
        dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);

        // Get the list of distinct pizza types
        List<PizzaMenu> pizzaMenuList = dataBaseHelper.getAllPizzaMenueDistinctType();

        // Initialize UI components
        spinnerPizzaType = view.findViewById(R.id.spinnerPizzaType);
        tvNumberOfOrders = view.findViewById(R.id.tvNumberOfOrders);
        tvIncomePerType = view.findViewById(R.id.tvIncomePerType);
        tvTotalIncome = view.findViewById(R.id.tvTotalIncome);
        MaterialButton btnCalculateOrders = view.findViewById(R.id.btnCalculateOrders);
        MaterialButton btnCalculateIncome = view.findViewById(R.id.btnCalculateIncome);

        // Populate the spinner with pizza types
        List<String> pizzaType =new ArrayList<>();
        for(PizzaMenu pizzaMenu :pizzaMenuList){
            pizzaType.add(pizzaMenu.getPizzaType());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pizzaType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPizzaType.setAdapter(adapter);

        btnCalculateOrders.setOnClickListener(v -> calculateNumberOfOrders());
        btnCalculateIncome.setOnClickListener(v -> calculateIncome());

        return view;
    }

        private void calculateNumberOfOrders() {

             totalIncomeOftTpe=0.0;
             totalOrderOftTpe=0;
            List<Order> orders =new ArrayList<>();
            orders=dataBaseHelper.getAllOrders();
            for(Order order:orders){
                PizzaMenu pizzaMenu =dataBaseHelper.getPizzaMenuById(order.getPizzaId());
                if(pizzaMenu.getPizzaType().equals(spinnerPizzaType.getSelectedItem().toString())){
                    totalIncomeOftTpe+=order.getTotalPrice();
                    totalOrderOftTpe+=order.getQuantity();
                }
            }
            tvNumberOfOrders.setText("Number of Orders: "+totalOrderOftTpe);
            tvIncomePerType.setText("Income for Selected Type: $"+totalIncomeOftTpe);
        }

        private void calculateIncome() {
            totalIncome=0.0;
            List<Order> orders =new ArrayList<>();
            orders=dataBaseHelper.getAllOrders();
            for(Order order:orders){

                totalIncome+=order.getTotalPrice();

            }

            tvTotalIncome.setText("Total Income for All Types: $"+totalIncome);
        }

}