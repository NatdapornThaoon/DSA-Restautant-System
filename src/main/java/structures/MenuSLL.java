package main.java.structures;

import main.java.models.MenuItems;

public class MenuSLL {
    //Singly Linked List for Menu Items
    //private static class: ใช้ได้เฉพาะภายในคลาส MenuSLL เท่านั้น
    private Node head;// ชี้ไปที่ Node แรกของ Linked List

    private static class Node {
        MenuItems data; //ข้อมูลที่เก็บใน (MenuItem)
        Node next; // ชี้ไป node ถัดไป
        Node(MenuItems data) { 
            this.data = data; 
        }
    }

    // เพิ่ม node ต่อท้าย linked list
    public void add(MenuItems item) {
        Node newNode = new Node(item);
        // กรณี list ว่าง: head == null
        if (head == null){
            head = newNode;
            return;
        }
        // กรณี list ไม่ว่าง: วนไปจนสุด
        Node current = head;
        while (current.next != null) { //ตราบใดที่ยังมี node ถัดไป
            current = current.next; // เลื่อนไป Node ถัดไป
        }
        current.next = newNode; //ต่อ node ใหม่ที่ท้าย
    }

    // แสดงเมนูทั้งหมด (Iterative)

    public void display(){
        if (head == null) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.println("---All Menu Items (SLL)---"); 
        Node current = head;
        while (current != null){
            System.out.println(current.data); //แสดงข้อมูล MenuItem
            current = current.next; //เลื่อนไป Node ถัดไป
        }
    }

    //ค้นหาเมนูตาม id (Iterative)
    public MenuItems findById(int id){
        Node current = head;
        while (current != null){
            if (current.data.getId() == id) {
                return current.data; //พบเมนูที่ต้องการ
            }
            current = current.next; //เลื่อนไป Node ถัดไป
        }
        return null; //ไม่พบเมนูที่ต้องการ
    }

    
}
