package main.java.models;

public class OrderItem {

    private final MenuItems item;
    private final int quantity;

    public OrderItem(MenuItems item,int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double  getTotal() {
        return item.getPrice()*quantity;
    }

    @Override
    public String toString() {
        return item.getName() + "x" + quantity + " = " +getTotal() + " THB";
    }
}
