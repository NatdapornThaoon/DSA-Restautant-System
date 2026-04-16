package structures;

import java.util.ArrayList;
import java.util.List;
import models.Order;

public class OrderHistory {
    class Node {
        Order data;
        Node next, prev;
        
        Node(Order data) {
            this.data = data;
        }
    }
    
    private Node head, tail;

    public void addOrder(Order data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void displayForward() {
        if (head == null) {
            System.out.println("Empty");
            return;
        }
        System.out.println("History Order (Time)");
        Node currtime = head;
        while (currtime != null) {
            System.out.println(currtime.data);
            System.out.println("-----------------------------------");
            currtime = currtime.next;
        }
    }

    public void displayBackwardRecursive() {
        if (tail == null) {
            System.out.println("No Order History");
            return;
        }
        System.out.println("History Order (Latest)");
        printBackward(tail);
    }

    private void printBackward(Node node) {
        if (node == null) return;

        System.out.println(node.data);
        System.out.println("-----------------------------------");

        printBackward(node.prev);
    }

    //TEST --------------------------------- OODP
    public List<String> toFileLines() {
        List<String> lines = new ArrayList<>();
        Node current = head;
        while (current != null){
            lines.add("Order #" + current.data.getId() + " | " + current.data.toString() + " | " + current.data.getTotalPrice() + " THB");
            current = current.next;
        }
        return lines;
    }






}
