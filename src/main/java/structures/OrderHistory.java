package main.java.structures;

public class OrderHistory {
    class Node {
    KitchenQueue data;
    Node next,prev;
    Node (KitchenQueue data){
        this.data = data;
    }
    }
    Node head, tail ;
    private int size = 0;

    public void historyOrder(KitchenQueue data){
        Node newNode = new Node(data);
       if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    public void Timehistory(){
        if (head == null) {
            System.out.println("Empty");
            return;
        }
        else{
            System.out.println("History Order (Time)");
            Node currtime = head;
            while (currtime != null) {
                System.out.println(currtime.data);
                System.out.println("-----------------------------------");
                currtime = currtime.next;
        }
        
            }
    }

    public void latestRecursive() {
        if (tail == null) {
            System.out.println("Empty");
            return;
        }
        System.out.println("History Order latest");
        printBackward(tail);
    }

    private void printBackward(Node node) {
        if (node == null) return;

        System.out.println(node.data);
        System.out.println("-----------------------------------");

        printBackward(node.prev);
    }
}
