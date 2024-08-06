package com.example.advancepizza.obects;

public class SpecialOffers {

    int OfferID;

    int pizzaID;

    String price;

    String startOfer;
    String endOfer;

    public SpecialOffers() {
    }
    public SpecialOffers( int pizzaID, String price, String startOfer, String endOfer) {
        this.pizzaID = pizzaID;
        this.price = price;
        this.startOfer = startOfer;
        this.endOfer = endOfer;
    }
    public SpecialOffers(int offerID, int pizzaID, String price, String startOfer, String endOfer) {
        OfferID = offerID;
        this.pizzaID = pizzaID;
        this.price = price;
        this.startOfer = startOfer;
        this.endOfer = endOfer;
    }

    public int getOfferID() {
        return OfferID;
    }

    public void setOfferID(int offerID) {
        OfferID = offerID;
    }

    public int getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartOfer() {
        return startOfer;
    }

    public void setStartOfer(String startOfer) {
        this.startOfer = startOfer;
    }

    public String getEndOfer() {
        return endOfer;
    }

    public void setEndOfer(String endOfer) {
        this.endOfer = endOfer;
    }
}
