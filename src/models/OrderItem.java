package src.models;

public class OrderItem {

    private final MenuItem item;
    private final int quantity;

    public OrderItem(MenuItem item,int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double  getTotal() {
        return item.getPrice()*quantity;
    }

    @Override
    public String toString() {
        return item.getName() + "x" + quantity + " = " +getTotal();
    }
}
