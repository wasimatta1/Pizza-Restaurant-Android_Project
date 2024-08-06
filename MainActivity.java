package com.example.advancepizza.Activity;


import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.User;
import com.example.advancepizza.rest.ConnectionAsyncTask;
import com.example.advancepizza.R;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public  static  String DBName= "V1.0";

    public  static User user;

    public static  ArrayList<Order> cardOrder =new ArrayList<>();
    public  static boolean isFromFilter = false;
    Button button;

    ArrayList<PizzaMenu> pizzaTypes;
    private ImageView pizzaImage;
    DataBaseHelper dataBaseHelper;


    int id;



    //array list  contains all pizza:
    public static List<PizzaMenu> allList = new ArrayList<>();


    //output list of filter operation
    public static List<PizzaMenu> outputList = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dataBaseHelper =new DataBaseHelper(this, DBName,null,1);




        setProgress(false);
        button = (Button) findViewById(R.id.get_start);
        pizzaImage = new ImageView(this);
        // get connection to rest and read the data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://3a4c425fee644b5991191edf71026c8d.api.mockbin.io/");
            }
        });

    }
    public void setButtonText(String text) {

        button.setText(text);
    }

    // stop the progress of working or turn it on
    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }



    // copy the list of types from rest to our list
    public void fillPizzaMenu(List<PizzaMenu> types) {

        pizzaTypes = new ArrayList<>(types);

        for (PizzaMenu pizzaMenu : pizzaTypes){

            // id this pizza not in data base add it
            if(!dataBaseHelper.isPizzaExist(pizzaMenu.getPizzaType(),pizzaMenu.getSize())){
                dataBaseHelper.addPizzaMenu(pizzaMenu);
            }

        }

    }

    public static int getPizzaImage(String type) {

        if(type.trim().equals("Margarita".trim())){
           return (R.drawable.margarita);
        }else if(type.trim().equals("Neapolitan".trim())){
            return(R.drawable.neapolitan);
        } else if(type.trim().equals("Hawaiian".trim())){
            return(R.drawable.hawaiin);
        } else if(type.trim().equals("Calzone".trim())){
            return(R.drawable.alzone);
        } else if(type.trim().equals("Tandoori Chicken Pizza".trim())){
            return(R.drawable.tandoori);
        } else if(type.trim().equals("BBQ Chicken Pizza".trim())){
            return(R.drawable.bbqchicken);
        } else if(type.trim().equals("Seafood Pizza".trim())){
            return(R.drawable.img_6);
        } else if(type.trim().equals("Vegetarian Pizza".trim())){
            return(R.drawable.vegetarian);
        }else if(type.trim().equals("Buffalo Chicken Pizza".trim())){
            return(R.drawable.buffalo);
        }else if(type.trim().equals("Mushroom Truffle Pizza".trim())){
            return(R.drawable.mashroom);
        } else if(type.trim().equals("Pepperoni".trim())){
            return(R.drawable.pepperoni);
        }else if(type.trim().equals("New York Style".trim())){
            return(R.drawable.img_6);
        }else if(type.trim().equals("Pesto Chicken Pizza".trim())){
           return(R.drawable.img_6);
         }
        else if(type.trim().equals("PizzaLogo".trim())){
            return(R.drawable.img_8);
        }else if(type.trim().equals("nav".trim())) {
            return (R.drawable.offer_nav);
        }
        return 0;
    }


    // function to hash the password
    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void makeListUniqueByPizzaType() {
        Set<String> seenPizzaTypes = new LinkedHashSet<>();
        List<PizzaMenu> uniqueList = new ArrayList<>();

        for (PizzaMenu pizza : outputList) {
            if (seenPizzaTypes.add(pizza.getPizzaType())) {
                uniqueList.add(pizza);
            }
        }

        outputList.clear();
        outputList.addAll(uniqueList);
    }


}