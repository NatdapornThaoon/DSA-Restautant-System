package main.java.structures;

import java.util.Stack;

// public class UndoStack {

//      private Node top;

//     private class Node {
//         OrderItem data;
//         Node next;

//         Node(OrderItem data) {
//             this.data = data;
//         }
//     }

//     // เพิ่มรายการลงใน Stack (Push)
//     public void push(OrderItem item) {
//         Node newNode = new Node(item);
//         newNode.next = top;
//         top = newNode;
//     }

//     // นำรายการล่าสุดออกมา (Pop)
//     public OrderItem pop() {
//         if (isEmpty()) {
//             throw new EmptyStackException();
            
//         }
//         OrderItem item = top.data;
//         top = top.next;
//         return item;
//     }

//     public boolean isEmpty() {
//         return top == null;
//     }
    
// }

public class UndoStack {
    private final Stack<String> stack = new Stack<>();  // เก็บข้อความการกระทำ
    
    // เพิ่มการกระทำลง stack
    public void push(String action) {
        stack.push(action);  // push = วางบนสุด
    }
    
    // ดึงการกระทำล่าสุดออก (LIFO)
    public String pop() {
        return stack.isEmpty() ? null : stack.pop();  // pop = ดึงออก
    }
    
    // ล้าง stack ทั้งหมด
    public void clear() {
        stack.clear();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}






