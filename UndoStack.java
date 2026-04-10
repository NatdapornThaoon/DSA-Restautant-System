package datastructure;

import model.OrderItem;
import java.util.EmptyStackException;

public class UndoStack {
    private Node top;

    private class Node {
        OrderItem data;
        Node next;

        Node(OrderItem data) {
            this.data = data;
        }
    }

    // เพิ่มรายการลงใน Stack (Push)
    public void push(OrderItem item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
    }

    // นำรายการล่าสุดออกมา (Pop)
    public OrderItem pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        OrderItem item = top.data;
        top = top.next;
        return item;
    }

    public boolean isEmpty() {
        return top == null;
    }
}