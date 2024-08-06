package com.example.advancepizza.rest;

import com.example.advancepizza.obects.PizzaMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PizzaTypesJsonParser {

    public static List<PizzaMenu> getPizzaMenuFromJson(String json) {
        List<PizzaMenu> pizzaMenuList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pizzaObject = jsonArray.getJSONObject(i);
                String pizzaType = pizzaObject.getString("pizzaType");
                String description = pizzaObject.getString("description");
                String size = pizzaObject.getString("size");
                String price = pizzaObject.getString("price");
                String category = pizzaObject.getString("category");

                PizzaMenu pizzaMenu = new PizzaMenu(pizzaType, description, size, price, category);
                pizzaMenuList.add(pizzaMenu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pizzaMenuList;
    }

}
