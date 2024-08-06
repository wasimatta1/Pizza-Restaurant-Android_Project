package com.example.advancepizza.obects;

public class PizzaMenu {

    int pizzaId ;

    String pizzaType;
    String Description;

    String size;

    String price;
    String  category;



    public PizzaMenu() {

    }
    public PizzaMenu(int pizzaId, String pizzaType, String description, String size, String price, String category) {
        this.pizzaId = pizzaId;
        this.pizzaType = pizzaType;
        Description = description;
        this.size = size;
        this.price = price;
        this.category = category;
    }
    public PizzaMenu(String pizzaType, String description, String size, String price, String category) {
        this.pizzaType = pizzaType;
        Description = description;
        this.size = size;
        this.price = price;
        this.category = category;
    }



    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "PizzaMenu{" +
                "pizzaType='" + pizzaType + '\'' +
                ", Description='" + Description + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
