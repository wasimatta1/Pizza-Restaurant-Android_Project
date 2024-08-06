package com.example.advancepizza.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.Activity.loginAndRegistrationActivity;
import com.example.advancepizza.obects.PizzaMenu;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
        ((MainActivity) activity).setProgress(true);
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!= null) {
            ((MainActivity) activity).setProgress(false);
            ((MainActivity) activity).setButtonText("connected");
            List<PizzaMenu> pizzaTypes = PizzaTypesJsonParser.getPizzaMenuFromJson(s);
            ((MainActivity) activity).fillPizzaMenu(pizzaTypes);
            Intent intent = new Intent((MainActivity) activity, loginAndRegistrationActivity.class);
            ((MainActivity) activity).startActivity(intent);
        }else {
            ((MainActivity) activity).setProgress(false);
            ((MainActivity) activity).setButtonText("Get Started");
            Toast.makeText((MainActivity) activity, "Connection failed", Toast.LENGTH_SHORT).show();
        }
    }
}