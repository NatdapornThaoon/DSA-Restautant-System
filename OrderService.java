package service;

import model.OrderItem;
import datastructure.UndoStack;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<OrderItem> cart = new ArrayList<>(); // ตะกร้าปัจจุบัน
    private UndoStack undoStack = new UndoStack();    // Stack สำหรับเก็บประวัติการเพิ่ม

    public void addItemToCart(OrderItem item) {
        cart.add(item);
        undoStack.push(item); // เก็บไว้ใน stack เพื่อให้ undo ได้
        System.out.println("Add " + item.getMenuName() + " x" + item.getQuantity() + " Added to cart");
    }

    public void undoLastAction() {
        if (!undoStack.isEmpty()) {
            OrderItem lastItem = undoStack.pop();
            cart.remove(lastItem); // ลบออกจากตะกร้าจริง
            System.out.println("Cancel add" + lastItem.getMenuName() + " x" + lastItem.getQuantity());
        } else {
            System.out.println( "no items to cancel");
        }
    }
}