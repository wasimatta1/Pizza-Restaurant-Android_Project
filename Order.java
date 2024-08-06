package com.example.advancepizza.obects;

public class Order {
    int orderId;
    int pizzaId;

    int orderNameId;

    int quantity;

    double totalPrice;

    String size;

    String dateOfOrder;

    public Order() {
    }

    public Order(int orderId, int pizzaId, int orderNameId, int quantity, double totalPrice, String size, String dateOfOrder) {
        this.orderId = orderId;
        this.pizzaId = pizzaId;
        this.orderNameId = orderNameId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.size = size;
        this.dateOfOrder = dateOfOrder;
    }
    public Order( int pizzaId, int orderNameId, int quantity, double totalPrice, String size, String dateOfOrder) {
        this.pizzaId = pizzaId;
        this.orderNameId = orderNameId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.size = size;
        this.dateOfOrder = dateOfOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getOrderNameId() {
        return orderNameId;
    }

    public void setOrderNameId(int orderNameId) {
        this.orderNameId = orderNameId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }
}
