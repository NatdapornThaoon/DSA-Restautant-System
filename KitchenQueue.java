package datastructure;

import model.Order;

public class KitchenQueue {
    private Node head, tail;
    private int size = 0;

    private class Node {
        Order order;
        Node next;
        Node(Order order) { this.order = order; }
    }

    // เพิ่มออเดอร์เข้าคิว (Enqueue)
    public void enqueue(Order order) {
        Node newNode = new Node(order);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // นำออเดอร์ออกไปทำ (Dequeue)
    public Order dequeue() {
        if (isEmpty()) return null;
        Order order = head.order;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return order;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // แสดงรายการคิวทั้งหมด
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("ไม่มีคิวในขณะนี้");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println("Order #" + current.order.getOrderId() + " (รวม " + current.order.getTotalPrice() + " THB)");
            current = current.next;
        }
    }
}