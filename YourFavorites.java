package com.example.advancepizza.obects;

public class YourFavorites {

    int favoritesId;
    String customerEmail;
    int pizzaID;

    public YourFavorites() {
    }

    public YourFavorites(int favoritesId, String customerEmail, int pizzaID) {
        this.favoritesId = favoritesId;
        customerEmail = customerEmail;
        this.pizzaID = pizzaID;
    }
    public YourFavorites(String customerEmail, int pizzaID) {
        customerEmail = customerEmail;
        this.pizzaID = pizzaID;
    }

    public int getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(int favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }
}

