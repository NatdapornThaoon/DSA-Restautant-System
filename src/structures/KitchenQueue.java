package structures;

import java.util.ArrayDeque;
import java.util.Queue;
import models.Order;

public class KitchenQueue {
    private final Queue<Order> queue = new ArrayDeque<>();  // ใช้ Queue ของ Java
    
    // เพิ่มออเดอร์ต่อท้ายคิว
    public void addOrder(Order order) {
        queue.offer(order);  // offer = เพิ่มต่อท้าย
        System.out.println("Order #" + order.getId() + " has been sent to the kitchen.");
    }
    
    // ดึงออเดอร์แรกออกมาทำอาหาร
    public void processNextOrder() {
        Order order = queue.poll();  // poll = ดึงตัวแรกออก
        if (order == null) {
            System.out.println("No orders in the kitchen");
            return;
        }
        System.out.println("Chef is cooking");
        System.out.println(order);
    }
    
    // แสดงคิวทั้งหมด
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is available");
            return;
        }
        
        System.out.println("Kitchen Queue:");
        for (Order order : queue) {
            System.out.println("   Order #" + order.getId() + " (Total: " + order.getTotalPrice() + " THB)");
        }
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
