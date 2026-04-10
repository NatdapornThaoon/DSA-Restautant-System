package main.java.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private final int orderId;
    private final List<OrderItem> items;
    private double total;
    private final Date timestamp;    

    public Order(int orderID) {
        this.orderId =  orderID;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.timestamp = new Date();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }

    private void calculateTotal() {
        total = 0;
        for (OrderItem item : items) {
            total +=item.getTotal();
        }
    }

    @Override
    public String toString() {
        return "Order #" + orderId + " | Total " + total + "THB (" + timestamp + ")";
    }
}
